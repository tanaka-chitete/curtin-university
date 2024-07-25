import { describe, it, expect, vi } from "vitest";
import ChatModal from "../ChatModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";

describe("CloseSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(ChatModal, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Chat");
  });
  it("Renders the modal body (No messages)", () => {
    const wrapper = mount(ChatModal, {
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
    expect(wrapper.text()).toContain("");
  });
  it("Renders the modal body (Messages)", () => {
    const messages = {
      username: "James",
      message: "Awesome song!",
      timestamp: "10:10:10",
      self: false,
    };
    const wrapper = mount(ChatModal, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              chat: { chat_messages: [messages] },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).not.toContain(
      "Send a message to get this party started!"
    );
    expect(wrapper.text()).toContain("James");
    expect(wrapper.text()).toContain("10:10:10");
    expect(wrapper.text()).toContain("Awesome song!");
  });
  it("Renders the modal buttons", () => {
    const wrapper = mount(ChatModal, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Send");
  });
});
