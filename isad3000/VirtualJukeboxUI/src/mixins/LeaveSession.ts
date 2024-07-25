/* Makes an API call to leave a session */
/* Imports */
import axios from "axios";
/* Stores */
import { useSession } from "../stores/session";
import { useUser } from "../stores/user";
export default {
  async leaveSession(): Promise<{ success: boolean; message: string }> {
    const sessionStore = useSession();
    const userStore = useUser();
    const request = { guest_id: userStore.id };
    let jsonResponse: { status: number } | undefined;
    let apiURL: string = import.meta.env.VITE_API_URL + "/vjsession/";
    try {
      /* If a session id exists, use that session id */
      if (sessionStore.session_id) {
        apiURL += "leave/" + sessionStore.session_id + "/";
      } else {
        apiURL += "user/leave/";
      }
      await axios
        .put(apiURL, request)
        .then((response) => (jsonResponse = response));
      if (jsonResponse && jsonResponse.status == 200) {
        sessionStore.websocket.close();
        return { success: true, message: "Session successfully left" };
      } else {
        /* Every status other than 200 should throw an error, so we should not reach this */
        return {
          success: false,
          message: "Failed to leave session",
        };
      }
    } catch (AxiosError: any) {
      if (AxiosError.response.status == 400) {
        /* 400 : Invalid request */
        return { success: false, message: "Invalid guest or session ID" };
      } else if (AxiosError.response.status == 404) {
        /* 404 : Cannot find host or session ID */
        return {
          success: false,
          message: "Unknown guest or session ID",
        };
      } else if (AxiosError.response.status == 409) {
        /* 409 : Guest is currently not in the session */
        return {
          success: false,
          message: "Guest is currently not in the session",
        };
      } else {
        /* Other : Any other issues such as no database connection */
        return { success: false, message: "Unknown error" };
      }
    }
  },
};
