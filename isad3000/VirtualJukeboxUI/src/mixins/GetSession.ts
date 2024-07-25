/* Makes an API call to get session info without joining */
/* Imports */
import axios from "axios";
/* Stores */
import { useSession } from "../stores/session";
import { useUser } from "../stores/user";
/* Services */
import WebSockets from "../services/WebSockets";
import TokenAdder from "../services/TokenAdder";
/* Types */
import type { playlistSong, queueElement } from "../stores/session";
export default {
  async getSession(session_id: string): Promise<{
    success: boolean;
    message: string;
  }> {
    const sessionStore = useSession();
    const userStore = useUser();
    const request = { user_id: userStore.id };
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
            session_user: {
              credits: number;
            };
            queue: queueElement[];
            playing: queueElement | undefined;
          };
          status: number;
        }
      | undefined;
    try {
      await axios
        .get(
          import.meta.env.VITE_API_URL +
            "/vjsession/get/user/" +
            session_id +
            "/",
          { params: request }
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
        sessionStore.setTokens(jsonResponse.data.session_user.credits);
        TokenAdder.startTokenInterval();
        return {
          success: true,
          message: "Session information successfully retreived",
        };
      } else {
        /* Every status other than 200 should throw an error, so we should not reach this */
        return {
          success: false,
          message: "Failed to join session",
        };
      }
    } catch (AxiosError: any) {
      if (AxiosError.response.status == 400) {
        /* 400: Invalid request */
        return {
          success: false,
          message: "Invalid ID or password",
        };
      } else if (AxiosError.response.status == 404) {
        /* 404: Session ID not found */
        return { success: false, message: "Sessuib not found" };
      } else {
        /* Other errors such as server connection */
        return { success: false, message: "Unknown error" };
      }
    }
  },
  checkLikedSongs(allSongs: any[], userID: string, sessionStore: any) {
    for (const song of allSongs) {
      if (song.voted_by.includes(userID)) sessionStore.voted.add(song.song_id);
    }
  },
};
