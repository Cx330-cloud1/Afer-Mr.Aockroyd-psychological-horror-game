<script setup lang="ts">
import type { GameState } from '../types';

defineProps<{
  state?: GameState
}>();
</script>

<template>

  <section class="status case-status">

    <div class="case-status-title">
      <h3>状态</h3>
      <span>调查记录</span>
    </div>

    <div class="status-line">

      <div class="status-line-head">
        <span>理智值</span>
        <em>{{ state?.sanity }}</em>
      </div>

      <div class="case-meter">
        <b
            class="sanity-fill"
            :style="{ width: `${state?.sanity || 0}%` }"
        />
      </div>

    </div>

    <div class="status-line">

      <div class="status-line-head">
        <span>自我认知</span>
        <em>{{ state?.selfAwareness }}</em>
      </div>

      <div class="case-meter">
        <b
            class="awareness-fill"
            :style="{ width: `${state?.selfAwareness || 0}%` }"
        />
      </div>

    </div>

    <div class="status-line">

      <div class="status-line-head">
        <span>叙述可信度</span>
        <em>{{ state?.narratorReliability }}</em>
      </div>

      <div class="case-meter">
        <b
            class="reliability-fill"
            :style="{ width: `${state?.narratorReliability || 0}%` }"
        />
      </div>

    </div>

    <div class="case-status-note">
      档案状态：
      <span>
        {{
          (state?.narratorReliability || 100) < 40
              ? '记录异常'
              : '记录稳定'
        }}
      </span>
    </div>

  </section>

</template>