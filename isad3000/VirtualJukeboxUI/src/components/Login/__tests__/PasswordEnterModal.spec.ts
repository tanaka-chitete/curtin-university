import { describe, it, expect, vi } from "vitest";
import PasswordEnterModal from "../PasswordEnterModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("PasswordEnterModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(PasswordEnterModal, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Joining a private session");
  });
  it("Renders the modal body", () => {
    const wrapper = mount(PasswordEnterModal, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Please enter the session password");
  });
  it("Renders the modal buttons", () => {
    const wrapper = mount(PasswordEnterModal, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Join Session");
  });
});
