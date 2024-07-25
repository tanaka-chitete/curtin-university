<script lang="ts">
/* Modal which allows the user to host a session by specifying a playlist, name, ect... */
/* Imports */
import axios from "axios";
import { useCookies } from "vue3-cookies";
import { defineComponent } from "vue";
/* Stores */
import { useUser } from "../../stores/user";
import { useSession } from "../../stores/session";
/* Services */
import SpotifyService from "../../services/SpotifyTokens";
/* Mixins */
import HostSession from "../../mixins/HostSession";
import CloseSession from "../../mixins/CloseSession";
import LeaveSession from "../../mixins/LeaveSession";
/* Types */
import type { playlistType } from "../../stores/session";
export default defineComponent({
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    return { userStore, sessionStore };
  },
  data() {
    return {
      is_private: false,
      session_name: "",
      session_password: "",
      session_location: "",
      session_description: "",
      connection_range: 50,
      spotify_playlist_id: "",
      playlists: [] as playlistType[],
      error_message: "",
      loading: false,
      debounce_enabled: false,
    };
  },
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const spotifyModal = document.getElementById("hostsession");
      if (spotifyModal) {
        spotifyModal.style.display = "none";
      }
    },
    /* Gets all the user's Spotify playlists using their access token */
    async getPlaylists() {
      if (!this.playlists?.length) {
        /*We only want to get playlists once, so if there are already playlists loaded don't make API call */
        const { cookies } = useCookies();
        let jsonResponse:
          | { data: { playlists: playlistType[] }; status: number }
          | undefined;
        await SpotifyService.methods.checkTokens();
        const request = { access_token: cookies.get("accesstoken") };
        try {
          await axios
            .get(import.meta.env.VITE_API_URL + "/spotify/get-user-playlists", {
              params: request,
            })
            .then((response) => (jsonResponse = response));
          if (jsonResponse && jsonResponse.status == 200) {
            this.$log.info("User's playlists successfully retreived");
            this.playlists = jsonResponse.data.playlists;
            if (this.playlists.length == 0) {
              this.error_message = "Please add a playlist on Spotify";
            }
          }
        } catch (AxiosError) {
          this.$log.error("Spotify playlists failed to fetch");
        }
      }
    },
    /* Creates a session based on inputted user information */
    async submitSession() {
      /* Make sure we have an entered name, playlist id and description before we submit API request */
      if (this.loading) return;
      if (this.is_private && !this.session_password) {
        this.error_message = "Password cannot be blank";
        return;
      }
      if (
        this.session_name &&
        this.spotify_playlist_id &&
        this.session_description
      ) {
        if (this.session_name.length > 50) {
          this.error_message = "Session name is too long";
        } else if (this.session_description.length > 200) {
          this.error_message = "Session description is too long";
        } else {
          this.loading = true;
          const loader = this.$loading.show({});
          const hostResponse: {
            success: boolean;
            message: string;
            sessionId: string | undefined;
          } = await HostSession.hostSession(
            this.is_private,
            this.session_name,
            this.session_password,
            this.session_description,
            "POINT(" +
              this.userStore.longitude +
              " " +
              this.userStore.latitude +
              ")",
            this.connection_range,
            this.spotify_playlist_id
          );
          if (hostResponse.success) {
            this.$log.info(hostResponse.message);
            loader.hide();
            this.$router.push("/session" + "?id=" + hostResponse.sessionId);
          } else {
            this.$log.error(hostResponse.message);
            this.loading = false;
            loader.hide();
            this.error_message = hostResponse.message;
          }
        }
      } else {
        this.error_message = "Please fill all fields";
      }
    },
    async checkSessionName() {
      if (!this.debounce_enabled) {
        this.debounce_enabled = true;
        let jsonResponse:
          | { data: { name_taken: boolean }; status: number }
          | undefined;
        const request = { session_name: this.session_name };
        await axios
          .get(import.meta.env.VITE_API_URL + "/vjsession/taken/", {
            params: request,
          })
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          if (jsonResponse.data.name_taken) {
            this.error_message = "Session name already in use";
          } else {
            this.error_message = "";
          }
        }
        await new Promise((resolve) => setTimeout(resolve, 100));
        this.debounce_enabled = false;
      }
    },
    async closeSession() {
      const closeResponse: { success: boolean; message: string } =
        await CloseSession.closeSession();
      if (closeResponse.success) {
        this.$log.info(closeResponse.message);
        this.error_message = "";
      } else {
        this.$log.error(closeResponse.message);
      }
    },
    async leaveSession() {
      const leaveResponse: { success: boolean; message: string } =
        await LeaveSession.leaveSession();
      if (leaveResponse.success) {
        this.$log.info(leaveResponse.message);
        this.error_message = "";
      } else {
        this.$log.error(leaveResponse.message);
      }
    },
  },
});
</script>

