import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vitest/config";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";
import { quasar, transformAssetUrls } from "@quasar/vite-plugin";

// https://vitejs.dev/config/
export default defineConfig({
  test: {
    globals: true,
    environment: "jsdom",
  },
  plugins: [
    vue({
      template: { transformAssetUrls },
    }),
    vueJsx(),
    quasar(),
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
});
