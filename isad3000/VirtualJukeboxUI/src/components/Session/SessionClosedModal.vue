<script lang="ts">
/* This is the leave session modal which will popup when the user selects "leave session" */
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
    async leaveSession() {
      this.sessionStore.allowLeave();
      await this.$router.push({ name: "home" });
    },
  },
});
</script>

<template>
  <div id="sessionClosed" class="modal">
    <div class="modal-content">
      <div class="modal-title">Closed Session</div>
      <div class="modal-text">This session has been closed</div>
      <button @click="leaveSession()" id="webplayer-close" class="connect-box">
        Back to home
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
    position: relative;
    top: 20vw;
  }
  .modal-title {
    @include text-mobile-large;
    width: 80vw;
  }

  .modal-text {
    @include text-mobile-large;
    width: 80vw;
    height: fit-content;
    padding-bottom: 5vh;
  }

  .connect-box {
    @include text-mobile-small;
    width: 40vw;
  }
}
</style>
