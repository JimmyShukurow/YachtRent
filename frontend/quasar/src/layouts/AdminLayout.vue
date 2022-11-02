<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>

        <q-toolbar-title>
          Quasar App
        </q-toolbar-title>

      </q-toolbar>
    </q-header>

    <q-drawer v-model="leftDrawerOpen" show-if-above bordered>
      <q-list>
        <q-item-label header>
          Essential Links
        </q-item-label>

        <div v-for="(page, id) in pages" :key="id">

          <q-item clickable :to="'/admin' + page.url">
            <q-item-section>
              {{ page.name }}
            </q-item-section>
          </q-item>

        </div>

      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'AdminLayout',


  setup() {
    const leftDrawerOpen = ref(false)
    const pages = [
      { name: 'Yachts', url: '/yachts' },
      { name: 'Users', url: '/users' }
    ]

    return {
      pages,
      leftDrawerOpen,
      toggleLeftDrawer() {
        leftDrawerOpen.value = !leftDrawerOpen.value
      }
    }
  }
});
</script>
