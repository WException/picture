import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/user/login',
      name: '登录',
      component: () => import('../views/user/UserLogin.vue'),
    },
    {
      path: '/user/register',
      name: '注册',
      component: () => import('../views/user/UserRegister.vue'),
    },
    {
      path: '/admin/userManage',
      name: '用户管理',
      component: () => import('../views/admin/UserManage.vue'),
    },
  ],
})

export default router
