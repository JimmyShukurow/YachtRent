<template>
  <div>
    <h1>Yacht Form here</h1>
    <div class="q-gutter-sm row items-start">
      <q-uploader url="http://localhost:8080/api/v1/yachts/upload-image" label="Individual upload" field-name="image"
        auto-upload multiple style="max-width: 300px; height: 100%;" accept="image/*"
        :headers="[{ name: 'Authorization', value: `Bearer ${user.getToken}` }]" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from 'src/stores/user-store';
const user = useUserStore()
</script>
