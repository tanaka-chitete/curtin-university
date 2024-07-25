<script lang="ts">
/* Prompts the user to enter a name */
/* Imports */
import axios from "axios";
import { defineComponent } from "vue";
/* Stores */
import { useUser } from "../../stores/user";
export default defineComponent({
  setup() {
    const userStore = useUser();
    return { userStore };
  },
  data() {
    return {
      usernameInput: "",
      error: "",
      debounce_enabled: false,
    };
  },
  methods: {
    /* A user has finished typing their name and submits. Will only accept if there are no active users with the same name */
    async submitUsername() {
      const submittedName = this.usernameInput;
      if (!submittedName) {
        this.error = "Not so fast! You haven't entered your name.";
      } else if (this.usernameInput.length > 50) {
        this.error = "Not so fast! Username cannot be over 50 characters";
      } else {
        if (!this.userStore.hasLocation()) {
          this.getLocation();
        } else {
          const request = { username: submittedName };
          let jsonResponse:
            | { data: { id: string }; status: number }
            | undefined;
          try {
            /* API Request to createuser */
            await axios
              .post(import.meta.env.VITE_API_URL + "/user/create/", request)
              .then((response) => (jsonResponse = response));
            /* If successful change values */
            if (jsonResponse && jsonResponse.status == 201) {
              /* 201: Success */
              this.$log.info("Valid username submitted");
              /* Sets the username in local storage.
              This will automatically change the page to the map screen */
              this.userStore.setUsername(submittedName);
              this.userStore.setID(jsonResponse.data.id);
              location.reload();
            }
          } catch (AxiosError: any) {
            if (AxiosError.response.status == 400) {
              /* 400 : Invalid request */
              this.error = "Invalid display name";
              this.$log.error("Invalid display name");
            } else if (AxiosError.response.status == 409) {
              /* 409 : Name already in database */
              this.error = "Display name already in use";
              this.$log.error("Display name already in use");
            } else {
              /* other : Any other issues such as no database connection */
              this.error = "Unknown error";
              this.$log.error("Unknown error");
            }
          }
        }
      }
    },
    async getLocation() {
      this.userStore.findLocation();
    },
    openPrivacyPolicy() {
      const privacyPolicyModal = document.getElementById("privacypolicy");
      if (privacyPolicyModal) {
        privacyPolicyModal.style.display = "block";
      }
    },
    async checkUsername() {
      if (!this.debounce_enabled) {
        this.debounce_enabled = true;
        let jsonResponse:
          | { data: { name_taken: boolean }; status: number }
          | undefined;
        const request = { username: this.usernameInput };
        await axios
          .get(import.meta.env.VITE_API_URL + "/user/taken/", {
            params: request,
          })
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          if (jsonResponse.data.name_taken) {
            this.error = "Display name already in use";
          } else {
            this.error = "";
          }
        }
        await new Promise((resolve) => setTimeout(resolve, 100));
        this.debounce_enabled = false;
      }
    },
  },
});
</script>

<template>
  <div class="name-container">
    <img
      alt="Virtual Jukebox logo"
      class="logo"
      src="@/assets/logo-white.png"
    />
    <div class="form-center">
      <div class="name-prompt">Tell me, what's your name?</div>
      <!-- Return false prevents page reload. @submit triggers when user pushes enter while typing input-->
      <form
        class="form-box"
        onsubmit="return false;"
        @submit="submitUsername()"
      >
        <input
          placeholder="Enter your display name"
          class="login-box"
          v-model="usernameInput"
          @keyup="checkUsername()"
        />
      </form>
      <button @click="submitUsername()" class="submit-box">Start</button>
      <div class="privacy-policy" @click="openPrivacyPolicy()">
        Privacy Policy
      </div>
      <div class="error">{{ error }}</div>
      <div v-if="!userStore.latitude" class="error">
        Please enable location services
      </div>
      <!-- If there is an error, display it here. Blank by default-->
    </div>
    <div class="version">Virtual Jukebox 3.0</div>
  </div>
</template>

<style lang="scss" scoped>
@import "../../assets/_button.scss";
@import "../../assets/_typography.scss";
@import "../../assets/_container.scss";

.name-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 50vw;
}

.logo {
  height: 12vh;
  margin-bottom: 30px;
}

::placeholder {
  color: #a0acbc;
}

.name-prompt {
  @include text-content;
  line-height: 1vw;
  text-align: center;
  margin: 1vh;
  color: #ffffff;
}

.form-center {
  @include form-center;
}

.form-box {
  @include form-box;
}

.login-box {
  @include button-box;
  background: #ffffff;
  color: #000000;
}

.login-box:focus {
  outline: none;
  /* Change border colour */
  border: 3px solid #188ac3;
}

.submit-box {
  @include button-box;
  background: #00bd7e;
  color: #ffffff;
  cursor: pointer;
}

.submit-box:hover {
  background: #ffffff;
  color: #000000;
}

.error {
  @include text-small-bold;
  padding-top: 1vh;
  color: #f61067;
}

.location {
  @include text-small;
  color: #ffffff;
  padding-bottom: 3vh;
}

.location:hover {
  cursor: pointer;
  color: #00bd7e;
}

.version {
  @include text-small;
  position: fixed;
  bottom: 10px;
}

.privacy-policy {
  top: 4px;
  @include text-small;
  color: #ffffff;
}

.privacy-policy:hover {
  color: #00bd7e;
  cursor: pointer;
}

// For mobile view
@media only screen and (max-device-width: 500px) {
  .name-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vw;
    margin-top: 5vh;
  }
  .logo {
    height: 20vh;
  }
  .name-prompt {
    @include text-mobile-large;
    color: aliceblue;
  }
  .login-box {
    @include text-mobile-large;
    height: 10vh;
    width: 80vw;
    min-height: 50px;
  }

  .error {
    @include text-mobile-small;
  }
  .submit-box {
    @include text-mobile-large;
    height: 7vh;
    min-height: 50px;
    width: 80vw;
    background: #00bd7e;
  }
  .privacy-policy {
    @include text-mobile-large;
  }

  .version {
    @include text-mobile-large;
    position: absolute;
    top: calc(45vh + 45vw);
  }
}
</style>
