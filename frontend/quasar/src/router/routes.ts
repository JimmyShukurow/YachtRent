import { RouteRecordRaw } from 'vue-router';
import { useUserStore } from 'src/stores/user-store';
import { createPinia } from 'pinia';
import { createApp } from 'vue';
import App from '/src/App.vue';

const pinia = createPinia();
const app = createApp(App);
app.use(pinia);

const user = useUserStore();
console.log(user.getToken);

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('src/layouts/UserLayout.vue'),
    children: [{ path: '', component: () => import('pages/IndexPage.vue') }],
  },
  {
    path: '/admin',
    component: () =>
      user.getToken == ''
        ? import('src/layouts/LoginLayout.vue')
        : import('src/layouts/AdminLayout.vue'),
    children: [
      {
        path: '',
        component: () =>
          user.getToken == ''
            ? import('pages/admin/user/LoginPage.vue')
            : import('pages/admin/AdminHomePage.vue'),
      },
      {
        path: 'yachts',
        component: () => import('pages/admin/yachts/AllYachts.vue'),
      },
      {
        path: 'store-yacht',
        component: () => import('pages/admin/yachts/YachtForm.vue'),
      },
      {
        path: 'login',
        component: () => import('pages/admin/user/LoginPage.vue'),
      },
    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
];

export default routes;
