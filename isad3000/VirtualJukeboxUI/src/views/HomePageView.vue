<script lang="ts">
/* Imports */
import { defineComponent } from "vue";
/* Components */
import NameEnter from "../components/Login/NameEnter.vue";
import HomeSidebar from "../components/Home/HomeSidebar.vue";
import MapDisplay from "../components/Home/MapDisplay.vue";
import LeaveJoined from "../components/Home/LeaveModal.vue";
import CloseHosted from "../components/Home/CloseModal.vue";
import SessionDisplay from "../components/Home/SessionDisplayModal.vue";
import PrivacyPolicy from "../components/Login/PrivacyPolicyModal.vue";
/* Stores */
import { useUser } from "../stores/user";
import { useSession } from "../stores/session";
import SpotifyService from "../services/SpotifyTokens";
import axios from "axios";
export default defineComponent({
  components: {
    NameEnter,
    HomeSidebar,
    MapDisplay,
    LeaveJoined,
    CloseHosted,
    SessionDisplay,
    PrivacyPolicy,
  },
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    return { userStore, sessionStore };
  },
  async created() {
    await SpotifyService.methods.getSpotifyTokens();
  },
  mounted() {
    if (this.sessionStore.host_id) {
      /*If the browser currently contains information about a session */
      this.$log.debug("Browser contains session data. Reloading...");
      location.reload();
    } else if (this.userStore.id) {
      /*If the user has logged in*/
      this.userStore.findLocation();
      this.checkUserStatus();
    } else {
      this.userStore.findLocation();
    }
  },
  methods: {
    async checkUserStatus() {
      let jsonResponse:
        | {
            status: number;
            data: { in_session: boolean; is_host: boolean; session_id: string };
          }
        | undefined;
      const request = { user_id: this.userStore.id };
      try {
        await axios
          .get(import.meta.env.VITE_API_URL + "/vjsession/user/insession/", {
            params: request,
          })
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          this.$log.info("User information successfully retreived");
          if (jsonResponse.data.in_session && jsonResponse.data.is_host)
            this.showCloseSession(jsonResponse.data.session_id);
          if (jsonResponse.data.in_session && !jsonResponse.data.is_host)
            this.showLeaveSession(jsonResponse.data.session_id);
        }
      } catch (AxiosError: any) {
        if (AxiosError.response.status == 404) {
          this.userStore.clearUserInfo();
        }
      }
    },
    showCloseSession(session_id: string) {
      this.sessionStore.session_id = session_id;
      const closeHosted = document.getElementById("closeHosted");
      if (closeHosted) {
        closeHosted.style.display = "block";
      }
    },
    showLeaveSession(session_id: string) {
      this.sessionStore.session_id = session_id;
      const leaveJoined = document.getElementById("leaveJoined");
      if (leaveJoined) {
        leaveJoined.style.display = "block";
      }
    },
  },
});
</script>

<template>
  <main>
    <div v-if="userStore.username">
      <LeaveJoined />
      <CloseHosted />
      <SessionDisplay />
      <div class="sidenav">
        <HomeSidebar />
      </div>
      <MapDisplay />
    </div>
    <div v-else>
      <img src="../assets/login-background-new.jpg" class="background" />
      <NameEnter />
      <PrivacyPolicy />
    </div>
  </main>
</template>

<style lang="scss" scoped>
@import "../assets/_container.scss";
.background {
  @include background-image;
}

/* The sidebar menu */
.sidenav {
  height: 100%; /* Full-height */
  width: 15%; /* Set the width of the sidebar */
  position: fixed; /* Fixed Sidebar (stay in place on scroll) */
  z-index: 2; /* Stay on top */
  top: 0; /* Stay at the top */
  left: 0;
  background-color: var(--color-background);
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 20px;
  min-width: 240px;
  float: left;
}

@media only screen and (max-device-width: 500px) {
  .sidenav {
    height: 90px; /* Full-height */
    width: 90px !important; /* Set the width of the sidebar */
    top: 20px; /* Stay at the top */
    left: 20px;
    overflow-y: hidden; /* Disable horizontal scroll */
    padding-top: 20px;
    min-width: 2vw;
    border-radius: 50%;
    background-color: hsla(160, 100%, 37%, 1);
  }
}
</style>
