import { describe, it, expect, vi } from "vitest";
import CloseSession from "../CloseSessionModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("CloseSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(CloseSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Ready to go?");
  });
  it("Renders the modal body", () => {
    const wrapper = mount(CloseSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain(
      "Are you sure you want to close this Virtual Jukebox session?"
    );
  });
  it("Renders the modal buttons", () => {
    const wrapper = mount(CloseSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Cancel");
    expect(wrapper.text()).toContain("Confirm");
  });
});
