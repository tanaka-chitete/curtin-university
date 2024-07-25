<script lang="ts">
/* Imports */
import axios from "axios";
import { defineComponent } from "vue";
/* Stores */
import { useUser } from "../../stores/user";
/* Services */
import MapworksDisplayer from "../../services/MapworksDisplayer";
/* Types */
import type { sessionType } from "../../stores/session";
export default defineComponent({
  setup() {
    const userStore = useUser();
    return { userStore };
  },
  async created() {
    let jsonResponse:
      | {
          data: sessionType[];
          status: number;
        }
      | undefined;
    const request = {
      user_location:
        "POINT(" +
        this.userStore.longitude +
        " " +
        this.userStore.latitude +
        ")",
    };
    try {
      await axios
        .get(import.meta.env.VITE_API_URL + "/vjsession/public/distance/", {
          params: request,
        })
        .then((response) => (jsonResponse = response));
      if (jsonResponse && jsonResponse.status == 200) {
        MapworksDisplayer.display(jsonResponse.data);
      }
    } catch (AxiosError) {
      this.$log.error("Error getting sessions");
    }
  },
});
</script>

<template>
  <div id="embed1"></div>
</template>

<style lang="scss" scoped>
@import "../../assets/_button.scss";
@import "../../assets/container.scss";

#embed1 {
  width: 100vw;
  height: 100vh;
}

// For mobile view
@media only screen and (max-device-width: 500px) {
  #embed1 {
    width: 90vw;
    height: 95vh;
    z-index: 1;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -1%);
  }
}
</style>
