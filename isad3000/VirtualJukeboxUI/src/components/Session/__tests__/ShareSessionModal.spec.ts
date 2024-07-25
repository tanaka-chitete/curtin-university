import { describe, it, expect, vi } from "vitest";
import ShareSession from "../ShareSessionModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("ShareSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(ShareSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Share session");
  });
  it("Renders the modal body", () => {
    const wrapper = mount(ShareSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain(window.location.href);
  });
});
