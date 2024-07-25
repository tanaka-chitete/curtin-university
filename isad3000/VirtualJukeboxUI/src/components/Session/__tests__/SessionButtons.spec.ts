import { describe, it, expect, vi } from "vitest";
import { mount } from "@vue/test-utils";
import SessionButtons from "../SessionButtons.vue";
import { Quasar } from "quasar";
import { createTestingPinia } from "@pinia/testing";
/* Add more unit tests when button functionality added */
describe("SessionButtons", () => {
  it("renders the HOST buttons to the screen", () => {
    const wrapper = mount(SessionButtons, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: { session: { host_id: "123" }, user: { id: "123" } },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("share");
    expect(wrapper.text()).toContain("play_circle");
    expect(wrapper.text()).toContain("skip_next");
    expect(wrapper.text()).toContain("playlist_add");
    expect(wrapper.text()).toContain("disabled_by_default");
    expect(wrapper.text()).toContain("info");
    expect(wrapper.text()).toContain("queue_music");
    expect(wrapper.text()).toContain("chat");
  });
  it("renders the GUEST buttons to the screen", () => {
    const wrapper = mount(SessionButtons, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: { host_id: "123" },
              user: { id: "different" },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("playlist_add");
    expect(wrapper.text()).toContain("logout");
    expect(wrapper.text()).toContain("queue_music");
    expect(wrapper.text()).toContain("chat");
  });
  it("renders the chat notification button to the screen", () => {
    const wrapper = mount(SessionButtons, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              chat: { new_messages: 4, chat_open: false },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("mark_unread_chat_alt");
  });
});
