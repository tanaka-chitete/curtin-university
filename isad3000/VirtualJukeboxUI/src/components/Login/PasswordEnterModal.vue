<script lang="ts">
/* Modal which allows the user to host a session by specifying a playlist, name, ect... */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useUser } from "../../stores/user";
import { useSession } from "../../stores/session";
/* Mixins */
import JoinSession from "../../mixins/JoinSession";
import LeaveSession from "../../mixins/LeaveSession";
export default defineComponent({
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    return { userStore, sessionStore };
  },
  data() {
    return {
      session_password: "",
      error_message: "",
    };
  },
  mixins: [JoinSession, LeaveSession],
  methods: {
    close() {
      const passwordModal = document.getElementById("enterpassword");
      if (passwordModal) {
        passwordModal.style.display = "none";
      }
    },
    /* Tries to join the session with the user inputted password */
    async submitPassword() {
      let joinResponse: {
        success: boolean;
        message: string;
        sessionId: string | undefined;
      };
      const uri = new URL(window.location.href);
      const session_id = uri.searchParams.get("id");
      if (session_id) {
        joinResponse = await JoinSession.joinSession(
          session_id,
          this.session_password
        );
        if (joinResponse.success) {
          this.$log.info(joinResponse.message);
          this.close();
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
  <div id="enterpassword" class="modal">
    <div class="modal-content">
      <div class="modal-title">Joining a private session</div>
      <div class="modal-sub-title">Please enter the session password</div>
      <!-- Add a password -->
      <input
        placeholder="Password"
        type="password"
        class="login-box"
        v-model="session_password"
      />
      <br />

      <button @click="submitPassword()" class="host-box">Join Session</button>
      <!-- If there is an error, display it here. Blank by default-->
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
  display: block;
}

/* Modal Content/Box */
.modal-content {
  @include modal-content;
  overflow: hidden; // Prevent host button from popping out of div when using float:right
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

/* The 'Host' box */
.host-box {
  @include button-box;
  background: #00bd7e;
  color: #ffffff;
  cursor: pointer;
  float: right;
  width: 10vw; // Override default width for button-box
}

.host-box:hover {
  background: #ffffff;
  color: #000000;
}

/* The 'Add a name' and 'Add a description' input fields */
.login-box {
  @include button-box;
  background: #ffffff;
  color: #000000;
  border-radius: 1vh; // Override default button-box border radius
  text-align: left; // Override default text-alignment
  padding-left: 1vh; // Override default padding
  width: 100%; // Override default width to span entire popup
}

.login-box:focus {
  outline: none;
  /* Change border colour */
  border: 3px solid #188ac3;
}

::placeholder {
  color: #a0acbc;
}

/* The 'Add a playlist', 'Configure join radius', and 'Configure visibility' drop downs */
.select-box {
  @include select-box;
  text-align: left; // Override default text-alignment
  padding-left: 1vh; // Override default padding
  width: 100%; // Override default width to span entire popup
}

.select-box:focus {
  outline: none;
  /* Change border colour */
  box-shadow: inset 0px 0px 0px 3px #188ac3;
}

.error {
  @include text-small-bold;
  color: red;
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
  .modal-sub-title {
    @include text-mobile-small;
    width: 80vw;
  }
  .login-box {
    @include text-mobile-large;
    width: 80vw;
    height: 40px;
    z-index: 10 !important;
    margin-bottom: 2vh;
  }

  .host-box {
    @include text-mobile-small;
    width: 77vw;
    height: 40px;
    margin-bottom: 2vh;
  }

  .error {
    @include text-mobile-small;
    left: 20vw;
    width: 77vw;
  }
}
</style>
