<script setup lang="ts">
import { watch } from 'vue';
import type { Flashback } from '../types';

const props = defineProps<{
  flashback?: Flashback
}>();

const emit = defineEmits<{
  done: []
}>();

watch(
    () => props.flashback,
    fb => {
      if (fb) {
        window.setTimeout(
            () => emit('done'),
            fb.duration || 1800
        );
      }
    }
);
</script>

<template>

  <div
      v-if="flashback"
      class="flashback"
      :class="flashback.type.toLowerCase()"
  >

    <div class="flashback-noise"></div>

    <div class="blood-layer"></div>

    <article class="flashback-file">

      <div class="flashback-stamp">
        MEMORY TRACE
      </div>

      <h2>
        {{ flashback.title }}
      </h2>

      <p>
        {{ flashback.content }}
      </p>

      <small>
        理智 -{{ flashback.sanityCost }}
      </small>

    </article>

  </div>

</template>
