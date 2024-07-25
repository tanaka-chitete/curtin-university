import { describe, it, expect, vi } from "vitest";
import LeaveSession from "../LeaveSessionModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("LeaveSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(LeaveSession, {
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
    const wrapper = mount(LeaveSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain(
      "Are you sure you want to leave this Virtual Jukebox session?"
    );
  });
  it("Renders the modal buttons", () => {
    const wrapper = mount(LeaveSession, {
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
