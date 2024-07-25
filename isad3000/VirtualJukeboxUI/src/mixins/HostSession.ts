/* Makes an API call to host a session */
/* Imports */
import axios from "axios";
import { useCookies } from "vue3-cookies";
/* Stores */
import { useSession } from "../stores/session";
import { useUser } from "../stores/user";
/*Services */
import SpotifyTokens from "../services/SpotifyTokens";
import WebSockets from "../services/WebSockets";
import TokenAdder from "../services/TokenAdder";
/* Types */
import type { playlistSong, queueElement } from "../stores/session";
export default {
  async hostSession(
    is_private: boolean,
    session_name: string,
    session_password: string,
    session_description: string,
    session_location: string,
    connection_range: number,
    spotify_playlist_id: string
  ): Promise<{
    success: boolean;
    message: string;
    sessionId: string | undefined;
  }> {
    const { cookies } = useCookies();
    await SpotifyTokens.methods.checkTokens(); /* Checks if current tokens have expired or not */
    const sessionStore = useSession();
    const userStore = useUser();
    const request = {
      access_token: cookies.get("accesstoken"),
      host_id: userStore.id,
      is_private: is_private,
      session_name: session_name,
      session_password: session_password,
      session_location: session_location,
      session_description: session_description,
      connection_range: connection_range,
      spotify_playlist_id: spotify_playlist_id,
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
          };
          status: number;
        }
      | undefined;
    try {
      await axios
        .post(import.meta.env.VITE_API_URL + "/vjsession/create/", request)
        .then((response) => (jsonResponse = response));
      if (jsonResponse && jsonResponse.status == 201) {
        /* Success */
        sessionStore.setPlaylist(jsonResponse.data.playlist_songs);
        sessionStore.setName(jsonResponse.data.session.session_name);
        sessionStore.setSessionID(jsonResponse.data.session.id);
        sessionStore.setDescription(
          jsonResponse.data.session.session_description
        );
        sessionStore.setHostID(userStore.id);
        WebSockets.methods.setupSocket(
          jsonResponse.data.session.id
        ); /* Setup the websockets */
        TokenAdder.startTokenInterval();
        return {
          success: true,
          message: "Virtual Jukebox session created",
          sessionId: jsonResponse.data.session.id,
        };
      } else {
        /* Every status other than 200 should throw an error, so we should not reach this */
        return {
          success: false,
          message: "Failed to create session",
          sessionId: "",
        };
      }
    } catch (AxiosError: any) {
      if (AxiosError.response.status == 400) {
        /* 400: Invalid request */
        return {
          success: false,
          message: "One or more fields contains invalid data",
          sessionId: "",
        };
      } else if (AxiosError.response.status == 404) {
        /* 404: Playlist contains no playable songs */
        return {
          success: false,
          message: "The selected playlist contains no playable songs.",
          sessionId: "",
        };
      } else if (AxiosError.response.status == 409) {
        /* 409: Already Hosting OR Session name taken*/
        if (AxiosError.response.data.Error == "Session name already taken.") {
          return {
            success: false,
            message: "Session name already taken",
            sessionId: "",
          };
        } else if (AxiosError.response.data.Error == "Already a guest.") {
          return {
            success: false,
            message: "You are currently a guest in another session",
            sessionId: "",
          };
        } else {
          return {
            success: false,
            message: "You currently are hosting another session",
            sessionId: "",
          };
        }
      } else {
        /* Other errors such as server connection */
        return { success: false, message: "Unknown error", sessionId: "" };
      }
    }
  },
};
