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
        <h1>Login here</h1>
        <q-input v-model="user.username" type="text" label="Username" class="login-form" />
        <q-input v-model="user.password" type="password" label="Password" class="login-form" />

        <div class="q-mt-lg row justify-between">
          <q-btn color="primary" icon="login" label="Enter" @click="submit" class="q-mr-xl" />
        </div>
      </q-tab-panel>

      <q-tab-panel name="register">
        <h3>Get Registration Mail</h3>
        <q-input v-model="userEmail.email" type="email" label="Email" class="login-form" />

        <div class="q-mt-lg row justify-between">
          <q-btn color="primary" icon="login" label="Get" @click="getEmial" class="q-mr-xl" />
        </div>
      </q-tab-panel>
    </q-tab-panels>




  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { data } from 'browserslist';
import { useUserStore } from 'src/stores/user-store';
import { reactive, ref } from 'vue';

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
const myIp = ref('')

function submit() {

  axios.post(url + 'users/login', user).then((res) => {
    userStore.setName(res.data.fullName)
    userStore.setUsername(res.data.username)
    userStore.setToken(res.data.token)
  })
}



function getEmial() {
  axios.get('https://www.cloudflare.com/cdn-cgi/trace').then(res => res.json({ data }))
  axios.post(url + 'users/send-email', userEmail, { headers: { myIP: myIp } }).then((res) => {
    console.log(res);

  })
}

</script>

