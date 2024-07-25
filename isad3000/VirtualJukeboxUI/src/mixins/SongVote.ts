/* Makes an API call to leave a session */
/* Imports */
import axios from "axios";
/* Stores */
import { useSession } from "../stores/session";
import { useUser } from "../stores/user";
export default {
  async songVote(
    song_id: string | number
  ): Promise<{ success: boolean; message: string }> {
    const sessionStore = useSession();
    const userStore = useUser();
    const session_id = sessionStore.session_id;
    const request = { user_id: userStore.id, spotify_song_id: song_id };
    let jsonResponse: { status: number } | undefined;
    try {
      await axios
        .put(
          import.meta.env.VITE_API_URL +
            "/vjsession/upvote/" +
            session_id +
            "/",
          request
        )
        .then((response) => (jsonResponse = response));
      if (jsonResponse && jsonResponse.status == 200) {
        sessionStore.voted.add(
          song_id
        ); /* Add the song to the set of liked songs */
        /* If API request successful, send to all users on websockets */
        sessionStore.websocket.send(
          JSON.stringify({ type: "song_vote", song_id: song_id })
        );
        return { success: true, message: "Song successfully voted for" };
      } else {
        /* Every status other than 200 should throw an error, so we should not reach this */
        return { success: false, message: "Error occured when song voted" };
      }
    } catch (AxiosError: any) {
      switch (AxiosError.response.status) {
        case 400:
          /* 400: Invalid request */
          return {
            success: false,
            message: "One or more fields contains invalid data",
          };
        case 401:
          /* 401: host or guest does not have enough credits, or not in session */
          return {
            success: false,
            message: "User is currently not in the session",
          };
        case 404:
          /* 404: user id or session id does not exist, or invalid song id */
          if (
            AxiosError.response.data.Error == "User id provided does not exist."
          ) {
            return {
              success: false,
              message: "User id provided does not exist.",
            };
          } else {
            return {
              success: false,
              message: "Session id provided does not exist.",
            };
          }
        case 409:
          /* 409: song not in the queue. */
          return {
            success: false,
            message: "Song is not in the queue",
          };
        default:
          /* Other : Any other issues such as no database connection */
          return {
            success: false,
            message: "One or more fields contains invalid data",
          };
      }
    }
  },
};
