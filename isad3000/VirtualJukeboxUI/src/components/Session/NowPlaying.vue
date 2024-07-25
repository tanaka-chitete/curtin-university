<script lang="ts">
/* Shows the currently playing song, as well as a progress bar of how long the song has left */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useSession } from "../../stores/session";
import { useUser } from "../../stores/user";
import { usePlayer } from "../../stores/player";
export default defineComponent({
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    const playerStore = usePlayer();
    return { userStore, sessionStore, playerStore };
  },
  data() {
    return {
      progressBarInterval: undefined as any,
      progress: "",
      duration: "",
    };
  },
  created() {
    this.playerStore.$subscribe(() => {
      this.updateProgressbar();
    });
  },
  mounted() {
    /* Updates the progress bar */
    this.progressBarInterval = setInterval(
      () =>
        (this.playerStore.song_progress += this.playerStore.paused ? 0 : 300),
      300
    );
  },
  unmounted() {
    clearInterval(this.progressBarInterval);
  },
  methods: {
    /* Updates the progress bar and the song duration */
    updateProgressbar() {
      const song_length = this.playerStore.song_length;
      const song_progress = this.playerStore.song_progress;
      /* Calculate percentage of song progress. Cannot go past 100%*/
      const percentage: number =
        song_progress && song_length
          ? Math.min((song_progress / song_length) * 100, 100)
          : 0;
      /* Set the width of the progress bar */
      const progress_bar = document.getElementById("progress-update");
      if (progress_bar)
        progress_bar.style["width"] = percentage.toString() + "%";
      /* Convert the milliseconds into a min:sec format */
      this.duration = this.millisToMinutesAndSeconds(
        this.playerStore.song_length
      );
      this.progress = this.millisToMinutesAndSeconds(
        Math.min(this.playerStore.song_progress, this.playerStore.song_length)
      );
    },
    /* Converts milliseconds into a min:sec format */
    millisToMinutesAndSeconds(millis: number) {
      const minutes = Math.floor(millis / 60000);
      const seconds = Math.round((millis % 60000) / 1000);
      return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
    },
  },
});
</script>

<template>
  <div class="nowplaying">
    <template v-if="sessionStore.queue.length > 0">
      <img
        :src="
          sessionStore.playlist_songs[sessionStore.queue[0].song_id].images[0]
        "
        class="playing-album"
      />
      <div class="song-info-container">
        <div
          class="song-title"
          :title="
            sessionStore.playlist_songs[sessionStore.queue[0].song_id].name
          "
        >
          {{ sessionStore.playlist_songs[sessionStore.queue[0].song_id].name }}
        </div>
        <div class="song-artist">
          {{
            sessionStore.playlist_songs[sessionStore.queue[0].song_id]
              .artists[0]
          }}
        </div>
        <div class="progress">
          <div class="progress-bar">
            <div class="progress-bar-progress" id="progress-update">&nbsp</div>
          </div>
          <span class="song-time" v-if="duration"
            >{{ progress }} / {{ duration }}</span
          >
        </div>
        <div class="session-info">
          <div v-if="sessionStore.queue[0]?.votes == 0" class="session-name">
            Autoplaying from playlist
          </div>
        </div>
      </div>
    </template>
    <template v-else>
      <div id="que" class="song-info-container">
        <div class="song-title">No Song</div>
        <div class="song-artist">
          Add a song from the playlist to get started!
        </div>
      </div>
    </template>
    <!-- Progress bar is currently acheived by having a div element with a background span the page-->
  </div>
</template>

<style lang="scss" scoped>
@import "../../assets/_typography.scss";

.nowplaying {
  display: flex;
}

.playing-album {
  /* This ensures the minimum width / height of the image is 100px regardless of screen size */
  width: calc(100px + 20vh);
  height: calc(100px + 20vh);
}

.song-info-container {
  margin-left: 24px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.song-title {
  @include text-title;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  font-weight: bold;
  line-height: 1.3;
  overflow: hidden;
  text-overflow: ellipsis;
}

.song-artist {
  @include text-subtitle;
}

.progress {
  display: flex;
  flex-direction: column;
}

.song-time {
  @include text-small;
  line-height: 0.5;
  margin-top: 8px;
}

.progress-bar {
  width: 100%;
  background-color: #666666;
  margin-top: 8px;
  margin-bottom: 8px;
  line-height: 0.5;
}

.progress-bar-progress {
  width: 0%;
  background-color: #00bd7e;
}

.session-info {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.session-name {
  @include text-content;
}

@media only screen and (max-device-width: 500px) {
  .nowplaying {
    width: 90vw;
    flex-direction: column;
    position: absolute;
    top: 0vh;
    left: 12vw;
  }

  .playing-album {
    height: calc(23vh + 23vw);
  }

  .song-info-container {
    position: relative;
    right: 20vw;
  }

  .song-title {
    @include text-mobile-large;
    overflow-x: scroll;
    padding-left: 10px;
  }

  .song-artist {
    @include text-mobile-small;
    width: 80vw;
    height: 25px;
    padding-left: 10px;
  }

  .song-time {
    @include text-mobile-small;
  }

  #que {
    position: relative;
    right: 10vw !important;
    top: 20vh !important;
  }

  .session-name {
    @include text-mobile-small;
    padding-top: 10px;
    padding-left: 27vw;
  }

  .progress {
    padding-left: 10px;
  }

  .modal {
    overflow-y: hidden !important;
  }
}
</style>
