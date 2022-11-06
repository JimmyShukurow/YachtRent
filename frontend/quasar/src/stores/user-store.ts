import { defineStore } from 'pinia';
import { useStorage } from '@vueuse/core';

export const useUserStore = defineStore('user', {
  state: () => ({
    name: useStorage('name', ''),
    username: useStorage('username', ''),
    token: useStorage('token', ''),
    roles: useStorage('roles', []),
  }),
  getters: {
    getName: (state) => state.name,
    getUsername: (state) => state.username,
    getToken: (state) => state.token,
    getRoles: (state) => state.roles,
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
    setRoles(roles: []) {
      this.roles = roles;
    },
    deleteUser() {
      localStorage.clear();
    },
  },
});
