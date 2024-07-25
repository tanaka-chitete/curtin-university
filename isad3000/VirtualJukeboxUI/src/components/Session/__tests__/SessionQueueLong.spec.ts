import { describe, it, expect, vi } from "vitest";
import SessionQueueLong from "../SessionQueueLong.vue";
import { mount } from "@vue/test-utils";
import { createTestingPinia } from "@pinia/testing";
import type { playlistSong, queueElement } from "../../../stores/session";
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
    const wrapper = mount(SessionQueueLong, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("View Queue");
  });
  const sampleQueueTiny: queueElement[] = [
    { song_id: "SongOne1", votes: 4, time_added: 2232 },
  ];
  const sampleQueueShort: queueElement[] = [
    { song_id: "SongOne1", votes: 40, time_added: 2232 },
    { song_id: "SongOne2", votes: 41, time_added: 2232 },
    { song_id: "SongOne3", votes: 42, time_added: 2232 },
    { song_id: "SongOne4", votes: 43, time_added: 2232 },
  ];
  it("Renders Nothing when there is no song in queue", () => {
    const wrapper = mount(SessionQueueLong, {
      global: {
        plugins: [
          createTestingPinia({
            createSpy: vi.fn,
          }),
        ],
      },
    });
    expect(wrapper.text()).toContain("Add a song to the queue to get started!");
    expect(wrapper.html()).not.toContain("queue-song");
  });
  it("Renders nothing when there is one song in the queue", () => {
    const wrapper = mount(SessionQueueLong, {
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
    expect(wrapper.text()).toContain("Nothing next in the queue!");
    expect(wrapper.html()).not.toContain("queue-song");
  });
  it("Renders song names when the queue has more than 1 element", () => {
    const wrapper = mount(SessionQueueLong, {
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
    const wrapper = mount(SessionQueueLong, {
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
    const wrapper = mount(SessionQueueLong, {
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
  it("Renders number of votes when the queue has more than 1 element", () => {
    const wrapper = mount(SessionQueueLong, {
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
