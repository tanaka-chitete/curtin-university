import { describe, it, expect } from "vitest";

import { mount } from "@vue/test-utils";
import NameEnter from "../NameEnter.vue";
import { createPinia, setActivePinia } from "pinia";

describe("NowPlaying", () => {
  it("renders properly", () => {
    setActivePinia(createPinia());
    const wrapper = mount(NameEnter);
    expect(wrapper.text()).toContain("Tell me, what's your name?");
  });
});
