<script lang="ts">
/* Popup modal which is seen when the user clicks the "view chat" button
Shows all chat messages sent since user joined session, 
allows user to send new chat mesages */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useChat } from "../../stores/chat";
import { useSession } from "../../stores/session";
import { useUser } from "../../stores/user";
export default defineComponent({
  setup() {
    const chatStore = useChat();
    const sessionStore = useSession();
    const userStore = useUser();
    return { chatStore, sessionStore, userStore };
  },
  data() {
    return {
      typed_message: "",
    };
  },
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const chatmodal = document.getElementById("chatmodal");
      if (chatmodal) {
        chatmodal.style.display = "none";
        this.chatStore.chat_open = false;
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "chatmodal") {
        this.close();
      }
    },
    async sendMessage() {
      if (this.typed_message.trim()) {
        this.sessionStore.websocket.send(
          JSON.stringify({
            type: "chat_message",
            message: this.typed_message,
            name: this.userStore.username,
          })
        );
        this.typed_message = "";
      }
    },
  },
});
</script>

<template>
  <div id="chatmodal" class="modal-fixed" @click="clickOutside($event)">
    <div class="modal-fixed-content">
      <div class="top-bar">
        <span class="close" @click="close()"> &times;</span>
        <div class="modal-title">Chat</div>
      </div>
      <!-- Show popup message if there are no messages -->
      <div v-if="chatStore.chat_messages.length == 0" class="queue">
        <div class="modal-text">Send a message to get this party started!</div>
      </div>
      <!-- Show the messages in the chat -->
      <div v-else class="message-container" id="msg-container">
        <template v-for="message in chatStore.chat_messages">
          <div
            v-if="message.self"
            class="self-message"
            v-bind:key="message.message"
          >
            <div class="message">{{ message.message }}</div>
            <div class="username">{{ message.username }}</div>
            <div class="timestamp">{{ message.timestamp }}</div>
          </div>
          <div v-else v-bind:key="message.username" class="other-message">
            <div class="other-messages">{{ message.message }}</div>
            <div class="other-username">{{ message.username }}</div>
            <div class="other-timestamp">{{ message.timestamp }}</div>
          </div>
        </template>
      </div>
      <div class="message-contain">
        <form onsubmit="return false;">
          <input
            placeholder="Add a message"
            v-model="typed_message"
            class="message-input"
          />
          <button @click="sendMessage()" class="message-send">Send</button>
        </form>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "../../assets/typography.scss";
@import "../../assets/_modal.scss";
@import "../../assets/_button.scss";
@import "../../assets/_container.scss";

.modal-fixed {
  @include modal-fixed;
}

/* Modal Content/Box */
.modal-fixed-content {
  @include modal-fixed-content;
  overflow-x: none; // Prevent send button from popping out of div
  display: flex;
  flex-direction: column;
}

.modal-title {
  @include text-content-bold;
  width: 50%; // Prevent it from overlapping the close button, preventing it from being clicked
}

.modal-sub-title {
  @include text-small-bold;
  height: 4vh; // Spacing after sub-title
}

.modal-text {
  @include text-small;
  color: #ffffff;
}

/* Close button */
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

.message-container,
.queue {
  @include text-small;
  display: flex;
  flex-direction: column;
  height: 75vh;
  overflow-y: none;
  overflow-x: hidden;
}

.message-input {
  @include button-box;
  background: #ffffff;
  color: #000000;
  border-radius: 10px;
  width: 80%;
  text-align: right;
  padding-right: 1vw;
}

.message-send {
  @include button-box;
  background: #ffffff;
  color: #000000;
  border-radius: 10px;
  width: 15%;
  float: right;
}

.message-send:hover {
  @include button-box;
  cursor: pointer;
  background: #00bd7e;
  color: #ffffff;
  border-radius: 10px;
  width: 15%;
}

.username {
  @include text-extra-small-bold;
  padding-right: 1vw;
}

.timestamp {
  @include text-extra-small-bold;
  padding-right: 1vw;
}

.other-username {
  @include text-extra-small-bold;
  padding-left: 1vw;
}

.other-timestamp {
  @include text-extra-small-bold;
  padding-left: 1vw;
}

.message {
  background-color: #00bd7e;
  margin-top: 15px;
  border-radius: 10px 10px 0px 10px;
  padding-top: 10px;
  padding-bottom: 10px;
  padding-right: 1vw;
  padding-left: 1vw;
  overflow: hidden;
  word-wrap: break-word;
}

.other-messages {
  width: 50%;
  padding-left: 1vw;
  padding-right: 1vw;
  margin-top: 15px;
  padding-top: 10px;
  padding-bottom: 10px;
  background-color: #188ac1;
  border-radius: 10px 10px 10px 0px;
  word-wrap: break-word;
}

.self-message {
  text-align: right;
  width: 50%;
  margin-left: 50%;
  float: right;
}

.message-contain {
  padding-top: 2vh;
}

@media only screen and (max-device-width: 500px) {
  .modal-fixed-content {
    width: 90vw;
    height: 84vh;
    position: relative;
    overflow-y: hidden;
    overflow-x: hidden;
  }
  .modal-title {
    @include text-mobile-large;
    width: 80vw;
  }

  .message-input,
  .message-send {
    @include text-mobile-large;
    margin-top: 10px;
    width: 80vw;
    height: 40px;
    text-align: center;
  }

  .message-send:hover {
    @include text-mobile-large;
    background: #00bd7e;
    color: #ffffff;
    border-radius: 10px;
    width: 80vw;
  }

  .modal-sub-title {
    @include text-mobile-small;
  }

  .modal-text {
    @include text-mobile-large;
    width: fit-content;
  }

  .message-contain {
    background-color: #24374e;
    width: 82vw !important;
    padding-bottom: 10px;
  }

  .top-bar {
    @include modal-mobile-top-bar;
  }

  .close {
    @include modal-mobile-close;
  }

  .username,
  .timestamp,
  .other-username,
  .other-timestamp {
    @include text-mobile-small;
  }

  .message {
    width: 65vw;
    margin-left: -27vw;
    padding-right: 30px;
    padding-left: 30px;
    margin-top: 15px;
    padding-top: 10px;
    padding-bottom: 10px;
    background-color: #00bd7e;
    border-radius: 20px;
    word-wrap: break-word;
    @include text-mobile-large;
  }

  .other-messages {
    width: 65vw;
    max-width: 65vw;
    margin-right: 10vw;
    padding-right: 30px;
    padding-left: 30px;
    margin-top: 15px;
    padding-top: 10px;
    padding-bottom: 10px;
    background-color: #5d6260;
    border-radius: 20px;
    word-wrap: break-word;
    @include text-mobile-large;
  }

  .modal-fixed {
    height: 100vh;
    overflow-y: hidden;
    z-index: 10;
  }

  .message-container,
  .queue {
    height: 59vh !important;
    overflow-y: scroll;
    overflow-x: hidden;
  }
}
</style>
