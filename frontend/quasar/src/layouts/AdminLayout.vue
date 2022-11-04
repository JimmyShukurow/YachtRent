<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>

        <q-toolbar-title>
          Quasar App
        </q-toolbar-title>
        <q-btn color="secondary" label="logout" @click="logout()" />
      </q-toolbar>
    </q-header>

    <q-drawer show-if-above bordered>
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

<script setup lang="ts">
import axios from 'axios';
import { useUserStore } from 'src/stores/user-store';

const userStore = useUserStore()
const url = import.meta.env.VITE_BACKEND_URL


function logout() {
  console.log(process.env);

  axios.post(url + 'users/logout', {}, { headers: { Authorization: `Bearer ${userStore.getToken}` } })
    .then((res) => { console.log(res); userStore.deleteUser() })
}

const pages = [
  { name: 'Yachts', url: '/yachts' },
  { name: 'Users', url: '/users' }
]

</script>
