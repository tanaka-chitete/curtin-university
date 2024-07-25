import { defineStore } from "pinia";

export const usePlayer = defineStore({
  id: "player",
  state: () => ({
    current_song: "" /*The ID of the currently playing song */,
    song_length: 0 /* Song length / song progress * 100 = progress bar */,
    song_progress: 0,
    paused: true /* Is the current song paused? */,
    device_ready: false /* Has the web player been initalised? */,
    starting_first_song: false /* Is the first song being queued? */,
    autoplay_allowed: false /* Has the first song been queued? */,
    swapping_songs: true /* Is the SDK in the process of changing songs? */,
  }),
  actions: {
    sessionDefault() {
      this.current_song = "";
      this.song_length = 0;
      this.song_progress = 0;
      this.paused = true;
      this.device_ready = false;
      this.starting_first_song = false;
      this.autoplay_allowed = false;
      this.swapping_songs = true;
    },
  },
});
