<script lang="ts">
/* This is a spotify login modal which will popup when the user selects "host session" and they are not logged into spotify */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useSession } from "../../stores/session";
/* Mixins */
import LeaveSession from "../../mixins/LeaveSession";
export default defineComponent({
  setup() {
    const sessionStore = useSession();
    return { sessionStore };
  },
  mixins: [LeaveSession],
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const spotifyModal = document.getElementById("leaveJoined");
      if (spotifyModal) {
        spotifyModal.style.display = "none";
      }
    },
    async leaveSession() {
      const leaveResponse: { success: boolean; message: string } =
        await LeaveSession.leaveSession();
      if (leaveResponse.success) {
        this.$log.info(leaveResponse.message);
      } else {
        this.$log.error(leaveResponse.message);
      }
      this.close();
    },
    reconnect() {
      this.$router.push("/session" + "?id=" + this.sessionStore.session_id);
    },
  },
});
</script>

<template>
  <div id="leaveJoined" class="modal">
    <div class="modal-content">
      <div class="modal-title">Currently in Session</div>
      <div class="modal-text">
        We have detected that you are currently in another session. <br />
        Please leave or rejoin that session to continue
      </div>
      <button @click="leaveSession()" class="connect-box">Leave</button>
      <button
        v-if="sessionStore.session_id"
        @click="reconnect()"
        class="connect-box"
      >
        Rejoin
      </button>
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
  z-index: 3;
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

.modal-text {
  @include text-small;
  height: 5em; // Spacing after text
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

.connect-box {
  @include button-box;
  background: #00bd7e;
  color: #ffffff;
  cursor: pointer;
  float: right;
  width: 10vw; // Override default width for button-box
}

.connect-box:hover {
  background: #ffffff;
  color: #000000;
}

@media only screen and (max-device-width: 500px) {
  .modal-content {
    width: 90vw !important;
    height: fit-content;
    position: relative;
    top: 20vw;
  }
  .modal-title {
    @include text-mobile-large;
    width: 80vw;
  }

  .modal-text {
    @include text-mobile-small;
    width: 80vw;
    height: fit-content;
    padding-bottom: 2vh;
  }

  .connect-box {
    @include text-mobile-small;
    width: 77vw;
  }
  .close {
    @include modal-mobile-close;
  }
}
</style>
