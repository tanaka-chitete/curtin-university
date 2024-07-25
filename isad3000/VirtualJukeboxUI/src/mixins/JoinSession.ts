/* Makes an API call to join a session */
/* Imports */
import axios from "axios";
import { inject } from "vue";
/* Stores */
import { useSession } from "../stores/session";
import { useUser } from "../stores/user";
/* Services */
import WebSockets from "../services/WebSockets";
import TokenAdder from "../services/TokenAdder";
/* Types */
import type { playlistSong, queueElement } from "../stores/session";
export default {
  async joinSession(
    session_id: string,
    session_password?: string
  ): Promise<{
    success: boolean;
    message: string;
    sessionId: string | undefined;
  }> {
    const sessionStore = useSession();
    const userStore = useUser();
    const request = {
      guest_id: userStore.id,
      session_password: session_password ?? "",
      // Actually the user's location, named like this for backend validation purposes
      session_location:
        "POINT(" + userStore.longitude + " " + userStore.latitude + ")",
    };
    let jsonResponse:
      | {
          data: {
            playlist_songs: playlistSong;
            session: {
              session_name: string;
              session_description: string;
              id: string;
              host_id: string;
            };
            queue: queueElement[];
            playing: queueElement | undefined;
          };
          status: number;
        }
      | undefined;
    try {
      await axios
        .put(
          import.meta.env.VITE_API_URL + "/vjsession/join/" + session_id + "/",
          request
        )
        .then((response) => (jsonResponse = response));
      if (jsonResponse && jsonResponse.status == 200) {
        /* Success */
        /* Session Info */
        sessionStore.setName(jsonResponse.data.session.session_name);
        sessionStore.setHostID(jsonResponse.data.session.host_id);
        sessionStore.setSessionID(jsonResponse.data.session.id);
        sessionStore.setDescription(
          jsonResponse.data.session.session_description
        );
        /* Session song info */
        sessionStore.setPlaylist(jsonResponse.data.playlist_songs);
        sessionStore.setQueue(jsonResponse.data.queue);
        sessionStore.setCurrentlyPlaying(jsonResponse.data.playing);
        sessionStore.sortQueue();
        this.checkLikedSongs(
          jsonResponse.data.queue,
          userStore.id,
          sessionStore
        );
        WebSockets.methods.setupSocket(session_id); /* Setup the websockets */
        TokenAdder.startTokenInterval();
        TokenAdder.addToken(
          userStore.id,
          sessionStore,
          inject("vuejs3-logger")
        ); /* Guests start with 1 token */
        return {
          success: true,
          message: "Session successfully joined",
          sessionId: jsonResponse.data.session.id,
        };
      } else {
        /* Every status other than 200 should throw an error, so we should not reach this */
        return {
          success: false,
          message: "Failed to join session",
          sessionId: "",
        };
      }
    } catch (AxiosError: any) {
      if (AxiosError.response.status == 400) {
        /* 400: Invalid request */
        return {
          success: false,
          message: "Invalid ID or password",
          sessionId: "",
        };
      } else if (AxiosError.response.status == 401) {
        /* 401: Incorrect Password OR out of connection range*/
        if (AxiosError.response.data.Error == "Not in range.") {
          return {
            success: false,
            message: "Not in range",
            sessionId: "",
          };
        } else {
          return {
            success: false,
            message: "Incorrect Password",
            sessionId: "",
          };
        }
      } else if (AxiosError.response.status == 404) {
        /* 404: User ID not found */
        return { success: false, message: "User ID not found", sessionId: "" };
      } else if (AxiosError.response.status == 409) {
        if (AxiosError.response.data.Error == "Already hosting a session.") {
          /* 409: Already Hosting a Session */
          const closeHosted = document.getElementById("closeHosted");
          if (closeHosted) {
            closeHosted.style.display = "block";
          }
          return {
            success: false,
            message: "You are already hosting a session",
            sessionId: "",
          };
        } else {
          /* 409: Already In Session */
          const leaveJoined = document.getElementById("leaveJoined");
          if (leaveJoined) {
            leaveJoined.style.display = "block";
          }
          return {
            success: false,
            message: "You are already in a session",
            sessionId: "",
          };
        }
      } else {
        /* Other errors such as server connection */
        return { success: false, message: "Unknown error", sessionId: "" };
      }
    }
  },
  checkLikedSongs(allSongs: any[], userID: string, sessionStore: any) {
    for (const song of allSongs) {
      if (song.voted_by.includes(userID)) sessionStore.voted.add(song.song_id);
    }
  },
};
