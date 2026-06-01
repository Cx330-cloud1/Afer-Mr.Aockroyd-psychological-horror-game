<script setup lang="ts">
defineProps<{
  items: any[]
}>();

defineEmits<{
  (e: 'interact', id: string): void
}>();

function iconOf(item: any) {

  const name = item.name || '';

  if (name.includes('信'))
    return '/icons/letter.png';

  if (
      name.includes('拆信刀') ||
      name.includes('凶器') ||
      name.includes('伤口')
  )
    return '/icons/knife.png';

  if (name.includes('窗'))
    return '/icons/window.png';

  if (
      name.includes('门') ||
      name.includes('出口')
  )
    return '/icons/door.png';

  if (name.includes('镜子'))
    return '/icons/mirror.png';

  if (name.includes('手套'))
    return '/icons/gloves.png';

  if (name.includes('药箱'))
    return '/icons/bottle.png';

  if (name.includes('钥匙'))
    return '/icons/key.png';

  if (
      name.includes('医疗记录') ||
      name.includes('病历')
  )
    return '/icons/diary.png';

  if (name.includes('记录'))
    return '/icons/record.png';

  if (name.includes('尸体'))
    return '/icons/body.png';

  if (name.includes('小径'))
    return '/icons/footprints.png';

  if (
      name.includes('话语') ||
      name.includes('低声')
  )
    return '/icons/voice.png';

  if (
      name.includes('帕克') ||
      name.includes('管家')
  )
    return '/icons/people.png';

  return '/icons/search.png';
}
</script>

<template>

  <section class="panel evidence-panel">

    <h3>
      可检查的对象
    </h3>

    <div class="items">

      <button
          v-for="item in items"
          :key="item.id"
          class="item evidence-item"
          :class="{ danger: item.dangerous }"
          @click="$emit('interact', item.id)"
      >

        <div class="item-top">

          <img
              :src="iconOf(item)"
              class="item-icon"
              alt=""
          />

          <div class="item-info">

            <strong class="item-title">
              {{ item.name }}
            </strong>

            <p class="item-action">
              检查证物
            </p>

          </div>

        </div>

      </button>

    </div>

  </section>

</template>