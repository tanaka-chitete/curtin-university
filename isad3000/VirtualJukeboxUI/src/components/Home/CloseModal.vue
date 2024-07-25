<script lang="ts">
/* This is a spotify login modal which will popup when the user selects "host session" and they are not logged into spotify */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useSession } from "../../stores/session";
/* Mixins */
import CloseSession from "../../mixins/CloseSession";
export default defineComponent({
  setup() {
    const sessionStore = useSession();
    return { sessionStore };
  },
  mixins: [CloseSession],
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const closeHosted = document.getElementById("closeHosted");
      if (closeHosted) {
        closeHosted.style.display = "none";
      }
    },
    async closeSession() {
      const closeResponse: { success: boolean; message: string } =
        await CloseSession.closeSession();
      if (closeResponse.success) {
        this.$log.info(closeResponse.message);
      } else {
        this.$log.error(closeResponse.message);
      }
      this.close();
      location.reload();
    },
    reconnect() {
      this.$router.push("/session" + "?id=" + this.sessionStore.session_id);
    },
  },
});
</script>

<template>
  <div id="closeHosted" class="modal">
    <div class="modal-content">
      <div class="modal-title">Currently in Session</div>
      <div class="modal-text">
        We have detected that you are currently hosting another session. <br />
        Please close or rejoin that session to continue
      </div>
      <button @click="closeSession()" class="connect-box">Close</button>
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
    top: 30vw;
    padding-bottom: 50px;
  }
  .modal-title {
    @include text-mobile-large;
    width: 80vw;
  }
  .modal-text {
    @include text-mobile-small;
  }
  .connect-box {
    @include text-mobile-small;
    position: relative;
    top: 30px;
    width: 77vw;
    margin-top: 10px;
  }
  .close {
    @include modal-mobile-close;
  }
}
</style>
