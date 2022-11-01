<template>
  <q-page class="row items-center justify-evenly">
    <h1>Jemshit is here</h1>
    <div class="row items-center justify-evenly">
      <div v-for="(yacht, id) in yachts" :key="id">
        {{ yacht }}
      </div>
    </div>
  </q-page>
</template>

<script setuplang="ts">
import axios from 'axios';
import { defineComponent, onMounted, ref } from 'vue';

export default defineComponent({
  name: 'IndexPage',

  setup() {
    const yachts = ref(null);
    onMounted(() => {
      return axios.get('http://localhost:8080/api/v1/yachts/all').then((response) => {
        yachts.value = response.data;

      })
    })

    return { yachts };
  }


});

</script>
