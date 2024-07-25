import { describe, it, expect, vi } from "vitest";
import PrivacyPolicyModal from "../PrivacyPolicyModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("PrivacyPolicyModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(PrivacyPolicyModal, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Privacy Policy");
  });
  it("Renders the modal headings", () => {
    const wrapper = mount(PrivacyPolicyModal, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              chat: { chat_messages: [] },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Information Collection and Use");
    expect(wrapper.text()).toContain("Log Data");
    expect(wrapper.text()).toContain("Cookies");
    expect(wrapper.text()).toContain("Service Providers");
    expect(wrapper.text()).toContain("Security");
    expect(wrapper.text()).toContain("Links to Other Sites");
    expect(wrapper.text()).toContain("Changes to the Privacy Policy");
  });
});
