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
    {
      path: '/addPicture',
      name: '上传图片',
      component: () => import('../views/picture/addPicture.vue'),
    },
    {
      path: '/pictureManage',
      name: '图片管理',
      component: () => import('../views/picture/pictureManage.vue'),
    },
    {
      path: '/picture/:id',
      name: '图片详情',
      component: () => import('../views/picture/PictureDetail.vue'),
      props: true,
    }

  ],
})

export default router
