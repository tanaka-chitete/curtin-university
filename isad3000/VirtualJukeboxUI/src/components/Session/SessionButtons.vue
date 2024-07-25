<script lang="ts">
/*Buttons at the bottom of the session screen. Each button will bring up a modal.
Which buttons are shown depends on wether the user is a guest or a host */
/* Imports */
import { defineComponent } from "vue";
/* Components */
import SessionPlaylist from "./SessionPlaylist.vue";
import SessionQueueLong from "./SessionQueueLong.vue";
import CloseSession from "./CloseSessionModal.vue";
import ConfirmSession from "./ConfirmSessionModal.vue";
import LeaveSession from "./LeaveSessionModal.vue";
import ShareSession from "./ShareSessionModal.vue";
import ChatModal from "./ChatModal.vue";
import SessionClosed from "./SessionClosedModal.vue";
import SessionSettings from "./SessionSettingsModal.vue";
import WebSocketClosed from "./WebSocketClosed.vue";
/* Stores */
import { useUser } from "../../stores/user";
import { useSession } from "../../stores/session";
import { usePlayer } from "../../stores/player";
import { useChat } from "../../stores/chat";
export default defineComponent({
  components: {
    SessionPlaylist,
    SessionQueueLong,
    CloseSession,
    ConfirmSession,
    LeaveSession,
    ShareSession,
    ChatModal,
    SessionClosed,
    SessionSettings,
    WebSocketClosed,
  },
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    const playerStore = usePlayer();
    const chatStore = useChat();
    return { userStore, sessionStore, playerStore, chatStore };
  },
  data() {
    return {
      skipping: false,
    };
  },
  methods: {
    /* Skips the currently playing song */
    async skipSong() {
      if (!this.playerStore.swapping_songs && !this.skipping) {
        this.skipping = true;
        this.playerStore.swapping_songs = true;
        this.sessionStore.websocket.send(
          JSON.stringify({ type: "song_changed" })
        );
        await new Promise((resolve) => setTimeout(resolve, 500));
        this.skipping = false;
      }
    },
    /* Function executed when add song / show playlist button is clicked */
    showPlaylist() {
      const hostModal = document.getElementById("showplaylist");
      if (hostModal) {
        hostModal.style.display = "block";
      }
    },
    /* Function executed when show queue button is clicked */
    showQueue() {
      const hostModal = document.getElementById("showqueue");
      if (hostModal) {
        hostModal.style.display = "block";
      }
    },
    async shareSession() {
      const hostModal = document.getElementById("shareSession");
      if (hostModal) {
        hostModal.style.display = "block";
      }
    },
    async sessionSettings() {
      const sessionSettings = document.getElementById("sessionSettings");
      if (sessionSettings) {
        sessionSettings.style.display = "block";
      }
    },
    /* Function executed when close session button is clicked */
    async closeSession() {
      const closeModal = document.getElementById("closeSession");
      if (closeModal) {
        closeModal.style.display = "block";
      }
    },
    /* Function executed when leave session button is clicked */
    async leaveSession() {
      const leaveModal = document.getElementById("leaveSession");
      if (leaveModal) {
        leaveModal.style.display = "block";
      }
    },
    /* Function executed when chat button is clicked */
    async showChat() {
      const chatModal = document.getElementById("chatmodal");
      if (chatModal) {
        chatModal.style.display = "block";
        this.chatStore.seenMessages();
      }
    },
  },
});
</script>

