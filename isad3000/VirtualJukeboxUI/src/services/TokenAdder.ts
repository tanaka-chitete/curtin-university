/* Makes an API call to leave a session */
/* Imports */
import axios from "axios";
import { inject } from "vue";
/* Stores */
import { useSession, MAX_TOKENS } from "../stores/session";
import { useUser } from "../stores/user";
export default {
  startTokenInterval() {
    const user_id = useUser().id;
    const sessionStore = useSession();
    const logger: any = inject("vuejs3-logger");
    setInterval(() => this.addToken(user_id, sessionStore, logger), 60000);
  },
  async addToken(user_id: string, sessionStore: any, logger: any) {
    const request = { user_id: user_id };
    let jsonResponse: { status: number } | undefined;
    if (sessionStore.tokens < MAX_TOKENS) {
      try {
        await axios
          .put(
            import.meta.env.VITE_API_URL +
              "/vjsession/addtoken/" +
              sessionStore.session_id +
              "/",
            request
          )
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          /* Success */
          sessionStore.addToken();
          logger?.info("Token added");
        }
      } catch (AxiosError: any) {
        if (AxiosError.response.status == 400) {
          /* 400 : Invalid request */
          logger?.warn("Invalid request");
        } else if (AxiosError.response.status == 401) {
          /* 401 : User not in session */
          logger?.warn("User not in session");
        } else if (AxiosError.response.status == 404) {
          /* 404 : wrong user or session id */
          logger?.warn("Wrong user or session id");
          sessionStore.allow_leave = true;
          const sessionClosedModal = document.getElementById("sessionClosed");
          if (sessionClosedModal) {
            sessionClosedModal.style.display = "block";
          }
          sessionStore.websocket.close();
        } else {
          /* Other : Invalid request */
          logger?.warn("Unknown error");
        }
      }
      /* If the user has no token, we check to see if the session still exists */
    } else {
      let jsonResponse:
        | { status: number; data: { exists: boolean } }
        | undefined;
      try {
        await axios
          .get(
            import.meta.env.VITE_API_URL +
              "/vjsession/exists/" +
              sessionStore.session_id +
              "/"
          )
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          if (!jsonResponse.data.exists) {
            sessionStore.allow_leave = true;
            const sessionClosedModal = document.getElementById("sessionClosed");
            if (sessionClosedModal) {
              sessionClosedModal.style.display = "block";
            }
            sessionStore.websocket.close();
          }
        }
      } catch (AxiosError: any) {
        if (AxiosError.response.status == 400) {
          /* 400 : Invalid request */
          logger?.warn("Invalid request");
        }
      }
    }
  },
};
