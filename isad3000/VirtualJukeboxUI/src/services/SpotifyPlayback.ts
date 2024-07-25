/* Service which controls playback through the Spotify Web SDK */
/* Imports */
import axios from "axios";
import { useCookies } from "vue3-cookies";
import { inject } from "vue";
/* Stores */
import { useSession } from "../stores/session";
import { usePlayer } from "../stores/player";
/* Services */
import SpotifyService from "./SpotifyTokens";
export default {
  methods: {
    playback() {
      const { cookies } = useCookies();
      const sessionStore = useSession();
      const playerStore = usePlayer();
      const logger: any = inject("vuejs3-logger");

      /*Insert the Web Player SDK script */
      const script = document.createElement("script");
      script.src = "https://sdk.scdn.co/spotify-player.js";
      script.async = true;
      script.id = "spotify-player";
      document.body.appendChild(script);

      /*When SDK playback is ready */
      (window as any).onSpotifyWebPlaybackSDKReady = () => {
        /*Initialises the web player */
        let device: string;
        // @ts-ignore
        const player = new Spotify.Player({
          name: "Virtual Jukebox Web Player",
          /* Callback function which is called whenever tokens expire */
          getOAuthToken: (cb: any) => {
            SpotifyService.methods.checkTokens();
            const token = cookies.get("accesstoken");
            cb(token);
          },
          volume: 0.5,
        });
        // Ready
        player.addListener("ready", ({ device_id }: any) => {
          logger?.info("Ready with Device ID", device_id);
          playerStore.device_ready = true;
          device = device_id;
          /* If a song exists in the queue, play that song */
          if (sessionStore.queue.length != 0) {
            const current_song = sessionStore.queue[0].song_id;
            const request = {
              access_token: cookies.get("accesstoken"),
              track_uri: current_song,
              device_id: device,
            };
            this.playSong(request, player, playerStore, logger);
          }
        });

        // Not Ready
        player.addListener("not_ready", ({ device_id }: any) => {
          logger?.error("Device ID has gone offline", device_id);
        });

        player.addListener("initialization_error", ({ message }: any) => {
          logger?.error(message);
        });

        player.addListener("authentication_error", ({ message }: any) => {
          logger?.error(message);
        });

        player.addListener("account_error", ({ message }: any) => {
          logger?.error(message);
        });

        player.on("playback_error", ({ message }: any) => {
          logger?.error("Failed to perform playback", message);
        });

        /* Method fires when song "changes state" 
      Changing state means a lot of things in Spotify. This includes:
      a song pausing, playing, skipping, cross-fading, ending or starting.
      It can also just fire randomly. Thanks Spotify.*/
        player.addListener("player_state_changed", (state: any) => {
          if (sessionStore.queue[0].song_id) {
            sessionStore.websocket.send(
              JSON.stringify({
                type: "playback_changed",
                song_id: sessionStore.queue[0].song_id,
                paused: state.paused,
                song_length: state.duration,
                song_progress: state.position,
              })
            );
          }
          /* Check if the song has just ended */
          if (
            !playerStore.swapping_songs &&
            !state.loading &&
            state.position == state.duration
          ) {
            /* If it has, we set a variable (acts as a cond), then pop the song queue */
            playerStore.swapping_songs = true;
            sessionStore.websocket.send(
              JSON.stringify({ type: "song_changed" })
            );
          }
        });

        /* Connects the player, authenticates it with the web browser */
        player.connect();
        const confirmButton = document.getElementById("webplayer-authenticate");
        confirmButton?.addEventListener("click", () => {
          player.activateElement();
          logger?.info("Web player authenticated");
        });

        const playbackButton = document.getElementById("playback-controls");
        playbackButton?.addEventListener("click", () => {
          if (playerStore.autoplay_allowed) {
            player.togglePlay();
            logger?.info("Playback changed");
          } else if (sessionStore.queue.length == 0) {
            const randomSong = sessionStore.pickRandomSong();
            sessionStore.websocket.send(
              JSON.stringify({
                type: "autoplay_song_queued",
                song_id: randomSong,
              })
            );
          }
        });

        /* Disconnects the web player when a session is closed */
        const closeButton = document.getElementById("webplayer-close");
        closeButton?.addEventListener("click", () => {
          playerStore.sessionDefault();
          player.disconnect();
        });

        /* Method fired when the queue is updated.
        If we are in the process of swapping songs, will play the next song in the queue */
        sessionStore.$subscribe(async () => {
          logger?.debug("The session store has changed state");
          /* Check if a song has ended */
          if (playerStore.swapping_songs && playerStore.device_ready) {
            /* Check if there is another song in the queue */
            if (sessionStore.queue.length != 0) {
              if (
                !playerStore.autoplay_allowed &&
                playerStore.starting_first_song
              )
                return;
              if (!playerStore.starting_first_song) {
                playerStore.starting_first_song = true;
              }
              await SpotifyService.methods.checkTokens(); /* Updates tokens if necessary */
              /* Use the session playlist to find information about the queued song */
              const current_song = sessionStore.queue[0].song_id;
              const request = {
                access_token: cookies.get("accesstoken"),
                track_uri: current_song,
                device_id: device,
              };
              await this.playSong(request, player, playerStore, logger);
              this.newSong(current_song, sessionStore, logger);
            } else if (playerStore.autoplay_allowed) {
              /* This will recursively run the subscribe method again once a song is added to the start of the queue */
              const randomSong = sessionStore.pickRandomSong();
              sessionStore.websocket.send(
                JSON.stringify({
                  type: "autoplay_song_queued",
                  song_id: randomSong,
                })
              );
            }
          }
        });
      };
    },
    /* Calls the playsong API to update the cache.
    This keeps the cache up-to-date with the currently playing song */
    async newSong(
      current_song: string | number,
      sessionStore: any,
      logger: any
    ) {
      let jsonResponse: { status: number } | undefined;
      const request = { spotify_song_id: current_song };
      try {
        await axios
          .put(
            import.meta.env.VITE_API_URL +
              "/vjsession/playsong/" +
              sessionStore.session_id +
              "/",
            request
          )
          .then((response) => (jsonResponse = response));
        if (jsonResponse?.status == 200) {
          logger?.info("Song changed on server");
        }
      } catch (AxiosError: any) {
        if (AxiosError.response.status == 400) {
          /* 400: Invalid request */
          logger?.warn("Invalid request");
        } else if (AxiosError.response.status == 404) {
          /* 404: Session or song does not exist */
          logger?.warn("Session or song does not exist");
        } else {
          /* Other errors such as server connection */
          logger?.warn("Unknown error");
        }
      }
    },
    /*Given a request, makes an API call to play that song. */
    async playSong(
      request: {
        access_token: string;
        track_uri: string | number;
        device_id: string;
      },
      player: any,
      playerStore: any,
      logger: any
    ) {
      let jsonResponse: { status: number } | undefined;
      await fetch(
        `https://api.spotify.com/v1/me/player/play?device_id=${request.device_id}`,
        {
          method: "PUT",
          body: JSON.stringify({
            uris: ["spotify:track:" + request.track_uri],
          }),
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${request.access_token}`,
          },
        }
      ).then((response) => (jsonResponse = response));
      if (jsonResponse?.status == 202) {
        this.getPlayerStatus(player, playerStore, logger);
        playerStore.started = true;
      } else {
        logger?.warn("Trying again...");
        this.playSong(request, player, playerStore, logger);
      }
    },
    /*Get the currently playing song (server side) and set it as the currenty playing song locally.
          This might seem counter-intuitive, but the web player sometimes redirects to other songs */
    async getPlayerStatus(player: any, playerStore: any, logger: any) {
      /*Wait half a second for API call to take effect in the web player.*/
      await new Promise((resolve) => setTimeout(resolve, 500));
      player.getCurrentState().then((state: any) => {
        if (state) {
          playerStore.current_song = state.context.metadata.current_item.uri;
          playerStore.swapping_songs = false;
          playerStore.autoplay_allowed = true;
          logger?.info("Song successfully played");
        } else {
          logger?.warn(
            "Couldn't find a currently playing song, trying again..."
          );
          this.getPlayerStatus(player, playerStore, logger);
        }
      });
    },
  },
};
