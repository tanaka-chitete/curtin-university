<script lang="ts">
/* Popup modal which is seen when the user clicks the "view queue" button
Users can view the entire playlist through this modal */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useSession } from "../../stores/session";
/* Mixins */
import SongVote from "../../mixins/SongVote";
export default defineComponent({
  setup() {
    const sessionStore = useSession();
    return { sessionStore };
  },
  methods: {
    close() {
      /* Method which closes the modal when the close button is clicked */
      const modal = document.getElementById("showqueue");
      if (modal) {
        modal.style.display = "none";
      }
    },
    clickOutside(event: MouseEvent) {
      const target = event.target as HTMLInputElement;
      const id = target.id;
      if (id == "showqueue") {
        this.close();
      }
    },
    async vote(song_id: string | number) {
      /* Do not vote for a song if it has already been voted for by the user */
      if (!this.sessionStore.voted.has(song_id)) {
        const voteResponse: { success: boolean; message: string } =
          await SongVote.songVote(song_id);
        if (voteResponse.success) {
          this.$log.info(voteResponse.message);
        } else {
          this.$log.error(voteResponse.message);
        }
      }
    },
  },
});
</script>

<template>
  <div id="showqueue" class="modal" @click="clickOutside($event)">
    <div class="modal-content">
      <div class="top-bar">
        <span class="close" @click="close()"> &times;</span>
        <div class="modal-title">View Queue</div>
      </div>

      <!-- Show popup message if there are no songs in the queue -->
      <div v-if="sessionStore.queue.length == 0" class="queue">
        <div class="modal-text">Add a song to the queue to get started!</div>
      </div>
      <!-- Show the songs in the queue -->
      <div v-else>
        <!-- Show the currently playing song -->
        <div class="modal-sub-title">Now Playing</div>
        <div class="song">
          <img
            :src="
              sessionStore.playlist_songs[sessionStore.queue[0].song_id]
                .images[1]
            "
            class="song-album"
          />
          <div class="song-info">
            <div
              class="playlist-song"
              :title="
                sessionStore.playlist_songs[sessionStore.queue[0].song_id].name
              "
            >
              {{
                sessionStore.playlist_songs[sessionStore.queue[0].song_id].name
              }}
            </div>
            <div class="playlist-artist">
              {{
                sessionStore.playlist_songs[sessionStore.queue[0].song_id]
                  .artists[0]
              }}
            </div>
          </div>
        </div>
        <!-- Show the songs up next in the queue -->
        <div class="modal-sub-title">Up Next</div>
        <div v-if="sessionStore.queue.length > 1" class="queue">
          <div
            v-for="song in sessionStore.queue.slice(1)"
            class="song"
            v-bind:key="song.song_id"
          >
            <img
              :src="sessionStore.playlist_songs[song.song_id].images[1]"
              class="song-album"
            />
            <div class="song-info">
              <div
                class="playlist-song"
                :title="sessionStore.playlist_songs[song.song_id].name"
              >
                {{ sessionStore.playlist_songs[song.song_id].name }}
              </div>
              <div class="playlist-artist">
                {{ sessionStore.playlist_songs[song.song_id].artists[0] }}
              </div>
            </div>
            <q-icon
              v-if="sessionStore.voted.has(song.song_id)"
              name="thumb_up"
              class="thumb"
            />
            <q-icon
              v-else
              @click="vote(song.song_id)"
              name="o_thumb_up"
              class="thumb"
            />
            <div class="thumb">{{ song.votes }}</div>
          </div>
        </div>
        <div v-else class="modal-text">Nothing next in the queue!</div>
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

.modal-sub-title {
  @include text-small-bold;
  height: 4vh; // Spacing after sub-title
}

/* Close button */
.close {
  @include text-content;
  color: #00bd7e;
  float: right;
  background: none;
  border: none;
}

.thumb {
  padding-right: 15px;
  font-size: 3vh;
  color: #00bd7e;
}

.modal-text {
  @include text-small;
  color: #ffffff;
}

.close:hover,
.close:focus {
  color: #ffffff;
  text-decoration: none;
  cursor: pointer;
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
    //overflow-y: scroll;
  }

  .top-bar {
    @include modal-mobile-top-bar;
  }

  .modal-title {
    @include text-mobile-large;
    width: 100vw;
    padding-top: 5px;
  }
  .modal-sub-title,
  .modal-text {
    @include text-mobile-small;
  }

  .playlist-song,
  .playlist-artist {
    width: 20vh;
    @include text-mobile-small;
  }

  // NEED TO TEST!!!!
  .song-album {
    height: calc(4vh + 4vw);
    width: auto;
  }

  .queue {
    overflow-y: scroll;
    overflow-x: hidden;
    height: 52vh;
  }

  .close {
    @include modal-mobile-close;
  }
}
</style>
