/* Service which controls the retrieval and refreshng of Spotify tokens */
/* Imports */
import router from "@/router";
import axios from "axios";
import { inject } from "vue";
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
export default {
  methods: {
    /* Redirects the user to Spotify to ask for permissions.
    Required for hosting a session */
    async spotifyRedirect(): Promise<void> {
      const logger: any = inject("vuejs3-logger");
      let jsonResponse: { data: { url: string }; status: number } | undefined;
      try {
        await axios
          .get(import.meta.env.VITE_API_URL + "/spotify/get-auth-url")
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          window.location.href =
            jsonResponse.data.url; /* Redirects to spotify page */
        }
      } catch (AxiosError: any) {
        if (AxiosError.response.status == 400) {
          logger?.error("Invalid request");
        } else {
          logger?.error("Unknown error");
        }
      }
    },
    /*Checks if a spotify auth code exists in the URL, and if it does, parses
    this auth code through Spotify to retreive access and refresh tokens */
    async getSpotifyTokens() {
      const logger: any = inject("vuejs3-logger");
      const uri = new URL(window.location.href);
      const authcode = uri.searchParams.get("code");
      if (authcode) {
        let jsonResponse:
          | {
              data: {
                url: string;
                error: string;
                access_token: string;
                refresh_token: string;
              };
              status: number;
            }
          | undefined;
        const request = { code: authcode };
        try {
          await axios
            .get(import.meta.env.VITE_API_URL + "/spotify/get-tokens", {
              params: request,
            })
            .then(
              (response) => (jsonResponse = response)
            ); /* Given a auth code, get the access and refresh tokens from the get-tokens API*/
          if (
            jsonResponse &&
            jsonResponse.status == 200 &&
            !jsonResponse.data.error
          ) {
            /* Error messages still return their status as 200, so they need to be checked seperately */
            const premiumCheckResponse = await this.checkPremium(
              jsonResponse.data.access_token
            );
            if (
              !premiumCheckResponse.success ||
              !premiumCheckResponse.premium
            ) {
              logger?.warn("Account detected as not premium");
              const spotifyPremiumModal =
                document.getElementById("spotifypremium");
              if (spotifyPremiumModal) {
                spotifyPremiumModal.style.display = "block";
              }
            } else {
              const expirytime =
                new Date().getTime() +
                3600 *
                  1000; /* The expiry time is 3600 seconds, time is stored in milliseconds */
              cookies.set(
                "accesstoken",
                jsonResponse.data.access_token,
                "",
                "",
                "",
                true
              ); /* Set access token as a secure cookie */
              cookies.set(
                "refreshtoken",
                jsonResponse.data.refresh_token,
                "",
                "",
                "",
                true
              ); /* Set refresh token as a secure cookie */
              cookies.set(
                "expiresat",
                expirytime.toString()
              ); /* Sets expiry time as a cookie */
              router.push({ name: "home" });
            }
          }
        } catch (AxiosError: any) {
          if (AxiosError.response.status == 400) {
            logger?.error("Invalid request");
          } else {
            logger?.error("Unknown error");
          }
        }
      }
    },
    /* Given an access token, checks if a user has a premium account */
    async checkPremium(
      access_token: string
    ): Promise<{ success: boolean; premium: boolean }> {
      const logger: any = inject("vuejs3-logger");
      let jsonResponse:
        | {
            data: {
              is_premium: boolean;
            };
            status: number;
          }
        | undefined;
      const request = {
        access_token: access_token,
      };
      try {
        await axios
          .get(import.meta.env.VITE_API_URL + "/spotify/account-status", {
            params: request,
          })
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          return { success: true, premium: jsonResponse.data.is_premium };
        } else {
          return { success: false, premium: false };
        }
      } catch (AxiosError: any) {
        if (AxiosError.response.status == 400) {
          logger?.error("Invalid request");
          return { success: false, premium: false };
        } else {
          logger?.error("Unknown error");
          return { success: false, premium: false };
        }
      }
    },
    /* Checks if the current access token has expired, and if it has, uses the refresh
    token to generate a new access token */
    async checkTokens(): Promise<void> {
      const currentTime = new Date().getTime();
      const expirytime = parseInt(cookies.get("expiresat"));
      if (expirytime < currentTime) {
        await this.refreshTokens();
      }
    },
    async refreshTokens() {
      const logger: any = inject("vuejs3-logger");
      let jsonResponse:
        | {
            data: {
              access_token: string;
            };
            status: number;
          }
        | undefined;
      const request = {
        access_token: cookies.get("accesstoken"),
        refresh_token: cookies.get("refreshtoken"),
      };
      try {
        await axios
          .post(
            import.meta.env.VITE_API_URL + "/spotify/refresh-tokens",
            request
          )
          .then((response) => (jsonResponse = response));
        if (jsonResponse && jsonResponse.status == 200) {
          const expirytime =
            new Date().getTime() +
            3600 *
              1000; /* The expiry time is 3600 seconds, time is stored in milliseconds */
          cookies.set(
            "accesstoken",
            jsonResponse.data.access_token,
            "",
            "",
            "",
            true
          ); /* Set access token as a secure cookie */
          cookies.set(
            "expiresat",
            expirytime.toString()
          ); /* Sets expiry time as a cookie */
        }
      } catch (AxiosError: any) {
        if (AxiosError.response.status == 400) {
          logger?.error("Invalid request");
        } else {
          logger?.error("Unknown error");
        }
      }
    },
  },
};
