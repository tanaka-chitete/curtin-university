import { describe, it, expect } from "vitest";

import { shallowMount } from "@vue/test-utils";
import PremiumModal from "../SpotifyPremiumModal.vue";

describe("SpotifyLogin", () => {
  it("renders the title", () => {
    const wrapper = shallowMount(PremiumModal);
    expect(wrapper.text()).toContain("Spotify Premium");
  });

  it("renders the 'Close' button", () => {
    const wrapper = shallowMount(PremiumModal);
    expect(wrapper.text()).toContain("×");
  });

  it("renders the body", () => {
    const wrapper = shallowMount(PremiumModal);
    expect(wrapper.text()).toContain(
      "The logged in account does not have Spotify Premium."
    );
    expect(wrapper.text()).toContain(
      "To use the Virtual Jukebox, a Premium account is required."
    );
  });

  it("renders the 'Ok' button", () => {
    const wrapper = shallowMount(PremiumModal);
    expect(wrapper.text()).toContain("Ok");
  });
});