<template>
  <SessionPlaylist />
  <SessionQueueLong />
  <CloseSession v-if="sessionStore.host_id == userStore.id" />
  <LeaveSession v-else />
  <SessionClosed />
  <ShareSession />
  <ChatModal />
  <SessionSettings />
  <WebSocketClosed />
  <ConfirmSession v-if="sessionStore.host_id == userStore.id" />
  <div class="bottom-bar">
    <div v-if="sessionStore.host_id == userStore.id" class="noselect">
      <div class="dropup">
        <button class="dropbtn">
          <q-icon name="menu" />
        </button>
        <div class="dropup-content">
          <!-- Close Session -->
          <q-icon
            @click="closeSession()"
            name="disabled_by_default"
            class="button-icon"
          >
            <span class="tooltiptext">Close Session</span>
          </q-icon>
          <!-- Session Settings. -->
          <q-icon @click="sessionSettings()" name="info" class="button-icon">
            <span class="tooltiptext">View Information</span>
          </q-icon>
          <!-- Share. -->
          <q-icon @click="shareSession()" name="share" class="button-icon">
            <span class="tooltiptext">Share</span>
          </q-icon>
        </div>
      </div>
    </div>
    <div v-else class="noselect">
      <!-- Leave Session -->
      <q-icon @click="leaveSession()" name="logout" class="button-icon">
        <span class="tooltiptext">Leave Session</span>
      </q-icon>
    </div>
    <div v-if="sessionStore.host_id == userStore.id" class="noselect">
      <!-- Play/pause Song -->
      <div class="playback-controls" id="playback-controls">
        <q-icon
          v-if="playerStore.paused"
          name="play_circle"
          class="button-icon"
        />
        <q-icon v-else name="pause_circle" class="button-icon" />
      </div>
      <!-- Skip/next -->
      <q-icon @click="skipSong()" name="skip_next" class="button-icon" />
    </div>
    <div class="noselect">
      <!-- Add Song / Show playlist -->
      <q-icon @click="showPlaylist()" name="playlist_add" class="button-icon">
        <span class="tooltiptext">Add Song</span>
      </q-icon>
      <!-- View Queue -->
      <q-icon @click="showQueue()" name="queue_music" class="button-icon">
        <span class="tooltiptext">View Queue</span>
      </q-icon>
      <!-- View Chat (No new messages) -->
      <q-icon
        v-if="chatStore.new_messages == 0"
        @click="showChat()"
        name="o_chat"
        class="button-icon"
      >
        <span class="tooltiptext">Chat</span>
      </q-icon>
      <!-- View Chat (Unread messages) -->
      <q-icon
        v-else
        @click="showChat()"
        name="o_mark_unread_chat_alt"
        class="button-icon"
      >
        <span class="tooltiptext">Chat</span>
      </q-icon>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "../../assets/_typography.scss";

.bottom-bar {
  display: flex;
  justify-content: space-between;
  width: 80vw;
}

.button-icon {
  font-size: 4vw;
  color: #00bd7e;
  cursor: pointer;
}

.button-icon:hover {
  color: #ffffff;
  transition: 0.1s;
}

.playback-controls {
  display: inline;
}

.button-icon .tooltiptext {
  @include text-small;
  text-align: center;
  visibility: hidden;
  position: absolute;
  z-index: 1;
  bottom: 100%;
  left: 50%;
  margin-left: -60px;
  width: 120px;
}

.button-icon:hover .tooltiptext {
  visibility: visible;
}

.noselect {
  user-select: none;
}

// For Destop view
@media only screen and (min-device-width: 501px) {
  // Hide hamburger menu
  .dropbtn {
    display: none;
  }

  .dropup-content a {
    background-color: #001822;
  }
}

// For mobile view
@media only screen and (max-device-width: 500px) {
  .bottom-bar {
    position: fixed;
    left: 10px;
    bottom: 1px;
    width: 95vw;
    max-width: 100vw;
    background-color: #001822;
  }

  .button-icon {
    font-size: 35px;
  }

  /* Dropup Button */
  .dropbtn {
    background-color: #3498db00;
    color: #00bd7e;
    font-size: 30px;
    border: none;
    padding-right: 18vw;
  }

  /* The container <div> - needed to position the dropup content */
  .dropup {
    position: relative;
    display: inline-block;
  }

  /* Dropup content (Hidden by Default) */
  .dropup-content {
    display: none;
    position: absolute;
    bottom: 40px;
    background-color: #001822;
    box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
    z-index: 1;
  }

  /* Links inside the dropup */
  .dropup-content a {
    color: black;
    padding: 20px 20px;
    text-decoration: none;
    display: block;
  }

  /* Change color of dropup links on hover */
  .dropup-content a:hover {
    background-color: rgb(55, 123, 172);
  }

  /* Show the dropup menu on hover */
  .dropup:hover .dropup-content {
    display: flex;
    width: 50px;
    height: 200px;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
    left: 10px;
  }

  .tooltiptext {
    display: none;
  }
}
</style>
