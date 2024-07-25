import { describe, it, expect, vi } from "vitest";
import WebSocketClosed from "../WebSocketClosed.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("ShareSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(WebSocketClosed, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Connection Issue");
  });
  it("Renders the modal body", () => {
    const wrapper = mount(WebSocketClosed, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain(
      "A connection issue has occured, please refresh the page"
    );
  });
  it("Renders the modal buttons", () => {
    const wrapper = mount(WebSocketClosed, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Refresh");
  });
});
