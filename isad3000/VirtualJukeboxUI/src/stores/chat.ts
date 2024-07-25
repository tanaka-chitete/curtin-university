import { defineStore } from "pinia";

export interface chatMessage {
  username: string;
  message: string;
  timestamp: string;
  self: boolean;
}

export const useChat = defineStore({
  id: "chat",
  state: () => ({
    chat_messages: [] as chatMessage[],
    new_messages: 0,
    chat_open: false,
  }),
  actions: {
    newMessage(message: chatMessage) {
      this.chat_messages.push(message);
      if (!this.chat_open) this.notification();
    },
    notification() {
      this.new_messages += 1;
    },
    seenMessages() {
      this.new_messages = 0;
      this.chat_open = true;
    },
  },
});
