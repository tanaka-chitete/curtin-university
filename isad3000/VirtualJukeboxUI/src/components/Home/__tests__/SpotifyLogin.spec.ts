import { describe, it, expect } from "vitest";

import { shallowMount } from "@vue/test-utils";
import SpotifyLogin from "../SpotifyLoginModal.vue";

describe("SpotifyLogin", () => {
  it("renders the title", () => {
    const wrapper = shallowMount(SpotifyLogin);
    expect(wrapper.text()).toContain("Connect to Spotify");
  });

  it("renders the 'Close' button", () => {
    const wrapper = shallowMount(SpotifyLogin);
    expect(wrapper.text()).toContain("×");
  });

  it("renders the message", () => {
    const wrapper = shallowMount(SpotifyLogin);
    expect(wrapper.text()).toContain(
      "Not so fast! Before hosting a session, please connect to Spotify Premium."
    );
  });

  it("renders the 'Connect' button", () => {
    const wrapper = shallowMount(SpotifyLogin);
    expect(wrapper.text()).toContain("Connect");
  });
});
