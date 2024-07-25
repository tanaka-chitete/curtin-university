import { describe, it, expect, vi } from "vitest";
import SessionDisplay from "../SessionDisplayModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("LeaveSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(SessionDisplay, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: {
                session_name: "Joe's Session",
              },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Joe's Session");
  });
  it("Renders the modal body", () => {
    const wrapper = mount(SessionDisplay, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: {
                session_description: "This session is cool",
                connection_range: "50m",
                distance: "3m",
              },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Description");
    expect(wrapper.text()).toContain("This session is cool");
    expect(wrapper.text()).toContain("Connection Range");
    expect(wrapper.text()).toContain("50m");
    expect(wrapper.text()).toContain("Distance");
    expect(wrapper.text()).toContain("3m");
  });
  it("Renders the modal buttons", () => {
    const wrapper = mount(SessionDisplay, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Join");
  });
});
