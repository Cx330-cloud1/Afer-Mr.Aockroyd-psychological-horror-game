<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';

import StoryRenderer
  from './components/StoryRenderer.vue';

import ChoiceRenderer
  from './components/ChoiceRenderer.vue';

import InteractionPanel
  from './components/InteractionPanel.vue';

import StatusPanel
  from './components/StatusPanel.vue';

import FlashbackOverlay
  from './components/FlashbackOverlay.vue';

import MemoryGallery
  from './components/MemoryGallery.vue';

import { api }
  from './services/api';

import type {
  Flashback,
  GameResponse,
  InteractionResponse,
  MemoryFragment
} from './types';

const response =
    ref<GameResponse | null>(null);

const memories =
    ref<MemoryFragment[]>([]);

const notice = ref('');

const queue =
    ref<Flashback[]>([]);

const showIntro = ref(true);

setTimeout(() => {
  showIntro.value = false;
}, 2200);

const activeFlashback =
    computed(() => queue.value[0]);

const sceneClass = computed(() => {

  if (response.value?.ending) {
    return 'scene-ending';
  }

  const sanity =
      response.value?.state?.sanity ?? 100;

  if (sanity < 30) {
    return 'scene-nightmare';
  }

  if (sanity < 60) {
    return 'scene-investigation';
  }

  return 'scene-mansion';
});

async function load() {

  response.value =
      await api.start();

  memories.value =
      await api.memories();
}

async function select(
    choiceId: string,
    timeTaken?: number
) {

  applyResponse(
      await api.choice(
          choiceId,
          timeTaken
      )
  );
}

async function interact(id: string) {

  const r: InteractionResponse =
      await api.interact(id);

  notice.value =
      r.triggeredMemory
          ? `记忆解锁：${r.triggeredMemory.title}`
          : r.description;

  if (r.flashbacks?.length) {
    queue.value.push(...r.flashbacks);
  }

  if (response.value && r.state) {
    response.value.state = r.state;
  }
}

function applyResponse(
    r: GameResponse
) {

  response.value = r;

  if (r.flashbacks?.length) {
    queue.value.push(...r.flashbacks);
  }

  if (r.triggeredMemories?.length) {

    notice.value =
        `记忆解锁：${r.triggeredMemories
            .map(m => m.title)
            .join('、')}`;
  }
}

function popFlashback() {
  queue.value.shift();
}

async function restart() {

  showIntro.value = true;

  setTimeout(() => {
    showIntro.value = false;
  }, 2200);

  applyResponse(
      await api.restart()
  );

  notice.value = '';

  queue.value = [];
}

onMounted(load);
</script>

<template>

  <main
      class="app"
      :class="[
      sceneClass,
      {
        lowSanity:
          (response?.state?.sanity || 100)
          < 40
      }
    ]"
  >

    <div
        v-if="showIntro"
        class="intro-screen"
    >
      <div class="intro-file">

        <p class="intro-label">
          私人卷宗
        </p>

        <h1>
          罗杰疑案：双重叙述
        </h1>

        <p class="intro-subtitle">
          After Mr. Ackroyd
        </p>

        <p class="intro-warning">
          有些记录，不该被重新翻开。
        </p>

      </div>
    </div>

    <header class="game-header">

      <div class="title-area">

        <img
            src="/ui/logo.png"
            class="game-logo"
            alt="罗杰疑案：双重叙述"
        />

        <div class="title-text">

          <h1>
            AFTER MR ACKROYD
          </h1>

          <p class="subtitle">
            罗杰疑案：双重叙述
          </p>

        </div>

      </div>

      <button @click="restart">
        重新开始
      </button>

    </header>

    <section
        v-if="response?.ending"
        class="ending"
    >

      <h2>
        {{ response.ending.title }}
      </h2>

      <p>
        {{ response.ending.content }}
      </p>

    </section>

    <template v-else>

      <div class="layout">

        <div class="main-col">

          <StoryRenderer
              :node="response?.node"
              :state="response?.state"
          />

          <p
              v-if="notice"
              class="notice"
          >
            {{ notice }}
          </p>

          <InteractionPanel
              v-if="
              response?.node
                ?.interactables?.length
            "
              :items="
              response.node.interactables
            "
              @interact="interact"
          />

          <ChoiceRenderer
              v-if="response?.node?.choices"
              :choices="response.node.choices"
              :state="response.state"
              @select="select"
          />

        </div>

        <div class="side-col">

          <StatusPanel
              :state="response?.state"
          />

          <MemoryGallery
              :memories="memories"
              :state="response?.state"
          />

        </div>

      </div>

    </template>

    <FlashbackOverlay
        :flashback="activeFlashback"
        @done="popFlashback"
    />

  </main>

</template>