import { defineStore } from "pinia";

export interface queueElement {
  song_id: string | number;
  votes: number;
  time_added: number;
}

export interface playlistSong {
  [song_id: string]: {
    album_name: string;
    album_uri: string;
    artists: string[];
    duration_ms: number;
    images: string[];
    name: string;
    track_num: number;
  };
}

export interface sessionType {
  id: string;
  session_name: string;
  session_description: string;
  session_location: { coordinates: number[] };
  connection_range: number;
  distance_to: number;
}

export interface playlistType {
  playlist_id: string;
  total_tracks: number;
  name: string;
}

export const MAX_TOKENS = 10;
export const HOST_TOKENS = 3;

export const useSession = defineStore({
  id: "session",
  state: () => ({
    /* Allow page navigation off the session page */
    allow_leave: false,
    /* Session playlist and song information */
    playlist_songs: {} as playlistSong,
    queue: [] as queueElement[],
    voted: new Set(),
    /* Session general information */
    session_id: "",
    session_name: "",
    session_description: "",
    connection_range: "",
    distance: "",
    host_id: "",
    /* User interacting with the session */
    websocket: undefined as any,
    tokens: 0,
  }),
  actions: {
    setPlaylist(newPlaylist: playlistSong) {
      this.playlist_songs = newPlaylist;
    },
    setQueue(newQueue: queueElement[]) {
      this.queue = newQueue;
    },
    setSessionID(newID: string) {
      this.session_id = newID;
    },
    setName(newName: string) {
      this.session_name = newName;
    },
    setDescription(newDescription: string) {
      this.session_description = newDescription;
    },
    setHostID(newID: string) {
      this.host_id = newID;
    },
    setSocket(socket: WebSocket) {
      this.websocket = socket;
      this.websocket.send(
        JSON.stringify({
          type: "user_joined",
        })
      );
    },
    allowLeave() {
      this.allow_leave = true;
    },
    setTokens(newTokens: number) {
      this.tokens = newTokens;
    },
    addToken() {
      if (this.tokens < MAX_TOKENS) this.tokens += 1;
    },
    useToken() {
      if (this.tokens > 0) this.tokens -= 1;
    },
    setCurrentlyPlaying(song: queueElement | undefined) {
      if (song?.song_id) this.queue.unshift(song);
    },
    addToQueue(song: queueElement) {
      this.queue.push(song);
      if (this.queue.length > 2) {
        this.sortQueue();
      }
    },
    voteForSong(song_id: string) {
      const song_index = this.queue.findIndex((e) => e.song_id == song_id);
      if (song_index != -1) {
        this.queue[song_index].votes += 1;
        this.sortQueue();
      }
    },
    sortQueue() {
      const firstSong = this.queue[0];
      this.queue.sort(function (a, b) {
        /*Ensures the first (currently playing) song is not changed */
        if (a == firstSong || b == firstSong) return 0;
        /* If votes are equal, sort by date added */ else if (
          a.votes == b.votes
        )
          return a.time_added - b.time_added;
        return b.votes - a.votes;
      });
    },
    popQueue() {
      const removedSong = this.queue.shift();
      this.voted.delete(removedSong?.song_id);
    },
    pickRandomSong() {
      const playlistSize: number = Object.keys(this.playlist_songs).length;
      const i: number = Math.floor(Math.random() * playlistSize);
      const songID: string = Object.keys(this.playlist_songs)[i];
      return songID;
    },
    clearSessionDetails() {
      this.queue = [] as queueElement[];
      this.playlist_songs = {} as playlistSong;
      this.session_name = "";
      this.host_id = "";
      this.session_id = "";
    },
  },
});
