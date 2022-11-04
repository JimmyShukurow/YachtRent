<template>
  <div class="column items-center ">
    <h1>Register here</h1>
    <q-input v-model="user.username" type="text" label="Username" class="login-form" />
    <q-input v-model="user.password" type="password" label="Password" class="login-form" />

    <div class="q-mt-lg row justify-between">
      <q-btn color="primary" icon="login" label="Enter" @click="submit" class="q-mr-xl" />
      <q-btn color="primary" icon="app_registration" label="Register" @click="submit" class="q-ml-xl" />
    </div>

  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { useUserStore } from 'src/stores/user-store';
import { reactive, ref } from 'vue';

const userStore = useUserStore();
const username = ref('')
const password = ref('')
const user = reactive({
  username,
  password
})
function submit() {

  axios.post('http://localhost:8080/api/v1/users/login', user).then((res) => {
    console.log(res.data);
    userStore.setName(res.data.fullName)
    userStore.setUsername(res.data.username)
    userStore.setToken(res.data.token)
  })
}

</script>

