<script lang="ts">
/* This is the session settings modal which will popup when the user selects "settings" */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useSession } from "../../stores/session";
export default defineComponent({
  setup() {
    const sessionStore = useSession();
    return { sessionStore };
  },
  methods: {
    getURL(): string {
      return window.location.href;
    },
    close() {
      /* Method which closes the modal when the close button is clicked */
      const sessionSettings = document.getElementById("sessionSettings");
      if (sessionSettings) {
        sessionSettings.style.display = "none";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "sessionSettings") {
        this.close();
      }
    },
  },
});
</script>

<template>
  <div id="sessionSettings" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <div class="top-bar">
        <span class="close" @click="close()">&times;</span>
        <div class="modal-title">Session Information</div>
      </div>
      <div id="Title" class="modal-sub-title">Name</div>
      <div class="modal-text">
        {{ sessionStore.session_name }}
      </div>
      <div id="Desc" class="modal-sub-title">Description</div>
      <div class="modal-text">
        {{ sessionStore.session_description }}
      </div>
      <div class="modal-sub-title">Songs in playlist</div>
      <div class="modal-text">
        {{ Object.keys(sessionStore.playlist_songs).length }}
      </div>
      <div class="modal-sub-title">Songs in queue</div>
      <div class="modal-text">
        {{ sessionStore.queue.length }}
      </div>
      <!-- <button @click="close()" class="connect-box">Done</button> -->
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
  height: 2em; // Spacing after title
}

.modal-text {
  @include text-small;
  height: 3em; // Spacing after text
  overflow: hidden;
  text-overflow: ellipsis;
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

@media only screen and (max-device-width: 500px) {
  .modal-content {
    width: 90vw;
    height: fit-content;
    max-height: 70vh;
    position: relative;
    top: 20vw;
    overflow-y: scroll;
  }

  .modal-title {
    @include text-mobile-large;
    width: 80vw;
  }

  .modal-sub-title {
    @include text-mobile-small;
    width: 90vw;
    height: 20px;
  }

  .modal-text {
    @include text-mobile-small;
    word-wrap: break-word;
    height: fit-content !important;
    padding-bottom: 10px;
  }

  .top-bar {
    @include modal-mobile-top-bar;
  }

  .close {
    @include modal-mobile-close;
  }
}
</style>
