import { describe, it, expect } from "vitest";
import { createPinia, setActivePinia } from "pinia";
import {
  MAX_TOKENS,
  useSession,
  type queueElement,
} from "../../stores/session";
import type { playlistSong } from "../session";

describe("SessionStore", () => {
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
  function songFactory(votes: number, id: string, time_added?: number) {
    return { song_id: id, votes: votes, time_added: time_added ?? 2232 };
  }
  it("setName method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setName("TestName");
    expect(sessionStore.session_name).toBe("TestName");
  });
  it("setHostID method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setHostID("TestID");
    expect(sessionStore.host_id).toBe("TestID");
  });
  it("setTokens method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setTokens(4);
    expect(sessionStore.tokens).toBe(4);
  });
  it("setSessionID method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setSessionID("TestSessionID");
    expect(sessionStore.session_id).toBe("TestSessionID");
  });
  it("setDescription method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setDescription("TestDesc");
    expect(sessionStore.session_description).toBe("TestDesc");
  });
  it("setPlaylist method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setPlaylist(samplePlaylist);
    expect(sessionStore.playlist_songs).toStrictEqual(samplePlaylist);
  });
  it("setQueue method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setQueue(sampleQueue);
    expect(sessionStore.queue).toStrictEqual(sampleQueue);
  });
  it("addToken method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setTokens(4);
    expect(sessionStore.tokens).toBe(4);
    sessionStore.addToken();
    expect(sessionStore.tokens).toBe(5);
    sessionStore.setTokens(MAX_TOKENS);
    sessionStore.addToken();
    expect(sessionStore.tokens).toBe(MAX_TOKENS);
  });
  it("useToken method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setTokens(4);
    expect(sessionStore.tokens).toBe(4);
    sessionStore.useToken();
    expect(sessionStore.tokens).toBe(3);
    sessionStore.setTokens(0);
    sessionStore.useToken();
    expect(sessionStore.tokens).toBe(0);
  });
  it("setCurrentlyPlaying method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    sessionStore.setQueue(sampleQueue);
    expect(sessionStore.queue).toStrictEqual(sampleQueue);
    sessionStore.setCurrentlyPlaying(undefined);
    expect(sessionStore.queue).toStrictEqual(sampleQueue);
    sessionStore.setCurrentlyPlaying(songFactory(70, "1000"));
    expect(sessionStore.queue[0].song_id).toBe("1000");
  });
  it("sortQueue method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    const testQueueSong1 = songFactory(30, "123");
    const testQueueSong2 = songFactory(10, "123");
    const testQueueSong3 = songFactory(20, "123");
    const queue = [testQueueSong1, testQueueSong2, testQueueSong3];
    sessionStore.setQueue(queue);
    expect(sessionStore.queue).toStrictEqual(queue);
    sessionStore.sortQueue();
    expect(sessionStore.queue[0]).toStrictEqual(testQueueSong1);
    expect(sessionStore.queue[1]).toStrictEqual(testQueueSong3);
    expect(sessionStore.queue[2]).toStrictEqual(testQueueSong2);
  });
  it("sortQueue method with equal votes", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    const testQueueSong1 = songFactory(30, "123", 1);
    const testQueueSong2 = songFactory(10, "123", 1);
    const testQueueSong3 = songFactory(20, "123", 2);
    const testQueueSong4 = songFactory(20, "123", 4);
    const testQueueSong5 = songFactory(20, "123", 3);
    const queue = [
      testQueueSong1,
      testQueueSong2,
      testQueueSong3,
      testQueueSong4,
      testQueueSong5,
    ];
    sessionStore.setQueue(queue);
    expect(sessionStore.queue).toStrictEqual(queue);
    sessionStore.sortQueue();
    expect(sessionStore.queue[0]).toStrictEqual(testQueueSong1);
    expect(sessionStore.queue[1]).toStrictEqual(testQueueSong3);
    expect(sessionStore.queue[2]).toStrictEqual(testQueueSong5);
    expect(sessionStore.queue[3]).toStrictEqual(testQueueSong4);
    expect(sessionStore.queue[4]).toStrictEqual(testQueueSong2);
  });
  it("voteForSong method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    const testQueueSong1 = songFactory(30, "1");
    const testQueueSong2 = songFactory(10, "2");
    const testQueueSong3 = songFactory(20, "3");
    const queue = [testQueueSong1, testQueueSong2, testQueueSong3];
    sessionStore.setQueue(queue);
    expect(sessionStore.queue).toStrictEqual(queue);
    sessionStore.sortQueue();
    expect(sessionStore.queue[0]).toStrictEqual(testQueueSong1);
    expect(sessionStore.queue[1]).toStrictEqual(testQueueSong3);
    expect(sessionStore.queue[2]).toStrictEqual(testQueueSong2);
    sessionStore.voteForSong("2");
    expect(sessionStore.queue[2].votes).toBe(11);
  });
  it("addToQueue method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    const testQueueSong = songFactory(10, "123");
    sessionStore.addToQueue(testQueueSong);
    expect(sessionStore.queue).toStrictEqual([testQueueSong]);
  });
  it("songs are sorted depending on votes (except for the first song)", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    const testQueueSongOne = songFactory(10, "1");
    const testQueueSongTwo = songFactory(20, "2");
    const testQueueSongThree = songFactory(30, "3");
    sessionStore.addToQueue(testQueueSongOne);
    expect(sessionStore.queue[0]).toStrictEqual(testQueueSongOne);
    sessionStore.addToQueue(testQueueSongTwo);
    expect(sessionStore.queue[0]).toStrictEqual(testQueueSongOne);
    expect(sessionStore.queue[1]).toStrictEqual(testQueueSongTwo);
    sessionStore.addToQueue(testQueueSongThree);
    expect(sessionStore.queue[0]).toStrictEqual(testQueueSongOne);
    expect(sessionStore.queue[1]).toStrictEqual(testQueueSongThree);
    expect(sessionStore.queue[2]).toStrictEqual(testQueueSongTwo);
  });
  it("Pops a song from the queue", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    const testQueueSongOne = songFactory(10, "1");
    const testQueueSongTwo = songFactory(20, "2");
    const testQueueSongThree = songFactory(30, "3");
    sessionStore.addToQueue(testQueueSongOne);
    sessionStore.addToQueue(testQueueSongTwo);
    sessionStore.addToQueue(testQueueSongThree);
    expect(sessionStore.queue[0]).toStrictEqual(testQueueSongOne);
    expect(sessionStore.queue[1]).toStrictEqual(testQueueSongThree);
    expect(sessionStore.queue[2]).toStrictEqual(testQueueSongTwo);
    sessionStore.popQueue();
    expect(sessionStore.queue[0]).toStrictEqual(testQueueSongThree);
    expect(sessionStore.queue[1]).toStrictEqual(testQueueSongTwo);
    sessionStore.popQueue();
    expect(sessionStore.queue[0]).toStrictEqual(testQueueSongTwo);
    sessionStore.popQueue();
    expect(sessionStore.queue.length).toStrictEqual(0);
  });
  it("clearSessionDetails method", () => {
    setActivePinia(createPinia());
    const sessionStore = useSession();
    const testQueueSongOne = songFactory(10, "1");
    sessionStore.setSessionID("TestSessionID");
    sessionStore.addToQueue(testQueueSongOne);
    sessionStore.setName("TestName");
    sessionStore.clearSessionDetails();
    expect(sessionStore.queue).toStrictEqual([]);
    expect(sessionStore.session_name).toStrictEqual("");
    expect(sessionStore.session_id).toStrictEqual("");
  });
});
