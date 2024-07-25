<script lang="ts">
/* A short preview of the next 10 songs in the queue */
/* Imports */
import { defineComponent } from "vue";
/* Stores */
import { useSession } from "../../stores/session";
import { useUser } from "../../stores/user";
/* Mixins */
import SongVote from "../../mixins/SongVote";
export default defineComponent({
  setup() {
    const userStore = useUser();
    const sessionStore = useSession();
    return { userStore, sessionStore };
  },
  methods: {
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
  <div class="up-next-queue">
    <p id="upNext" class="up-next">Up Next</p>
    <div v-if="sessionStore.queue.length > 1" class="queue">
      <div
        v-for="song in sessionStore.queue.slice(1, 10)"
        class="queue-element"
        v-bind:key="song.song_id"
      >
        <!-- Limits loop to first 10 elements so entire queue is not rendered -->
        <img
          :src="sessionStore.playlist_songs[song.song_id].images[1]"
          class="queue-album"
        />
        <div
          class="queue-song"
          :title="sessionStore.playlist_songs[song.song_id].name"
        >
          {{ sessionStore.playlist_songs[song.song_id].name }}
        </div>
        <div
          class="queue-song song-artist"
          :title="sessionStore.playlist_songs[song.song_id].artists[0]"
        >
          {{ sessionStore.playlist_songs[song.song_id].artists[0] }}
        </div>
        <div class="thumb-container">
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
    </div>
    <div v-else class="up-next">Nothing next in the queue!</div>
  </div>
</template>

<style lang="scss" scoped>
@import "../../assets/_typography.scss";

.up-next {
  @include text-subtitle;
  margin: 1vh 0px;
  vertical-align: middle;
}

.queue {
  display: flex;
  width: 80vw;
  overflow-y: hidden;
  -ms-overflow-style: none;
  /* Hide scrollbar for IE, Edge and Firefox */
  scrollbar-width: none;
  /* Hide scrollbar for IE, Edge and Firefox */
}

/* Hide scrollbar for Chrome, Safari and Opera */
.queue::-webkit-scrollbar {
  display: none;
}

.queue-element {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 1vw;
}

.queue-album {
  width: 8vw;
  height: 8vw;
  text-align: left;
}

.queue-song {
  @include text-small;
  width: 8vw;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  text-align: left;
  font-weight: bold;
}

.song-artist {
  @include text-small;
}

.thumb-container {
  margin-top: 4px;
  display: flex;
  justify-content: left;
  align-items: center;
}

.thumb {
  font-size: 2vw;
  color: #00bd7e;
  cursor: pointer;
}

@media only screen and (max-device-width: 500px) {
  .up-next-queue {
    position: relative;
    width: 90vw;
    right: 10vh;
    top: 20%;
  }

  .queue {
    width: 90vw;
  }

  .queue-album {
    //height: 140px;
    height: calc(10vh + 10vw);
    width: auto;
  }

  .queue-song {
    @include text-mobile-small;
    width: calc(10vh + 10vw);
  }

  .thumb {
    font-size: calc(2vh + 3vw);
  }

  #upNext {
    padding-top: calc(1vh + 1vw);
    @include text-mobile-large;
  }
}
</style>
