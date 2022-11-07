<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>

        <q-toolbar-title>
          Jimmy's App
        </q-toolbar-title>
        <q-btn color="secondary" label="Admin Panel" to="/admin" />
      </q-toolbar>
    </q-header>



    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'UserLayout',


  setup() {

    return {

    }
  }
});
</script>
