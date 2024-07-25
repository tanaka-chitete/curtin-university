import { describe, it, expect, vi } from "vitest";
import NowPlaying from "../NowPlaying.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";
import type { playlistSong, queueElement } from "../../../stores/session";

describe("NowPlaying", () => {
  const samplePlaylist: playlistSong = {
    SongOne1: {
      album_name: "Album",
      album_uri: "spotify:example",
      artists: ["Joe", "Swanson"],
      duration_ms: 30000,
      images: ["image1.jpg", "image2.jpg"],
      name: "Cool Song",
      track_num: 3,
    },
  };
  const sampleQueue: queueElement[] = [
    { song_id: "SongOne1", votes: 4, time_added: 2232 },
  ];
  it("Renders 'No Song' when there is no song in queue'", () => {
    const wrapper = mount(NowPlaying, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("No Song");
  });
  it("Renders the first queue song's song name", () => {
    const wrapper = mount(NowPlaying, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: { playlist_songs: samplePlaylist, queue: sampleQueue },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Cool Song");
  });
  it("Renders the first queue song's artist name", () => {
    const wrapper = mount(NowPlaying, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: { playlist_songs: samplePlaylist, queue: sampleQueue },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Joe");
  });
  it("Renders the first queue song's image", () => {
    const wrapper = mount(NowPlaying, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: { playlist_songs: samplePlaylist, queue: sampleQueue },
            },
          }),
        ],
      },
    });
    expect(wrapper.html()).toContain("image1.jpg");
  });
});
