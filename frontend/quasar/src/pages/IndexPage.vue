<template>
  <q-page class="column items-center">
    <h1>Welcome to yacht renting site</h1>
    <div class="row items-center">
      <div v-for="(yacht, id) in yachts" :key="id">
        <YachtComponent :yacht="yacht"></YachtComponent>
      </div>
    </div>
  </q-page>
</template>

<script lang="ts">
import axios from 'axios';
import YachtComponent from 'src/components/YachtComponent.vue';
import { defineComponent, onMounted, ref } from 'vue';

export default defineComponent({
  name: 'IndexPage',
  components: {
    YachtComponent
  },

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
