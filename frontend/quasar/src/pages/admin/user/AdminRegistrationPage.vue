<template>
  <div class="column items-center ">
    <div v-if="!expired">
      <h1>Register here</h1>
      <form @submit.prevent="submit">
        <q-input v-model="user.firstName" type="text" label="First Name" class="login-form" />
        <q-input v-model="user.lastName" type="text" label="Last Name" class="login-form" />
        <q-input v-model="user.username" type="text" label="Username" class="login-form" />
        <q-input v-model="user.password" type="password" label="Password" class="login-form" />
        <q-input v-model="confirm" type="password" label="Confirm Password" class="login-form" />

        <div class="q-mt-lg row justify-between">
          <q-btn color="primary" icon="app_registration" label="Register" type="submit" class="q-ml-xl" />
        </div>
      </form>


    </div>
    <div v-else>
      <h1>Link is expired</h1>
    </div>


  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { useUserStore } from 'src/stores/user-store';
import { onBeforeMount, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';

const userStore = useUserStore();

const firstName = ref('')
const lastName = ref('')
const username = ref('')
const password = ref('')
const confirm = ref('')
const route = useRoute()
const expired = ref(false)
const url = import.meta.env.VITE_BACKEND_URL
const user = reactive({
  firstName,
  lastName,
  username,
  password
})

onBeforeMount(() => {

  axios.post(url + 'users/check-link/' + route.params.hash).then(res => {
    console.log(res.status);

  }).catch(() => {
    expired.value = true
  });


})

function submit() {

  axios.post(url + 'users/register', user).then((res) => {
    userStore.setName(res.data.fullName)
    userStore.setUsername(res.data.username)
    userStore.setToken(res.data.token)
  })
}

</script>

