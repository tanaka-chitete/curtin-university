import { describe, it, expect, vi } from "vitest";
import { mount } from "@vue/test-utils";
import SessionQueueShort from "../SessionQueueShort.vue";
import { createTestingPinia } from "@pinia/testing";
import { Quasar } from "quasar";
import type { playlistSong, queueElement } from "../../../stores/session";

describe("SessionQueueShort", () => {
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
  const sampleQueueTiny: queueElement[] = [
    { song_id: "SongOne1", votes: 4, time_added: 2232 },
  ];
  const sampleQueueShort: queueElement[] = [
    { song_id: "SongOne1", votes: 40, time_added: 2232 },
    { song_id: "SongOne2", votes: 41, time_added: 2232 },
    { song_id: "SongOne3", votes: 42, time_added: 2232 },
    { song_id: "SongOne4", votes: 43, time_added: 2232 },
  ];
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
  it("Renders Nothing when there is no song in queue", () => {
    const wrapper = mount(SessionQueueShort, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Up Next");
    expect(wrapper.html()).not.toContain("queue-song");
  });
  it("Renders nothing when there is one song in the queue", () => {
    const wrapper = mount(SessionQueueShort, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: {
                playlist_songs: samplePlaylist,
                queue: sampleQueueTiny,
              },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Up Next");
    expect(wrapper.html()).not.toContain("queue-song");
  });
  it("Renders song names when the queue has more than 1 element", () => {
    const wrapper = mount(SessionQueueShort, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: {
                playlist_songs: samplePlaylist,
                queue: sampleQueueShort,
              },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Up Next");
    expect(wrapper.text()).toContain("Cool Song2");
    expect(wrapper.text()).toContain("Cool Song3");
    expect(wrapper.text()).toContain("Cool Song4");
  });
  it("Renders artist names when the queue has more than 1 element", () => {
    const wrapper = mount(SessionQueueShort, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: {
                playlist_songs: samplePlaylist,
                queue: sampleQueueShort,
              },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Up Next");
    expect(wrapper.text()).toContain("Joe2");
    expect(wrapper.text()).toContain("Joe3");
    expect(wrapper.text()).toContain("Joe4");
  });
  it("Renders album art when the queue has more than 1 element", () => {
    const wrapper = mount(SessionQueueShort, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: {
                playlist_songs: samplePlaylist,
                queue: sampleQueueShort,
              },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Up Next");
    expect(wrapper.html()).toContain("image22.jpg");
    expect(wrapper.html()).toContain("image23.jpg");
    expect(wrapper.html()).toContain("image24.jpg");
  });
  it("Does not render elements past the 10th queue element", () => {
    const wrapper = mount(SessionQueueShort, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: {
                playlist_songs: samplePlaylist,
                queue: sampleQueueLong,
              },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Up Next");
    expect(wrapper.text()).not.toContain("Album2");
  });
  it("Renders number of votes when the queue has more than 1 element", () => {
    const wrapper = mount(SessionQueueShort, {
      global: {
        plugins: [
          Quasar,
          createTestingPinia({
            createSpy: vi.fn,
            initialState: {
              session: {
                playlist_songs: samplePlaylist,
                queue: sampleQueueShort,
              },
            },
          }),
        ],
      },
    });
    expect(wrapper.text()).not.toContain("40");
    expect(wrapper.html()).toContain("41");
    expect(wrapper.html()).toContain("42");
    expect(wrapper.html()).toContain("43");
  });
});
