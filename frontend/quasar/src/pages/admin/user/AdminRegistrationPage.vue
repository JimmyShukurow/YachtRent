<template>
  <div class="column items-center ">
    <div v-if="!expired">
      <h1>Register here</h1>
      <q-form @submit.prevent="submit">
        <q-input :rules="[(val) => (val && val.length > 0) || 'Write something']" v-model="user.firstName" type="text"
          label="First Name" class="login-q-form" />
        <q-input :rules="[(val) => (val && val.length > 0) || 'Write something']" v-model="user.lastName" type="text"
          label="Last Name" class="login-form" />
        <q-input :rules="[(val) => validateEmail(val) || 'Not Valid']" v-model="user.username" type="text"
          label="Username" class="login-form" />
        <q-input :rules="[(val) => validatePassword(val) || 'Not Valid']" v-model="user.password" type="password"
          label="Password" class="login-form" />
        <div class="password-criteria q-pa-sm">
          <div class="text-subtitle2 q-mb-sm">Password Criteria:</div>
          <div>
            <q-icon :name="validPassword.length ? 'check_circle' : 'cancel'"
              :color="validPassword.length ? 'positive' : 'negative'"></q-icon>
            Must be at least 12 characters long.
          </div>
          <div>
            <q-icon :name="validPassword.capital ? 'check_circle' : 'cancel'"
              :color="validPassword.capital ? 'positive' : 'negative'"></q-icon>
            Must contain at least one capital letter.
          </div>
          <div>
            <q-icon :name="validPassword.number ? 'check_circle' : 'cancel'"
              :color="validPassword.number ? 'positive' : 'negative'"></q-icon>
            Must contain at least one number.
          </div>
          <div>
            <q-icon :name="validPassword.symbol ? 'check_circle' : 'cancel'"
              :color="validPassword.symbol ? 'positive' : 'negative'"></q-icon>
            Must contain at least one symbol.
          </div>
        </div>
        <q-input v-model="confirm" type="password" label="Confirm Password" class="login-form"
          :disable="!validatePassword(user.password)" :rules="[
            (val) =>
              (val && val === user.password) ||
              'Must match password.',
          ]" />

        <div class="q-mt-lg row justify-between">
          <q-btn color="primary" icon="app_registration" label="Register" type="submit" class="q-ml-xl" />
        </div>
      </q-form>


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
import { useRoute, useRouter } from 'vue-router';
import  {showNotif}  from 'src/components/Notify';
const userStore = useUserStore();

const firstName = ref('')
const lastName = ref('')
const username = ref('')
const password = ref('')
const confirm = ref('')
const route = useRoute()
const router = useRouter()
const expired = ref(false)
const url = import.meta.env.VITE_BACKEND_URL
const user = reactive({
  firstName,
  lastName,
  username,
  password
})



onBeforeMount(() => {

  let hash = route.params.hash as string;

  axios.post(url + 'users/check-link/' + encodeURIComponent(hash)).then(res => {
    console.log(res.status);

  }).catch(() => {
    expired.value = true
  });


})
function validateEmail(email: string): boolean {
  return /[a-z0-9]+@[a-z]+\.[a-z]{2,3}/.test(email);
}

type PasswordValidator = {
  length: boolean;
  capital: boolean;
  number: boolean;
  symbol: boolean;
};

const validPassword = reactive(<PasswordValidator>{
  length: false,
  capital: false,
  number: false,
  symbol: false,
});

function validatePassword(password: string): boolean {
  // Test length
  validPassword.length = user.password.length >= 12;

  // Test capital
  validPassword.capital = /^(?=.*[A-Z]).*$/.test(password);

  // Test number
  validPassword.number = /^(?=.*[0-9]).*$/.test(password);

  // Test symbol
  validPassword.symbol = /^(?=.*[!@#$%^&*-_+=]).*$/.test(password);

  return (
    validPassword.length &&
    validPassword.capital &&
    validPassword.number &&
    validPassword.symbol
  );
}

function submit() {

  axios.post(url + 'client/register', user).then((res) => {
    userStore.setName(res.data.fullName)
    userStore.setUsername(res.data.username)
    userStore.setToken(res.data.token)

    showNotif('Success', 'positive');
    router.push('/admin')

  }).catch((error)=>{
    if (error.response) {
            showNotif(error.response.data.message, 'negative');
        }
  })
}

</script>

