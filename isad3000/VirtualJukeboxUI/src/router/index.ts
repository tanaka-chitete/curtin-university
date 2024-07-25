import { createRouter, createWebHistory } from "vue-router";
import HomePageViewVue from "../views/HomePageView.vue";
import SessionViewVue from "../views/SessionView.vue";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomePageViewVue,
    },
    {
      path: "/session",
      name: "session",
      component: SessionViewVue,
    },
  ],
});

export default router;
