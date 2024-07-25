import { describe, it, expect } from "vitest";

import { shallowMount } from "@vue/test-utils";
import HostSession from "../HostSessionModal.vue";
import { createPinia, setActivePinia } from "pinia";
import { useSession } from "../../../stores/session";

describe("HostSession", () => {
  it("renders the title", () => {
    setActivePinia(createPinia());
    const wrapper = shallowMount(HostSession);
    expect(wrapper.text()).toContain("Host a Session");
  });

  it("renders the 'Close' button", () => {
    const wrapper = shallowMount(HostSession);
    expect(wrapper.text()).toContain("×");
  });

  it("renders the first sub-title", () => {
    setActivePinia(createPinia());
    const wrapper = shallowMount(HostSession);
    expect(wrapper.text()).toContain("Details");
  });

  it("renders the 'Add a playlist' dropdown", () => {
    setActivePinia(createPinia());
    const wrapper = shallowMount(HostSession);
    expect(wrapper.text()).toContain("Add a playlist");
  });

  it("changes session name in the session store", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.session_name = "Earl Burler's 21st Birthday";
    expect(sessionStore.session_name).toBe("Earl Burler's 21st Birthday");
  });

  it("renders the 'Host' button", () => {
    setActivePinia(createPinia());
    const wrapper = shallowMount(HostSession);
    expect(wrapper.text()).toContain("Host");
  });
});
