import { describe, it, expect } from "vitest";
import { createPinia, setActivePinia } from "pinia";
import { useUser } from "../../stores/user";

describe("NowPlaying", () => {
  it("setUsername method", () => {
    setActivePinia(createPinia());
    const userStore = useUser();
    userStore.setUsername("Test");
    expect(userStore.username).toBe("Test");
  });
  it("setID method", () => {
    setActivePinia(createPinia());
    const userStore = useUser();
    userStore.setUsername("123");
    expect(userStore.username).toBe("123");
  });
});
