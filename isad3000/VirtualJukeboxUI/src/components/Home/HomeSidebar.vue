<script lang="ts">
/* Imports */
import { useCookies } from "vue3-cookies";
import { defineComponent } from "vue";
import axios from "axios";
/* Components */
import SpotifyLogin from "./SpotifyLoginModal.vue";
import HostSession from "./HostSessionModal.vue";
import JoinSession from "./JoinSessionModal.vue";
import SpotifyPremium from "./SpotifyPremiumModal.vue";
/* Stores */
import { useUser } from "../../stores/user";
export default defineComponent({
  components: {
    SpotifyLogin,
    HostSession,
    JoinSession,
    SpotifyPremium,
  },
  setup() {
    const userStore = useUser();
    return { userStore };
  },
  methods: {
    checkForSpotify() {
      const { cookies } = useCookies();
      if (cookies.isKey("accesstoken")) {
        const hostModal = document.getElementById("hostsession");
        if (hostModal) {
          hostModal.style.display = "block";
        }
      } else {
        const spotifyModal = document.getElementById("nospotify");
        if (spotifyModal) {
          spotifyModal.style.display = "block";
        }
      }
    },
    joinSession() {
      const joinModal = document.getElementById("joinsession");
      if (joinModal) {
        joinModal.style.display = "block";
      }
    },
    async logout() {
      let jsonResponse: { status: number } | undefined;
      try {
        await axios
          .delete(
            import.meta.env.VITE_API_URL +
              "/user/logout/" +
              this.userStore.id +
              "/"
          )
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          this.$log.info("User successfully logged out");
          this.userStore.id = "";
          this.userStore.username = "";
        }
      } catch (AxiosError: any) {
        if (AxiosError.response.status == 404) {
          this.$log.error("Unknown host ID");
        } else {
          this.$log.error("Unknown Error");
        }
        /* Log user out regardless of if API call eas successful */
        this.userStore.id = "";
        this.userStore.username = "";
      }
    },
    // Open sidebar for mobile
    openNav() {
      const width = Math.max(window.screen.width, window.innerWidth);
      if (width < 500) {
        const sidebar = document.getElementById("mySidenavM");
        if (sidebar) {
          sidebar.style.width = "100vw";
        }
      }
    },
    // Close sidebar for mobile
    closeNav() {
      const width = Math.max(window.screen.width, window.innerWidth);
      if (width < 500) {
        const sidebar = document.getElementById("mySidenavM");
        if (sidebar) {
          sidebar.style.width = "0vw";
        }
      }
    },
  },
});
</script>

<template>
  <SpotifyLogin />
  <SpotifyPremium />
  <HostSession />
  <JoinSession />
  <div id="mySidenavM" class="sidenavM">
    <a class="closebtn" @click="closeNav()"><q-icon name="o_map"></q-icon></a>
    <div class="logo-container">
      <img
        alt="Virtual Jukebox Logo"
        class="logo"
        src="@/assets/logo-new-large.png"
      />
    </div>
    <div class="button-center">
      <div class="button-align">
        <button
          @click="
            joinSession();
            closeNav();
          "
          class="sidebar-button"
        >
          Join a Session
        </button>
      </div>
      <div class="button-align">
        <button
          @click="
            checkForSpotify();
            closeNav();
          "
          class="sidebar-button"
        >
          Host a Session
        </button>
      </div>
    </div>
    <div class="bottom">
      <div class="bottom-div">
        <p id="welcome">Welcome, {{ userStore.username }}</p>
        <a id="logout-link" @click="logout()">Logout</a>
      </div>
    </div>
  </div>

  <span
    class="icon"
    style="font-size: 55px; cursor: pointer"
    @click="openNav()"
  >
    <img
      alt="Virtual Jukebox Logo ICON"
      class="logo-icon"
      src="@/assets/logo-white.png"
    />
  </span>
</template>

<style lang="scss" scoped>
@import "../../assets/_button.scss";
@import "../../assets/container.scss";
@import "../../assets/_typography.scss";

.button-center {
  @include form-center;
}

.button-align {
  @include form-box;
}

.sidebar-button {
  @include button-box;
  background: #00bd7e;
  color: #ffffff;
  cursor: pointer;
  float: right;
  width: 13vw; // Override default width for button-box
}

.sidebar-button:hover {
  background: #ffffff;
  color: #000000;
}

.logo {
  height: 75%;
  width: 75%;
  margin-bottom: 30vh;
  user-select: none;
}

.logo-container {
  text-align: center;
}

.bottom {
  @include text-small;
  position: absolute;
  bottom: 24px;
  left: 28px;
  display: flex;
}

//For Desktop view
@media only screen and (min-device-width: 500px) {
  .closebtn {
    display: none;
  }
  .icon {
    display: none;
  }
  .bottom {
    position: fixed;
    bottom: 25px;
    left: 28px;
    display: flex;
  }
}

//For Mobile view
@media only screen and (max-device-width: 500px) {
  .button-center {
    position: absolute;
    top: 40vh;
    left: 50%;
    margin-right: -50%;
    transform: translate(-50%, -50%);
    width: 80vw;
  }

  .logo-container {
    padding-top: 2vh;
  }

  .sidebar-button {
    width: 80vw;
    font-size: 1.6em;
    height: 2.5em;
  }

  .bottom-div {
    width: 80vw;
    text-align: center;
    padding-bottom: 20vh;
  }

  #welcome {
    display: none;
  }

  #logout-link {
    font-size: 7em;
    color: hsla(160, 100%, 37%, 1) !important;
  }

  /* The side navigation menu */
  .sidenavM {
    height: 100vh; /* 100% Full-height */
    width: 100vw; /* 0 width - change this with JavaScript */
    position: fixed; /* Stay in place */
    z-index: 2; /* Stay on top */
    top: 0; /* Stay at the top */
    left: 0;
    background-color: #111; /* Black*/
    overflow-x: hidden; /* Disable horizontal scroll */
    overflow-y: scroll;
    padding-top: 60px; /* Place content 60px from the top */
    transition: 0.5s; /* 0.5 second transition effect to slide in the sidenav */
  }

  /* The navigation menu links */
  .sidenavM a {
    padding: 8px 8px 8px 32px;
    text-decoration: none;
    font-size: 25px;
    color: #818181;
    display: block;
    transition: 0.3s;
  }

  /* When you mouse over the navigation links, change their color */
  .sidenavM a:hover {
    color: #f1f1f1;
  }

  /* Position and style the close button (top right corner) */
  .sidenavM .closebtn {
    position: absolute;
    top: 0vh;
    right: 2vw;
    font-size: calc(3vh + 4vw);
  }

  .icon {
    position: relative;
    left: 20px;
    bottom: 10px;
    z-index: 0;
    color: #ffffff;
  }
  .logo-icon {
    height: 65px;
  }
}
</style>
