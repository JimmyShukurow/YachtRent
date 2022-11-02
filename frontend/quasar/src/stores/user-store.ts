import { defineStore } from 'pinia';
import { useStorage } from '@vueuse/core';

export const useUserStore = defineStore('user', {
  state: () => ({
    name: useStorage('name', ''),
    username: useStorage('username', ''),
    token: useStorage('token', ''),
  }),
  getters: {
    getName: (state) => state.name,
    getUsername: (state) => state.username,
    getToken: (state) => state.token,
  },
  actions: {
    setName(name: string) {
      this.name = name;
    },
    setUsername(username: string) {
      this.username = username;
    },
    setToken(token: string) {
      this.token = token;
    },
    deleteUser() {
      localStorage.clear();
    },
  },
});
