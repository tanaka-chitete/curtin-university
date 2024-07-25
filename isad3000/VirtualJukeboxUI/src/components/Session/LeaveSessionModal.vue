<script lang="ts">
/* This is the leave session modal which will popup when the user selects "leave session" */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { usePlayer } from "../../stores/player";
import { useSession } from "../../stores/session";
import { useUser } from "../../stores/user";
/* Mixins */
import LeaveSession from "../../mixins/LeaveSession";
export default defineComponent({
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    const playerStore = usePlayer();
    return { userStore, sessionStore, playerStore };
  },
  mixins: [LeaveSession],
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const leaveModal = document.getElementById("leaveSession");
      if (leaveModal) {
        leaveModal.style.display = "none";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "leaveSession") {
        this.close();
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
      /* Navigate to home page regardless of success */
      this.sessionStore.allowLeave();
      await this.$router.push({ name: "home" });
    },
  },
});
</script>

<template>
  <div id="leaveSession" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <div class="top-bar">
        <span class="close" @click="close()">&times;</span>
        <div class="modal-title">Ready to go?</div>
      </div>
      <div class="modal-text">
        Are you sure you want to leave this Virtual Jukebox session?
      </div>
      <button @click="leaveSession()" id="webplayer-close" class="connect-box">
        Confirm
      </button>
      <button @click="close()" class="connect-box">Cancel</button>
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
    width: 90vw;
    height: fit-content;
    position: fixed;
    top: 40%;
    left: 53%;
    transform: translate(-53%, -50%);
  }
  .modal-title {
    @include text-mobile-large;
    width: 80vw;
  }

  .modal-text {
    @include text-mobile-large;
    width: 80vw;
  }

  .connect-box {
    @include text-mobile-small;
    width: 30vw;
  }

  .top-bar {
    @include modal-mobile-top-bar;
  }

  .close {
    @include modal-mobile-close;
  }
}
</style>
