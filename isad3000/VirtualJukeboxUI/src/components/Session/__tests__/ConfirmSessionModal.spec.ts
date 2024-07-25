import { describe, it, expect, vi } from "vitest";
import ConfirmSession from "../ConfirmSessionModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("ConfirmSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(ConfirmSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Welcome");
  });
  it("Renders the modal body", () => {
    const wrapper = mount(ConfirmSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain(
      "Welcome to the Virtual Jukebox! Add a song to the queue to get started!"
    );
  });
  it("Does NOT render the button by default", () => {
    const wrapper = mount(ConfirmSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).not.toContain("All good!");
  });
  it("Does DOES render the button when player is ready", () => {
    const wrapper = mount(ConfirmSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: { player: { device_ready: true } },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("All good!");
  });
});
