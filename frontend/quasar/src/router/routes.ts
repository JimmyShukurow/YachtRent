import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('src/layouts/UserLayout.vue'),
    children: [{ path: '', component: () => import('pages/IndexPage.vue') }],
  },
  {
    path: '/admin',
    component: () => import('src/layouts/AdminLayout.vue'),
    children: [
      { path: '', component: () => import('pages/admin/AdminHomePage.vue') },
      {
        path: 'yachts',
        component: () => import('pages/admin/yachts/AllYachts.vue'),
      },
      {
        path: 'store-yacht',
        component: () => import('pages/admin/yachts/YachtForm.vue'),
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
