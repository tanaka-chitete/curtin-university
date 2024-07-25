import { describe, it, expect, vi } from "vitest";
import LeaveJoined from "../LeaveModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("LeaveSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(LeaveJoined, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Currently in Session");
  });
  it("Renders the modal body", () => {
    const wrapper = mount(LeaveJoined, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain(
      "We have detected that you are currently in another session."
    );
    expect(wrapper.text()).toContain(
      "Please leave or rejoin that session to continue"
    );
  });
  it("Renders the modal buttons", () => {
    const wrapper = mount(LeaveJoined, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: { session: { session_id: "123" } },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Leave");
    expect(wrapper.text()).toContain("Rejoin");
  });
});
