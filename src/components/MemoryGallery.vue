<script setup lang="ts">
import type {
  MemoryFragment,
  GameState
} from '../types';

defineProps<{
  memories: MemoryFragment[];
  state?: GameState;
}>();

const imageMap: Record<string, string> = {
  memory_1_1: '/memory/memory_1_1.png',
  memory_letter: '/memory/memory_letter.png',
  memory_letter_opener: '/memory/memory_letter_opener.png',
  memory_window: '/memory/memory_window.png',
  memory_weapon: '/memory/memory_weapon.png',
  memory_handwriting: '/memory/memory_handwriting.png',
  memory_medicine: '/memory/memory_medicine.png',
  memory_gloves: '/memory/memory_gloves.png',
  memory_mirror: '/memory/memory_mirror.png',
  memory_secret_door: '/memory/memory_secret_door.png',
  memory_apology: '/memory/memory_apology.png'
};
</script>

<template>

  <section class="memory-gallery">

    <h3>
      记忆档案
    </h3>

    <div
        v-for="m in memories"
        :key="m.id"
        class="memory-card"
        :class="{
    unlocked:
      state?.collectedMemories
        ?.includes(m.id),

    locked:
      !state?.collectedMemories
        ?.includes(m.id)
  }"
    >

      <template
          v-if="
    state?.collectedMemories
      ?.includes(m.id)
  "
      >

        <div class="memory-photo">

          <img
              :src="
          imageMap[m.id]
          || '/memory/memory_1_1.png'
        "
              alt=""
          />

        </div>

        <div class="memory-unlocked-banner">
          新的记忆已记录
        </div>

        <div class="memory-content">

          <h4>
            {{ m.title }}
          </h4>

          <p>
            {{ m.content }}
          </p>

        </div>

      </template>

      <template v-else>

        <div class="memory-locked">

          <div class="memory-photo locked-photo">
            ?
          </div>

          <p>
            影像损毁
          </p>

        </div>

      </template>

    </div>

  </section>

</template>
