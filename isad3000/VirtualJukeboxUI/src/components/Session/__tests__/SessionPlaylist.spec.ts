import { describe, it, expect, vi } from "vitest";
import SessionPlaylist from "../SessionPlaylist.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";
import type { playlistSong } from "../../../stores/session";
import { Quasar } from "quasar";

describe("NowPlaying", () => {
  const samplePlaylist: playlistSong = {
    SongOne1: {
      album_name: "Album",
      album_uri: "spotify:example",
      artists: ["Joe", "Swanson"],
      duration_ms: 30000,
      images: ["image1.jpg", "image2.jpg", "image3.jpg"],
      name: "Cool Song",
      track_num: 3,
    },
    SongOne2: {
      album_name: "Album2",
      album_uri: "spotify:example",
      artists: ["Joe2", "Swanson"],
      duration_ms: 30000,
      images: ["image12.jpg", "image22.jpg", "image32.jpg"],
      name: "Cool Song2",
      track_num: 3,
    },
    SongOne3: {
      album_name: "Album3",
      album_uri: "spotify:example",
      artists: ["Joe3", "Swanson"],
      duration_ms: 30000,
      images: ["image13.jpg", "image23.jpg", "image33.jpg"],
      name: "Cool Song3",
      track_num: 3,
    },
    SongOne4: {
      album_name: "Album4",
      album_uri: "spotify:example",
      artists: ["Joe4", "Swanson4"],
      duration_ms: 30000,
      images: ["image14.jpg", "image24.jpg", "image34.jpg"],
      name: "Cool Song4",
      track_num: 3,
    },
  };
  it("Renders title", () => {
    const wrapper = mount(SessionPlaylist, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Add song to queue");
  });
  it("Renders the name of songs in the playlist", () => {
    const wrapper = mount(SessionPlaylist, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: { playlist_songs: samplePlaylist, tokens: 10 },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Cool Song");
    expect(wrapper.text()).toContain("Cool Song2");
    expect(wrapper.text()).toContain("Cool Song3");
    expect(wrapper.text()).toContain("Cool Song4");
  });
  it("Renders the album art of songs in the playlist", () => {
    const wrapper = mount(SessionPlaylist, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: { playlist_songs: samplePlaylist, tokens: 10 },
            },
          }),
        ],
      },
    });
    expect(wrapper.html()).toContain("image3.jpg");
    expect(wrapper.html()).toContain("image32.jpg");
    expect(wrapper.html()).toContain("image33.jpg");
    expect(wrapper.html()).toContain("image34.jpg");
  });
  it("Dpes not render the playlist when tokens = 0", () => {
    const wrapper = mount(SessionPlaylist, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: { playlist_songs: samplePlaylist, tokens: 0 },
            },
          }),
        ],
      },
    });
    expect(wrapper.html()).not.toContain("image3.jpg");
    expect(wrapper.html()).not.toContain("image32.jpg");
    expect(wrapper.html()).not.toContain("image33.jpg");
    expect(wrapper.html()).not.toContain("image34.jpg");
  });
});
