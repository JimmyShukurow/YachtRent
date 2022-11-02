<template>
  <div>

    <q-card class="my-card q-ma-sm">
      <img
        src="https://cdn.boatinternational.com/convert/files/2020/12/59858a70-3bb7-11eb-957a-6996e21593a4-top-25-Azzam-credit-Klaus-Jordan-Lurssen.jpg/r%5Bwidth%5D=1920/59858a70-3bb7-11eb-957a-6996e21593a4-top-25-Azzam-credit-Klaus-Jordan-Lurssen.webp">
      <q-card-section>
        <div class="text-h6">{{ yacht.name }}</div>
        <div class="text-subtitle2">{{ yacht.description }}</div>
      </q-card-section>
      <q-card-section>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit
      </q-card-section>
    </q-card>
  </div>
</template>
<script setup lang="ts">
defineProps<{
  yacht?: object
}>()
</script>
