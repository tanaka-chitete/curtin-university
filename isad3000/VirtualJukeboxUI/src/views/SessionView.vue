<script lang="ts">
/* Imports */
import { defineComponent } from "vue";
import axios from "axios";
/* Components */
import NowPlaying from "../components/Session/NowPlaying.vue";
import SessionButtons from "../components/Session/SessionButtons.vue";
import SessionQueueShort from "../components/Session/SessionQueueShort.vue";
import NameEnter from "../components/Login/NameEnter.vue";
import PasswordEnter from "../components/Login/PasswordEnterModal.vue";
/* Stores */
import { HOST_TOKENS, useSession } from "../stores/session";
import { useUser } from "../stores/user";
/* Services */
import SpotifyPlayer from "../services/SpotifyPlayback";
/* Mixins */
import JoinSession from "../mixins/JoinSession";
import LeaveSession from "../mixins/LeaveSession";
import CloseSession from "../mixins/CloseSession";
import GetSession from "../mixins/GetSession";
export default defineComponent({
  components: {
    NameEnter,
    PasswordEnter,
    NowPlaying,
    SessionButtons,
    SessionQueueShort,
  },
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    return { sessionStore, userStore };
  },
  data() {
    return {
      passworded: false,
    };
  },
  mixins: [JoinSession, LeaveSession, CloseSession],
  beforeRouteLeave(to, from, next) {
    /* Only leave the page if page leaving is allowed */
    if (this.sessionStore.allow_leave) {
      next();
      /* Otherwise, show the user confirmation dialogues */
    } else {
      /* Close session dialogue for hosts */
      if (this.userStore.id == this.sessionStore.host_id) {
        const closeModal = document.getElementById("closeSession");
        if (closeModal) {
          closeModal.style.display = "block";
        }
        /* Leave session dialogue for guests */
      } else {
        const leaveModal = document.getElementById("leaveSession");
        if (leaveModal) {
          leaveModal.style.display = "block";
        }
      }
    }
  },
  async created() {
    /* Checks to see if there are any current values in the stores */
    if (this.userStore.id) {
      const uri = new URL(window.location.href);
      const session_id = uri.searchParams.get("id");
      if (session_id) {
        /* The user has loaded the page as the host */
        if (
          this.sessionStore.host_id &&
          this.sessionStore.host_id == this.userStore.id
        ) {
          this.$log.debug("User connected as a host");
          this.setupHost();
          this.sessionStore.setTokens(HOST_TOKENS);
          /* The user has not already loaded the page */
        } else if (!this.sessionStore.host_id) {
          const sessionResponse = await this.checkSessionInfo(session_id);
          /* If the user is re-joining */
          if (sessionResponse.success && sessionResponse.in_session) {
            await this.getSession(session_id);
            this.$log.info("User re-joined the session");
            if (
              this.sessionStore.host_id &&
              this.sessionStore.host_id == this.userStore.id
            ) {
              this.$log.debug("User connected as a host");
              this.setupHost();
            }
            /* The user is joining for the first time, and the session is passworded */
            /* Session joining will be done through the Password Enter modal */
          } else if (sessionResponse.success && sessionResponse.passworded) {
            this.passworded = true;
            /* The user is joining for the first time and the session is not passworded */
          } else if (sessionResponse.success && !sessionResponse.passworded) {
            if (!this.sessionStore.host_id) this.joinSession(session_id);
            this.$log.debug("User connected as a guest");
          } else {
            /* Error has occured when checking the session password */
            this.sessionStore.allowLeave();
            this.$router.push({ name: "home" });
          }
        }
      } else {
        /* The user has navigated to "/session" with no id parameter. */
        this.$log.warn("Invalid session URL");
        this.sessionStore.allowLeave();
        this.$router.push({ name: "home" });
      }
    }
  },
  methods: {
    /*Checks if the user is in a session, and if the session is passworded */
    async checkSessionInfo(session_id: string): Promise<{
      success: boolean;
      passworded: boolean | undefined;
      in_session: boolean | undefined;
    }> {
      let jsonResponse:
        | { status: number; data: { in_session: boolean; is_private: boolean } }
        | undefined;
      const request = { user_id: this.userStore.id };
      try {
        await axios
          .get(
            import.meta.env.VITE_API_URL +
              "/vjsession/insession/" +
              session_id +
              "/",
            { params: request }
          )
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          this.$log.info("Session user information successfully retreived");
          return {
            success: true,
            passworded: jsonResponse.data.is_private,
            in_session: jsonResponse.data.in_session,
          };
        } else {
          /* Every status other than 200 should throw an error, so we should not reach this */
          return {
            success: false,
            passworded: undefined,
            in_session: undefined,
          };
        }
      } catch (AxiosError: any) {
        if (AxiosError.response.status == 404) {
          /* 404 :Session or user not found in database */
          this.$log.error("Session or user not found");
          return {
            success: false,
            passworded: undefined,
            in_session: undefined,
          };
        } else {
          /* other : Any other issues such as no database connection */
          this.$log.error("Unknown error in finding session");
          return {
            success: false,
            passworded: undefined,
            in_session: undefined,
          };
        }
      }
    },
    /*Joins the session, getting all session data */
    async joinSession(session_id: string) {
      const joinResponse: {
        success: boolean;
        message: string;
        sessionId: string | undefined;
      } = await JoinSession.joinSession(session_id);
      if (joinResponse.success) {
        this.$log.info(joinResponse.message);
      } else {
        this.$log.error(joinResponse.message);
        this.sessionStore.allowLeave();
        this.$router.push({ name: "home" });
      }
    },
    /* Returns all session details without a user joining the session.
    Called when a user is rejoining the session */
    async getSession(session_id: string) {
      const getResponse: {
        success: boolean;
        message: string;
      } = await GetSession.getSession(session_id);
      if (getResponse.success) {
        this.$log.info(getResponse.message);
      } else {
        this.$log.error(getResponse.message);
        this.sessionStore.allowLeave();
        this.$router.push({ name: "home" });
      }
    },
    setupHost() {
      this.$log.debug("User connected as a host");
      SpotifyPlayer.methods.playback(); /*Start the Spotify Web Playback SDK*/
    },
  },
});
</script>

<template>
  <div v-if="!userStore.username">
    <img src="../assets/login-background-new.jpg" class="background" />
    <NameEnter />
  </div>
  <template v-else>
    <div class="session-container">
      <PasswordEnter v-if="passworded" />
      <NowPlaying />
      <SessionQueueShort />
      <SessionButtons />
    </div>
  </template>
</template>

<style lang="scss" scoped>
@import "../assets/_container.scss";
.background {
  @include background-image;
}
.session-container {
  padding: 10vh;
  padding-bottom: 0;
  position: absolute;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

@media only screen and (max-device-width: 500px) {
  .session-container {
    width: 99vw;
    max-width: 90vw;
    height: 99vh;
    max-height: 90vh;
    overflow-y: hidden;
    overflow-x: hidden;
    position: relative;
    right: 3vw;
  }
}
</style>
