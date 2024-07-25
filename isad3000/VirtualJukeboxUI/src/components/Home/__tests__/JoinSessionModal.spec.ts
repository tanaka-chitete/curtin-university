import { describe, it, expect, vi } from "vitest";
import JoinSession from "../JoinSessionModal.vue";
import { shallowMount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("LeaveSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = shallowMount(JoinSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Join a Session in Range");
  });
  it("Renders the modal body", () => {
    const wrapper = shallowMount(JoinSession, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("No sessions in range");
  });
});
