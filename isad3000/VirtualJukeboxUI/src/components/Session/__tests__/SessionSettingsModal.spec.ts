import { describe, it, expect, vi } from "vitest";
import SessionSettings from "../SessionSettingsModal.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";
import type { playlistSong, queueElement } from "../../../stores/session";

describe("ShareSessionModal", () => {
  it("Renders the modal title", () => {
    const wrapper = mount(SessionSettings, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Session Information");
  });
  it("Renders the modal body", () => {
    const wrapper = mount(SessionSettings, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Name");
    expect(wrapper.text()).toContain("Description");
    expect(wrapper.text()).toContain("Songs in playlist");
    expect(wrapper.text()).toContain("Songs in queue");
  });
  it("Renders the session information body", () => {
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
      SongOne2: {
        album_name: "Album2",
        album_uri: "spotify:example",
        artists: ["Joe2", "Swanson"],
        duration_ms: 30000,
        images: ["image12.jpg", "image22.jpg"],
        name: "Cool Song2",
        track_num: 3,
      },
      SongOne3: {
        album_name: "Album3",
        album_uri: "spotify:example",
        artists: ["Joe3", "Swanson"],
        duration_ms: 30000,
        images: ["image13.jpg", "image23.jpg"],
        name: "Cool Song3",
        track_num: 3,
      },
      SongOne4: {
        album_name: "Album4",
        album_uri: "spotify:example",
        artists: ["Joe4", "Swanson4"],
        duration_ms: 30000,
        images: ["image14.jpg", "image24.jpg"],
        name: "Cool Song4",
        track_num: 3,
      },
    };
    const sampleQueueLong: queueElement[] = [
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne1", votes: 4, time_added: 2232 },
      { song_id: "SongOne2", votes: 4, time_added: 2232 },
    ];
    const wrapper = mount(SessionSettings, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: {
                session_name: "Josh's Session",
                session_description: "Fun Songs",
                playlist_songs: samplePlaylist,
                queue: sampleQueueLong,
              },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Josh's Session");
    expect(wrapper.text()).toContain("Fun Songs");
    expect(wrapper.text()).toContain("4");
    expect(wrapper.text()).toContain(sampleQueueLong.length);
  });
});
