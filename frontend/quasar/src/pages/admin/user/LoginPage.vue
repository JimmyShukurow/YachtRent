<template>
  <div class="column items-center ">

    <q-card class="q-mt-xl login-form">
      <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" align="justify"
        narrow-indicator>
        <q-tab name="login" label="Login" />
        <q-tab name="register" label="Register" />
      </q-tabs>
    </q-card>

    <q-tab-panels v-model="tab" animated class="q-mt-xl ">
      <q-tab-panel name="login">
        <q-form @submit="submit" @refresh="submit" class="q-gutter-md">
          <h1>Login here</h1>
          <q-input v-model="user.username" type="text" label="Username" class="login-form" />
          <q-input v-model="user.password" type="password" label="Password" class="login-form" />

          <div class="q-mt-lg row justify-between">
            <q-btn color="primary" icon="login" label="Enter" type="submit" class="q-mr-xl" />
          </div>
        </q-form>

      </q-tab-panel>

      <q-tab-panel name="register">
        <form @submit.prevent="getEmail">
          <h3>Get Registration Mail</h3>
          <q-input v-model="userEmail.email" type="email" label="Email" class="login-form" />

          <div class="q-mt-lg row justify-between">
            <q-btn color="primary" type="submit" icon="login" label="Get" class="q-mr-xl" />
          </div>
        </form>

      </q-tab-panel>
    </q-tab-panels>
  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { useUserStore } from 'src/stores/user-store';
import { reactive, ref } from 'vue';
import { showNotif } from 'src/components/Notify';


const userStore = useUserStore();
const username = ref('')
const password = ref('')
const user = reactive({
  username,
  password
})
const email = ref('')
const userEmail = reactive({ email })
const tab = ref('login')
const url = import.meta.env.VITE_BACKEND_URL

function submit() {

  axios.post(url + 'users/login', user).then((res) => {
    console.log(res.data);

    userStore.setName(res.data.fullName)
    userStore.setUsername(res.data.username)
    userStore.setToken(res.data.token)
    userStore.setRoles(res.data.roles)
    showNotif('Welcome', 'positive')
    location.reload();

  }).catch(error => {
    showNotif(error.response.data, 'negative')

  })
}



function getEmail() {
  axios.post(url + 'users/send-email', userEmail).then((res) => {
    console.log(res);
  })
}

</script>

