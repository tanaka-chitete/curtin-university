<script lang="ts">
/* This is the close session modal which will popup when the user selects "close session" */
/* Imports */
import { defineComponent } from "vue";
import QrcodeVue from "qrcode.vue";
/* Stores */
import { useSession } from "../../stores/session";
export default defineComponent({
  setup() {
    const sessionStore = useSession();
    return { sessionStore };
  },
  components: { QrcodeVue },
  methods: {
    getURL(): string {
      return window.location.href;
    },
    close() {
      /* Method which closes the modal when the close button is clicked */
      const spotifyModal = document.getElementById("shareSession");
      if (spotifyModal) {
        spotifyModal.style.display = "none";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "shareSession") {
        this.close();
      }
    },
  },
});
</script>

<template>
  <div id="shareSession" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <div class="top-bar">
        <span class="close" @click="close()">&times;</span>
        <div class="modal-title">Share session</div>
      </div>
      <div class="qr-code">
        <QrcodeVue id="QR" :margin="2" :value="getURL()" :size="200" />
        <div class="modal-text">
          <a>{{ getURL() }}</a>
        </div>
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
  margin: 10% auto;
  /* 15% from the top and centered */
  overflow: hidden; // Prevent connect button from popping out of div when using float:right
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

.modal-text {
  @include text-small;
  height: 2em; // Spacing after text
  margin-top: 3vh;
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

.qr-code {
  text-align: center;
}

@media only screen and (max-device-width: 500px) {
  .modal-content {
    width: 90vw;
    height: fit-content;
    position: relative;
    top: 5vw;
    overflow-y: scroll;
  }

  .modal-title {
    @include text-mobile-large;
    width: 80vw;
  }

  #QR {
    width: 75vw !important;
    height: auto !important;
  }

  .modal-text {
    @include text-mobile-large;
    width: 80vw;
    height: fit-content;
  }

  .close {
    @include modal-mobile-close;
  }

  .top-bar {
    @include modal-mobile-top-bar;
  }
}
</style>
