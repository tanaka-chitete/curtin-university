<script lang="ts">
/* Modal which displays when the user clicks an icon on the map. Will show session name, description and an option to join */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useSession } from "../../stores/session";
/* Mixins */
import JoinSession from "../../mixins/JoinSession";
export default defineComponent({
  setup() {
    const sessionStore = useSession();
    return { sessionStore };
  },
  data() {
    return {
      error_message: "",
    };
  },
  mixins: [JoinSession],
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const JoinSelectedSession = document.getElementById(
        "joinselectedsession"
      );
      if (JoinSelectedSession) {
        JoinSelectedSession.style.display = "none";
        this.error_message = "";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "joinselectedsession") {
        this.close();
      }
    },
    async joinSession() {
      let joinResponse: {
        success: boolean;
        message: string;
        sessionId: string | undefined;
      };
      if (this.sessionStore.session_id) {
        joinResponse = await JoinSession.joinSession(
          this.sessionStore.session_id
        );
        if (joinResponse.success) {
          this.$log.info(joinResponse.message);
          this.$router.push("/session" + "?id=" + joinResponse.sessionId);
        } else {
          this.$log.error(joinResponse.message);
          this.error_message = joinResponse.message;
        }
      }
    },
  },
});
</script>

<template>
  <div id="joinselectedsession" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <span class="close" @click="close()">&times;</span>
      <div class="modal-title">
        {{ sessionStore.session_name }}
      </div>
      <div class="modal-sub-title">Description</div>
      <div class="session-description">
        {{ sessionStore.session_description }}
      </div>
      <br />
      <div class="modal-sub-title">Connection Range</div>
      <div class="session-description">{{ sessionStore.connection_range }}</div>
      <br />
      <div class="modal-sub-title">Distance</div>
      <div class="session-description">{{ sessionStore.distance }} away</div>
      <button class="connect-box" @click="joinSession()">Join</button>
      <div class="error">{{ error_message }}</div>
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
  width: 90%; // Prevent it from overlapping the close button, preventing it from being clicked
}

.modal-sub-title {
  @include text-small-bold;
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

.session-description {
  @include text-small;
  text-overflow: ellipsis;
  overflow: hidden;
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

.error {
  @include text-small-bold;
  color: red;
  display: inline;
}

@media only screen and (max-device-width: 500px) {
  .modal-content {
    position: relative;
    top: 10vh;
    width: 80vw;
    max-height: 80vh;
    overflow-y: scroll;
    z-index: 2 !important;
  }
  .modal-title {
    @include text-mobile-large;
    width: 50vw !important;
    overflow-x: scroll !important;
  }

  .modal-sub-title {
    @include text-mobile-large;
  }
  .session-description {
    @include text-mobile-small;
  }
  .error {
    @include text-mobile-small;
  }
  .connect-box {
    @include text-mobile-large;
    margin-top: 15px;
    width: 70vw;
  }
  // .close {
  //   font-size: 30px;
  //   position: relative;
  //   bottom: 5px;
  // }

  .close {
    // font-size: 50px;
    // position: relative;
    // bottom: 20px;
    // z-index: 10;
    @include modal-mobile-close;
  }
}
</style>
