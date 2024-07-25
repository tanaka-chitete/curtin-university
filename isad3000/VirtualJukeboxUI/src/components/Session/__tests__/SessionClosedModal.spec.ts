import { describe, it, expect, vi } from "vitest";
import HostClosed from "../SessionClosedModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("HostClosedModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(HostClosed, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Closed Session");
  });
  it("Renders the modal body", () => {
    const wrapper = mount(HostClosed, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("This session has been closed");
  });
  it("Renders the modal buttons", () => {
    const wrapper = mount(HostClosed, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Back to home");
  });
});
