<script lang="ts">
/* Popup modal which is seen when the user clicks the "view playlist" button
Users can pick a song from the playlist to add to the queue
Currently this does not make any API calls, check for tokens, or use websockets to add the song
to all user's queues in the session */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useSession } from "../../stores/session";
/* Mixins */
import AddQueue from "../../mixins/AddQueue";
export default defineComponent({
  setup() {
    const sessionStore = useSession();
    return { sessionStore };
  },
  data() {
    return {
      filter: "",
    };
  },
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const spotifyModal = document.getElementById("showplaylist");
      if (spotifyModal) {
        spotifyModal.style.display = "none";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "showplaylist") {
        this.close();
      }
    },
    async addToQueue(song_id: string | number) {
      /* Do not add song to the queue if it is already in the queue */
      if (
        this.sessionStore.queue.filter((e) => {
          return e.song_id == song_id;
        }).length == 0 &&
        /* Or, if the user has no tokens */
        this.sessionStore.tokens > 0
      ) {
        const queueReponse: { success: boolean; message: string } =
          await AddQueue.addQueue(song_id);
        if (queueReponse.success) {
          this.$log.info(queueReponse.message);
        } else {
          this.$log.error(queueReponse.message);
        }
      }
    },
    millisToMinutesAndSeconds(millis: number) {
      const minutes = Math.floor(millis / 60000);
      const seconds = Math.round((millis % 60000) / 1000);
      return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
    },
  },
});
</script>

<template>
  <div id="showplaylist" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <div class="top-bar">
        <span class="close" @click="close()"> &times;</span>
        <div class="modal-title">Add song to queue</div>
        <input placeholder="Search" class="login-box" v-model="filter" />
        <div class="tokens">
          <p v-if="sessionStore.tokens">Tokens: {{ sessionStore.tokens }}</p>
          <p v-else>Whoops, you're all out of tokens! Come back in a moment.</p>
        </div>
      </div>

      <div class="song-list-container">
        <template v-if="sessionStore.tokens">
          <!-- <input placeholder="Search" class="login-box" v-model="filter" /> -->
          <template
            v-for="(song, song_id) in sessionStore.playlist_songs"
            v-bind:key="song_id"
          >
            <div
              class="song"
              v-bind:value="song"
              v-if="
                (song.images &&
                  song.name.toLowerCase().includes(filter.toLowerCase())) ||
                song.artists[0].toLowerCase().includes(filter.toLowerCase())
              "
              @click="addToQueue(song_id)"
            >
              <img :src="song.images[2]" class="song-album" />
              <div class="song-info">
                <div class="playlist-song" :title="song.name">
                  {{ song.name }}
                </div>
                <div class="playlist-artist">
                  {{ song.artists[0] }}
                </div>
                <p class="song-time">
                  {{ millisToMinutesAndSeconds(song.duration_ms) }}
                </p>
              </div>

              <q-icon name="add" class="add-icon" />
            </div>
          </template>
        </template>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "../../assets/typography.scss";
@import "../../assets/_modal.scss";
@import "../../assets/_button.scss";
@import "../../assets/_container.scss";
.modal {
  @include modal;
}

/* Modal Content/Box */
.modal-content {
  @include modal-content;
}

.modal-title {
  @include text-content-bold;
  height: 7.5vh; // Spacing after title
  width: 50%; // Prevent it from overlapping the close button, preventing it from being clicked
}

/* Close button */
.close {
  @include text-content;
  color: #00bd7e;
  float: right;
  background: none;
  border: none;
  z-index: 10;
}
.tokens {
  @include text-small;
  font-size: 1vw;
}
.add-icon {
  padding-right: 15px;
  font-size: 3vh;
}

.modal-text {
  color: #ffffff;
}

.close:hover,
.close:focus {
  color: #ffffff;
  text-decoration: none;
  cursor: pointer;
}

/* The 'Add a song' input field */
.login-box {
  @include button-box;
  background: #ffffff;
  color: #000000;
  border-radius: 1vh; // Override default button-box border radius
  text-align: left; // Override default text-alignment
  padding-left: 1vh; // Override default padding
  width: 100%; // Override default width to span entire popup
  margin-bottom: 20px;
}

.login-box:focus {
  outline: none;
  /* Change border colour */
  border: 3px solid #188ac3;
}

::placeholder {
  color: #a0acbc;
}

.error {
  @include text-small;
  color: red;
}
.song {
  @include text-small;
  display: flex;
  flex-direction: row;
  align-items: center;
  cursor: pointer;
  height: 10vh;
}

.song:hover {
  background-color: #3a597c;
  border-radius: 10px;
}

.song-info {
  @include text-small;
  display: flex;
  flex-direction: column;
  margin-left: 15px;
  width: 100%;
  align-items: right;
}

.playlist-song {
  @include text-small;
  width: 25vw;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  text-align: left;
  font-weight: bold;
}

.playlist-artist {
  @include text-small;
  width: 25vw;
  text-overflow: ellipsis;
  overflow: hidden;
}

.song-album {
  width: min(10vw, 10vh);
  padding-left: 15px;
}

// For mobile view
@media only screen and (max-device-width: 500px) {
  .modal-content {
    @include modal-mobile-content;
  }

  .tokens {
    @include text-mobile-small;
    padding-bottom: 10px;
    padding-left: 1.6vw;
  }

  .modal-title {
    @include text-mobile-large;
    width: 100vw;
    padding-left: 2vw;
  }

  .login-box {
    @include text-mobile-small;
    width: 80vw;
    height: 40px;
  }

  .playlist-song,
  .playlist-artist {
    width: 25vh;
    @include text-mobile-small;
  }

  .song-album {
    height: calc(3vw + 6vh);
    width: calc(5vw + 7vh);
  }

  .close {
    @include modal-mobile-close;
    height: 1px;
  }

  .song-time {
    @include text-mobile-small;
  }
  .song {
    height: fit-content;
    width: 50vw;
  }
  .song:hover {
    background-color: rgba(255, 255, 255, 0);
  }
  .song-list-container {
    height: 55vh !important;
    overflow-y: scroll;
    overflow-x: hidden;
  }
}
</style>