<template>
  <div id="hostsession" class="modal">
    <div class="modal-content">
      <div class="top-bar">
        <span class="close" @click="close()"> &times;</span>
        <div class="modal-title">Host a Session</div>
      </div>
      <label>Session Details </label>
      <div
        class="list-down"
        style="display: flex; flex-direction: column; align-items: flex-start"
      >
        <!-- Add a playlist -->
        <select
          v-model="spotify_playlist_id"
          class="login-box"
          @click="getPlaylists()"
        >
          <option value="" disabled selected>Add a playlist</option>
          <template v-for="playlist in playlists">
            <option
              v-if="playlist.total_tracks"
              v-bind:value="playlist.playlist_id"
              v-bind:key="playlist.playlist_id"
            >
              {{ playlist.name }} - {{ playlist.total_tracks }} Songs
            </option>
          </template>
        </select>
        <!-- Add a name -->
        <input
          placeholder="Add a name"
          class="login-box"
          v-model="session_name"
          @keyup="checkSessionName()"
        />
        <!-- Add a description -->
        <input
          placeholder="Add a description"
          class="login-box"
          v-model="session_description"
        />
        <!-- Configure join radius -->
        <label
          >Connection Range
          <q-icon name="help" class="help-icon">
            <span id="tp1" class="tooltiptext"
              >The maximum distance a user can be to join your session.</span
            >
          </q-icon>
        </label>
        <select v-model="connection_range" class="login-box">
          <option
            v-for="radius in 5"
            v-bind:key="2 ** (radius - 1) * 25"
            v-bind:value="2 ** (radius - 1) * 25"
          >
            {{ 2 ** (radius - 1) * 25 }} m
          </option>
          <option v-bind:key="40000000" v-bind:value="40000000">
            Unlimited
          </option>
        </select>
        <label
          >Visibility
          <q-icon name="help" class="help-icon">
            <span id="tp2" class="tooltiptext"
              >A public session will show on the map. <br />
              A private session will not show up on the map.</span
            >
          </q-icon>
        </label>
        <!-- Configure visibility -->
        <select v-model="is_private" class="login-box">
          <option v-bind:value="false">Public</option>
          <option v-bind:value="true">Private</option>
        </select>
        <!-- Add a password -->
        <input
          v-if="is_private"
          placeholder="Add a password"
          type="password"
          class="login-box"
          v-model="session_password"
        />
        <br />
      </div>
      <button @click="submitSession()" class="host-box">Host</button>
      <!-- If there is an error, display it here. Blank by default-->
      <div class="error">{{ error_message }}</div>
      <div v-if="error_message === 'You currently are hosting another session'">
        <p class="error">
          Would you like to <a @click="closeSession()">close it?</a>
        </p>
      </div>
      <div
        v-if="error_message === 'You are currently a guest in another session'"
      >
        <p class="error">
          Would you like to <a @click="leaveSession()">leave it?</a>
        </p>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "../../assets/modal.scss";
@import "../../assets/_button.scss";
@import "../../assets/_typography.scss";
@import "../../assets/_container.scss";
.modal {
  @include modal;
}

/* Modal Content/Box */
.modal-content {
  @include modal-content;
  overflow: hidden; // Prevent host button from popping out of div when using float:right
}

.modal-title {
  @include text-content-bold;
  height: 3em; // Spacing after title
  width: 50%; // Prevent it from overlapping the close button, preventing it from being clicked
}

.modal-sub-title {
  @include text-small-bold;
  height: 2em; // Spacing after sub-title
}

label {
  @include text-small;
}

/* The Close Button */
.close {
  @include text-content;
  color: #00bd7e;
  float: right;
  background: none;
  border: none;
}

.close:hover,
.close:focus {
  color: #ffffff;
  text-decoration: none;
  cursor: pointer;
}

/* The 'Host' box */
.host-box {
  @include button-box;
  background: #00bd7e;
  color: #ffffff;
  cursor: pointer;
  float: right;
  width: 10vw; // Override default width for button-box
}

.host-box:hover {
  background: #ffffff;
  color: #000000;
}

/* The 'Add a name' and 'Add a description' input fields */
.login-box {
  @include button-box;
  background: #ffffff;
  color: #000000;
  border-radius: 1vh; // Override default button-box border radius
  text-align: left; // Override default text-alignment
  padding-left: 1vh; // Override default padding
  width: 100%; // Override default width to span entire popup
}

.login-box:focus {
  outline: none;
  /* Change border colour */
  border: 3px solid #188ac3;
}

label {
  display: flex;
}

::placeholder {
  color: #a0acbc;
}

/* The 'Add a playlist', 'Configure join radius', and 'Configure visibility' drop downs */
.select-box {
  @include select-box;
  text-align: left; // Override default text-alignment
  padding-left: 1vh; // Override default padding
  width: 100%; // Override default width to span entire popup
}

.select-box:focus {
  outline: none;
  /* Change border colour */
  box-shadow: inset 0px 0px 0px 3px #188ac3;
}

.help-icon {
  margin: auto;
  margin-left: 8px;
  cursor: pointer;
}

.help-icon .tooltiptext {
  @include text-small;
  text-align: center;
  visibility: hidden;
  position: absolute;
  z-index: 1;
  bottom: 100%;
  left: 50%;
  margin-left: -60px;
  background: #188ac1;
  padding: 8px;
  border-radius: 4px;
}

.help-icon:hover .tooltiptext {
  visibility: visible;
}

.error {
  @include text-small-bold;
  color: red;
  display: inline;
}

@media only screen and (max-device-width: 500px) {
  .modal-content {
    width: 88vw;
    position: relative;
    bottom: 5vh;
  }

  .top-bar {
    @include modal-mobile-top-bar;
  }
  .modal-title {
    @include text-mobile-large;
    width: 90vw;
  }
  .select-box,
  .login-box,
  .modal-sub-title {
    @include text-mobile-small;
    height: 50px;
  }

  label {
    @include text-mobile-small;
  }

  .close {
    @include modal-mobile-close;
  }
  .host-box {
    @include text-mobile-small;
    width: 77vw;
  }
  .error {
    @include text-mobile-small;
  }
  #tp1 {
    @include text-mobile-small;
    width: 70vw;
    white-space: normal;
    height: fit-content;
    margin-left: -35vw;
  }
  #tp2 {
    @include text-mobile-small;
    width: 70vw;
    white-space: normal;
    height: fit-content;
  }
}
</style>
