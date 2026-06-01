<script setup lang="ts">
import { computed, onBeforeUnmount, ref } from 'vue';
import type { Choice, GameState } from '../types';
const props = defineProps<{ choices: Choice[]; state?: GameState }>();
const emit = defineEmits<{ select: [choiceId: string, timeTaken?: number] }>();
const activeChoice = ref<Choice | null>(null);
const startedAt = ref(0);
const timeLeft = ref(0);
let timer: number | undefined;
function resolve(key: string): number | boolean {
  const s = props.state;
  if (!s) return 0;
  if (key === 'selfAwareness') return s.selfAwareness;
  if (key === 'narratorReliability') return s.narratorReliability;
  if (key === 'poirotSuspicion') return s.poirotSuspicion;
  if (key === 'sanity') return s.sanity;
  if (key.startsWith('interacted.')) return !!s.interacted?.[key.slice(11)];
  if (key.startsWith('foundClues.')) return s.foundClues?.includes(key.slice(11));
  return 0;
}
function matches(key: string, c: any) {
  const v = resolve(key);
  if (c.boolValue !== undefined) return Boolean(v) === c.boolValue;
  const n = Number(v), e = c.value ?? 0;
  if (c.operator === 'GT') return n > e; if (c.operator === 'GTE') return n >= e; if (c.operator === 'LT') return n < e; if (c.operator === 'LTE') return n <= e; if (c.operator === 'NE') return n !== e;
  return n === e;
}
const availableChoices = computed(() => props.choices.filter(c => !c.requirements || Object.entries(c.requirements).every(([k, v]) => matches(k, v))));
function choose(choice: Choice) {
  if (choice.quickTimeEvent && activeChoice.value?.id !== choice.id) {
    activeChoice.value = choice; startedAt.value = Date.now(); timeLeft.value = choice.timeLimit || 10;
    timer = window.setInterval(() => { timeLeft.value -= 1; if (timeLeft.value <= 0) finish(choice); }, 1000);
    return;
  }
  finish(choice);
}
function finish(choice: Choice) {
  if (timer) clearInterval(timer);
  const taken = startedAt.value ? Math.ceil((Date.now() - startedAt.value) / 1000) : undefined;
  activeChoice.value = null; startedAt.value = 0; emit('select', choice.id, taken);
}
onBeforeUnmount(() => timer && clearInterval(timer));
</script>
<template>
  <section class="choice-panel">
    <button v-for="choice in availableChoices" :key="choice.id" class="choice" :class="{ timed: choice.quickTimeEvent, urgent: activeChoice?.id === choice.id && timeLeft <= 3 }" @click="choose(choice)">
      <span>{{ choice.text }}</span><small v-if="choice.quickTimeEvent">⏱ {{ activeChoice?.id === choice.id ? timeLeft : choice.timeLimit }} 秒</small>
    </button>
  </section>
</template>
