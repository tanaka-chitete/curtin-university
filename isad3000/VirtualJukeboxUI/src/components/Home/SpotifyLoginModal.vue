<script lang="ts">
/* This is a spotify login modal which will popup when the user selects "host session" and they are not logged into spotify */
/* Imports */
import { defineComponent } from "vue";
/* Services */
import SpotifyService from "../../services/SpotifyTokens";
export default defineComponent({
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const spotifyModal = document.getElementById("nospotify");
      if (spotifyModal) {
        spotifyModal.style.display = "none";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "nospotify") {
        this.close();
      }
    },
    spotifyLogin() {
      SpotifyService.methods.spotifyRedirect();
    },
  },
});
</script>

<template>
  <div id="nospotify" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <span class="close" @click="close()">&times;</span>
      <div class="modal-title">Connect to Spotify</div>
      <div class="modal-text">
        Not so fast! Before hosting a session, please connect to Spotify
        Premium.
      </div>
      <button @click="spotifyLogin()" class="connect-box">Connect</button>
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
    width: 90vw;
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
