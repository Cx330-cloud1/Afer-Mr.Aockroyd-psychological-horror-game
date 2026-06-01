<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import type { StoryNode, GameState } from '../types';

const props = defineProps<{
  node?: StoryNode;
  state?: GameState;
}>();

const displayedText = ref('');
const typingSpeed = 18;

function distort(text?: string) {
  if (!text) return '';
  const reliability = props.state?.narratorReliability ?? 100;
  if (reliability >= 50) return text;

  return text
      .split('')
      .map((c, i) =>
          c !== ' ' &&
          i % Math.max(2, Math.floor(reliability / 10 + 2)) === 0
              ? `<span class="text-distort">${c}</span>`
              : c
      )
      .join('');
}

const fullText = computed(() => props.node?.publicNarrative || '');

watch(
    fullText,
    (newText) => {
      displayedText.value = '';
      if (!newText) return;

      let index = 0;
      const timer = setInterval(() => {
        displayedText.value += newText[index];
        index++;
        if (index >= newText.length) clearInterval(timer);
      }, typingSpeed);
    },
    { immediate: true }
);
</script>

<template>
  <section
      :key="node?.id"
      class="story-card story-enter"
      :class="{
      disturbing: node?.disturbing,
      unreliable: (state?.narratorReliability || 100) < 50
    }"
  >

    <div class="story-header">
      <div class="case-tag">
        案件记录
      </div>

      <p class="scene">
        {{ node?.scene }}
      </p>
    </div>

    <div
        class="public-narrative typing-text"
        v-html="distort(displayedText)"
    />

    <div
        v-if="node?.innerThoughts"
        class="inner-thoughts"
    >
      <div class="thought-title">
        内心独白
      </div>

      <p>
        {{ node.innerThoughts }}
      </p>
    </div>

    <div
        v-if="(state?.narratorReliability || 100) < 30"
        class="warning"
    >
      ⚠ 记忆开始扭曲，你已无法确认自己所见是否真实。
    </div>

  </section>
</template>