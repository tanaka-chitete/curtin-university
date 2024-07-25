<script lang="ts">
/* This is the close session modal which will popup when the user selects "close session" */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { usePlayer } from "../../stores/player";
import { useSession } from "../../stores/session";
import { useUser } from "../../stores/user";
/* Mixins */
import CloseSession from "../../mixins/CloseSession";
export default defineComponent({
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    const playerStore = usePlayer();
    return { userStore, sessionStore, playerStore };
  },
  mixins: [CloseSession],
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const spotifyModal = document.getElementById("closeSession");
      if (spotifyModal) {
        spotifyModal.style.display = "none";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "closeSession") {
        this.close();
      }
    },
    async closeSession() {
      const closeResponse: { success: boolean; message: string } =
        await CloseSession.closeSession();
      if (closeResponse.success) {
        this.$log.info(closeResponse.message);
        /* Removes the "change page" event listener */
      } else {
        this.$log.error(closeResponse.message);
      }
      /* Navigate home regardless of API success */
      this.sessionStore.allowLeave();
      await this.$router.push({ name: "home" });
    },
  },
});
</script>

<template>
  <div id="closeSession" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <div class="top-bar">
        <span class="close" @click="close()">&times;</span>
        <div class="modal-title">Ready to go?</div>
      </div>

      <div class="modal-text">
        Are you sure you want to close this Virtual Jukebox session?
      </div>
      <div class="button-layout">
        <button
          @click="closeSession()"
          id="webplayer-close"
          class="connect-box"
        >
          Confirm
        </button>
        <button @click="close()" id="webplayer-cancel" class="connect-box">
          Cancel
        </button>
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

// For mobile view
@media only screen and (max-device-width: 500px) {
  .modal-content {
    position: fixed;
    top: 40%;
    left: 53%;
    transform: translate(-53%, -50%);
    height: fit-content;
    width: 90vw;
  }

  .modal-title {
    @include text-mobile-large;
    width: 100vw;
    padding-top: 5px;
  }

  .modal-text {
    @include text-mobile-small;
  }

  .close {
    @include modal-mobile-close;
  }

  .top-bar {
    @include modal-mobile-top-bar;
  }

  #webplayer-close,
  #webplayer-cancel {
    width: 30vw;
    @include text-mobile-small;
  }
}
</style>
