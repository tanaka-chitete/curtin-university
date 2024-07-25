import { describe, it, expect, vi } from "vitest";
import CloseHosted from "../CloseModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("LeaveSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(CloseHosted, {
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
    const wrapper = mount(CloseHosted, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain(
      "We have detected that you are currently hosting another session."
    );
    expect(wrapper.text()).toContain(
      "Please close or rejoin that session to continue"
    );
  });
  it("Renders the modal buttons", () => {
    const wrapper = mount(CloseHosted, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: { session: { session_id: "123" } },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Close");
    expect(wrapper.text()).toContain("Rejoin");
  });
});
