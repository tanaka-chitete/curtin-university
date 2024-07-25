/* Service which controls the the setup, disconnection and messaging handling of session websockets. */
/* Imports */
import { inject } from "vue";
/* Stores */
import { useSession } from "../stores/session";
import { usePlayer } from "../stores/player";
import { useUser } from "../stores/user";
import { useChat } from "../stores/chat";
export default {
  methods: {
    setupSocket(roomID: string) {
      const logger: any = inject("vuejs3-logger");
      const sessionStore = useSession();
      const playerStore = usePlayer();
      const userStore = useUser();
      const chatStore = useChat();
      /* Connect to the session's websocket */
      const socket = new WebSocket(
        import.meta.env.VITE_WEBSOCKET_URL + "/ws/session/" + roomID + "/"
      );
      socket.onopen = () => {
        logger?.info("Websocket connection established");
        sessionStore.setSocket(socket);
      };
      /* Event handlers for various websocket events */
      socket.onmessage = function (e) {
        const msg = JSON.parse(e.data);
        switch (msg.type) {
          case "user_joined":
            logger?.info("user joined event: " + msg.type);
            /* If we are the host, broadcast playback information */
            if (userStore.id == sessionStore.host_id && sessionStore.queue.length > 0) {
              socket.send(
                JSON.stringify({
                  type: "playback_changed",
                  song_id: sessionStore.queue[0].song_id,
                  paused: playerStore.paused,
                  song_length: playerStore.song_length,
                  song_progress: playerStore.song_progress,
                })
              );
            }
            break;
          /* A song has been queued */
          case "song_queued":
            logger?.info("song queued event: " + msg.type);
            const queueSong = {
              song_id: msg.song_id,
              votes: 1,
              time_added: msg.time_added,
            };
            sessionStore.addToQueue(queueSong);
            break;
          /* A song has been queued */
          case "autoplay_song_queued":
            logger?.info("song queued event: " + msg.type);
            const autoplaySong = {
              song_id: msg.song_id,
              votes: 0,
              time_added: new Date().getTime(),
            };
            sessionStore.addToQueue(autoplaySong);
            break;
          /* A song has been voted for */
          case "song_vote":
            logger?.info("song vote event: " + msg.type);
            sessionStore.voteForSong(msg.song_id);
            break;
          /* A chat message has been sent */
          case "chat_message":
            logger?.info("chat message event: " + msg.type);
            const chatMessage = {
              username: msg.name,
              message: msg.message,
              self: msg.name == userStore.username,
              timestamp: new Date().toLocaleTimeString(),
            };
            chatStore.newMessage(chatMessage);
            break;
          /* A new song is currently playing */
          case "song_changed":
            logger?.info("song changed event: " + msg.type);
            sessionStore.popQueue();
            break;
          /* Playback has changed (paused, played) */
          case "playback_changed":
            logger?.info("playback changed event: " + msg.type);
            if (msg.song_id != sessionStore.queue[0]?.song_id && !playerStore.swapping_songs) {
              location.reload();
              break;
            }
            playerStore.paused = msg.paused;
            /* Set the song position for the progress bar */
            playerStore.song_length = msg.song_length;
            playerStore.song_progress = msg.song_progress;
            break;
          /* The room has been closed */
          case "room_closed":
            logger?.info("room closed event: " + msg.type);
            sessionStore.allow_leave = true;
            const sessionClosedModal = document.getElementById("sessionClosed");
            if (sessionClosedModal) {
              sessionClosedModal.style.display = "block";
            }
            socket.close();
            break;
          default:
            logger?.warn("Unkown message event: " + msg.type);
        }
      };
      socket.onclose = function () {
        logger?.warn("Socket closed");
        const websocketClosedModal = document.getElementById("websocketClosed");
        if (websocketClosedModal && !sessionStore.allow_leave) {
          websocketClosedModal.style.display = "block";
        }
      };
    },
  },
};
