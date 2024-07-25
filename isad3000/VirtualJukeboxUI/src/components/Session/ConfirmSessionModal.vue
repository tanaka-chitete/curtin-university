<script lang="ts">
/* This is a modal which will appear when a user has hosted a session.
The modal will be unclosable until the Spotify Web Player has been initalised. */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { usePlayer } from "../../stores/player";
export default defineComponent({
  setup() {
    const playerStore = usePlayer();
    return { playerStore };
  },
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const spotifyModal = document.getElementById("webplayerstart");
      if (spotifyModal && this.playerStore.device_ready) {
        spotifyModal.style.display = "none";
      }
    },
  },
});
</script>

<template>
  <div id="webplayerstart" class="modal">
    <div class="modal-content">
      <div class="modal-title">Welcome</div>
      <div class="modal-text">
        Welcome to the Virtual Jukebox! Add a song to the queue to get started!
        <br />
      </div>
      <div id="webplayer-authenticate">
        <button
          v-if="playerStore.device_ready"
          @click="close()"
          id="webplayer-authenticate"
          class="connect-box"
        >
          All good!
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
  display: block;
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
  }
  .modal-text {
    @include text-mobile-small;
  }

  .connect-box {
    @include text-mobile-large;
    width: 80vw;
  }
}
</style>
