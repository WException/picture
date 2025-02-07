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
      path: '/admin/user/userManage',
      name: '用户管理',
      component: () => import('../views/user/UserManage.vue'),
    },
    {
      path: '/addPicture',
      name: '上传图片',
      component: () => import('../views/picture/addPicture.vue'),
    },
    {
      path: '/admin/picture/pictureManage',
      name: '图片管理',
      component: () => import('../views/picture/pictureManage.vue'),
    },
    {
      path: '/picture/:id',
      name: '图片详情',
      component: () => import('../views/picture/PictureDetail.vue'),
      props: true,
    },
    {
      path: '/admin/space/spaceManage',
      name: '空间管理',
      component: () => import('../views/space/SpaceManage.vue'),
      props: true,
    },
    {
      path: '/add_space',
      name: '创建空间',
      component: () => import('../views/space/AddSpace.vue'),
      props: true,
    },
    {
      path: '/space/my_space',
      name: '我的空间',
      component: () => import('../views/space/MySpace.vue'),
      props: true,
    },
    {
      path: '/space/:id',
      name: '空间详情',
      component: () => import('../views/space/SpaceDetail.vue'),
      props: true,
    },

  ],
})

export default router
