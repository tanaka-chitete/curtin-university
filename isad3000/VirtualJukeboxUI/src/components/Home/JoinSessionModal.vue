<script lang="ts">
/* Imports */
import axios from "axios";
import { defineComponent } from "vue";
/* Stores */
import { useUser } from "../../stores/user";
import { useSession } from "../../stores/session";
import type { sessionType } from "../../stores/session";
/* Mixins */
import JoinSession from "../../mixins/JoinSession";
export default defineComponent({
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    return { userStore, sessionStore };
  },
  data() {
    return {
      sessions: [] as { distance_to: number; session: sessionType }[],
      error_message: "",
    };
  },
  async created() {
    let jsonResponse:
      | {
          data: { distance_to: number; session: sessionType }[];
          status: number;
        }
      | undefined;
    const request = {
      user_location:
        "POINT(" +
        this.userStore.longitude +
        " " +
        this.userStore.latitude +
        ")",
    };
    try {
      await axios
        .get(import.meta.env.VITE_API_URL + "/vjsession/public/inrange/", {
          params: request,
        })
        .then((response) => (jsonResponse = response));
      if (jsonResponse && jsonResponse.status == 200) {
        this.sessions = jsonResponse.data;
        // MapworksDisplayer.display(); // Pass session store with all the sessions here
      }
    } catch (AxiosError) {
      this.$log.error("Error getting sessions");
    }
  },
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const spotifyModal = document.getElementById("joinsession");
      if (spotifyModal) {
        spotifyModal.style.display = "none";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "joinsession") {
        this.close();
      }
    },
    async joinSession(session_id: string) {
      let joinResponse: {
        success: boolean;
        message: string;
        sessionId: string | undefined;
      };
      if (session_id) {
        joinResponse = await JoinSession.joinSession(session_id);
        if (joinResponse.success) {
          this.$log.info(joinResponse.message);
          this.$router.push("/session" + "?id=" + joinResponse.sessionId);
        } else {
          this.$log.error(joinResponse.message);
          this.error_message = joinResponse.message;
        }
      } else {
        this.error_message = "Please fill all fields";
      }
    },
  },
});
</script>

<template>
  <div id="joinsession" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <div class="top-bar">
        <span class="close" @click="close()">&times;</span>
        <div class="modal-title">Join a Session in Range</div>
      </div>
      <template v-if="sessions.length > 0">
        <div
          class="session"
          v-for="session in sessions.sort(
            (a, b) => a.distance_to - b.distance_to
          )"
          v-bind:key="session.distance_to"
          @click="joinSession(session.session.id)"
        >
          <div class="session-info">
            <div class="session-name">
              Session: {{ session.session.session_name }}
            </div>
            <div class="session-description">
              Description: {{ session.session.session_description }}
            </div>
          </div>
          <div class="distance">
            <template v-if="Math.round(session.distance_to) < 1000">
              {{ Math.round(session.distance_to) }}m away
            </template>
            <template v-else>
              {{ Math.round(session.distance_to / 100) / 10 }}km away
            </template>
          </div>
          <q-icon name="login" class="join-icon" />
        </div>
      </template>
      <template v-else>
        <div id="no-session" class="session-name">No sessions in range</div>
      </template>
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
  margin: 10% auto; /* 15% from the top and centered */
  overflow: hidden; // Prevent connect button from popping out of div when using float:right
}

.modal-title {
  @include text-content-bold;
  height: 3em; // Spacing after title
  width: 50%; // Prevent it from overlapping the close button, preventing it from being clicked
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

.session {
  @include text-small;
  display: flex;
  flex-direction: row;
  align-items: center;
  cursor: pointer;
  height: 10vh;
}

.session:hover {
  background-color: #3a597c;
  border-radius: 10px;
}

.session-info {
  @include text-small;
  display: flex;
  flex-direction: column;
  margin-left: 15px;
  width: 75%;
  align-items: right;
}

.session-name {
  @include text-small;
  width: 20vw;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  text-align: left;
  font-weight: bold;
}
.session-description {
  @include text-small;
  width: 25vw;
  text-overflow: hidden;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.join-icon {
  padding-right: 15px;
  padding-left: 15px;
  font-size: 3vh;
}

@media only screen and (max-device-width: 500px) {
  .modal-content {
    width: 90vw;
    height: fit-content;
    max-height: 70vh;
    position: relative;
    top: 20vw;
    overflow-y: scroll;
  }

  .top-bar {
    @include modal-mobile-top-bar;
  }
  .modal-title {
    @include text-mobile-large;
    width: 100vw;
  }

  .session {
    height: fit-content;
    margin-top: 10px;
  }
  .session:hover {
    height: fit-content;
    margin-top: 10px;
  }

  .session-info {
    width: 50vw;
  }
  .session-name {
    @include text-mobile-large;
    width: 40vw !important;
    overflow-x: scroll !important;
    overflow-x: visible !important;
    text-overflow: clip;
  }
  .distance {
    @include text-mobile-large;
    max-width: 15vw;
  }

  .session-description {
    @include text-mobile-small;
    width: 50vw;
    word-wrap: break-word;
  }

  .close {
    @include modal-mobile-close;
  }

  #no-session {
    width: fit-content !important;
  }
}
</style>
