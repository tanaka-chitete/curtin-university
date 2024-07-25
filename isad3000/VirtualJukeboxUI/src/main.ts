import { createApp } from "vue";
/* Stores */
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
/* Icon library */
import { Quasar } from "quasar";
/* Logging library */
import VueLogger from "vuejs3-logger";

import App from "./App.vue";
import router from "./router";

import VueLoading from "vue-loading-overlay";
import "vue-loading-overlay/dist/vue-loading.css";

//Import icon libraries
import "@quasar/extras/material-icons/material-icons.css";
import "@quasar/extras/material-icons-outlined/material-icons-outlined.css";
//css Files
import "./assets/main.scss";
import "typeface-roboto/index.css";

const app = createApp(App);
app.use(router);

/* Store setup */
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(pinia);

/* Quasar icon library */
app.use(Quasar, {
  plugins: {}, //Any Quasar plugins here
});

/* Loading component */
app.use(VueLoading);

/* Logging options */
const options = {
  isEnabled: true,
  logLevel: import.meta.env.VITE_LOGGING_LEVEL,
  stringifyArguments: false,
  showLogLevel: true,
  showMethodName: import.meta.env.VITE_LOGGING_LEVEL === "debug",
  separator: "|",
  showConsoleColors: true,
};
app.use(VueLogger, options);

app.mount("#app");
