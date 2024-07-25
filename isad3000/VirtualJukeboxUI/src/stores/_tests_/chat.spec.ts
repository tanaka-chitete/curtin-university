import { describe, it, expect } from "vitest";
import { createPinia, setActivePinia } from "pinia";
import { useChat } from "../chat";

describe("NowPlaying", () => {
  const sampleMessage = {
    username: "Jame",
    message: "Awesome song!",
    timestamp: "10:10:10",
    self: false,
  };
  it("newMessage method (chat closed)", () => {
    setActivePinia(createPinia());
    const chatStore = useChat();
    chatStore.newMessage(sampleMessage);
    expect(chatStore.chat_messages).toStrictEqual([sampleMessage]);
    expect(chatStore.new_messages).toBe(1);
  });
  it("newMessage method (chat open)", () => {
    setActivePinia(createPinia());
    const chatStore = useChat();
    chatStore.chat_open = true;
    chatStore.newMessage(sampleMessage);
    expect(chatStore.chat_messages).toStrictEqual([sampleMessage]);
    expect(chatStore.new_messages).toBe(0);
  });
  it("notification method", () => {
    setActivePinia(createPinia());
    const chatStore = useChat();
    chatStore.notification();
    expect(chatStore.new_messages).toBe(1);
  });
  it("seenMessages method", () => {
    setActivePinia(createPinia());
    const chatStore = useChat();
    chatStore.newMessage(sampleMessage);
    expect(chatStore.chat_messages).toStrictEqual([sampleMessage]);
    expect(chatStore.new_messages).toBe(1);
    chatStore.seenMessages();
    expect(chatStore.chat_open).toBe(true);
    expect(chatStore.new_messages).toBe(0);
  });
});
