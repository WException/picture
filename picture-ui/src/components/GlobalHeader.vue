<template>
  <div id="globalHeader">
    <a-row :wrap="false">
      <a-col flex="200px">
        <RouterLink to="/">
          <div class="title-bar">
<!--            <img class="logo" src="../assets/logo.png" alt="logo" />-->
            <div class="title">硬核云图库</div>
          </div>
        </RouterLink>
      </a-col>
      <a-col flex="auto">
        <a-menu
          v-model:selectedKeys="current"
          mode="horizontal"
          :items="items"
          @click="doMenuClick"
        />

      </a-col>
      <a-col flex="120px">
        <div class="user-login-status">
          <div v-if="loginUserStore.loginUser.id">
            <a-dropdown>
              <ASpace>
                <a-avatar :src="loginUserStore.loginUser.userAvatar" />
                {{ loginUserStore.loginUser.userName ?? '无名' }}
              </ASpace>
              <template #overlay>
                <a-menu>
                  <a-menu-item @click="doLogout">
                    <LogoutOutlined />
                    退出登录
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>

          <div v-else>
            <a-button type="primary" href="/user/login">登录</a-button>
          </div>
        </div>

      </a-col>
    </a-row>

  </div>
</template>
<script lang="ts" setup>
import {useLoginUserStore} from "@/stores/useLoginUserStore.ts";
import { h, ref } from 'vue'
import { HomeOutlined, HolderOutlined } from '@ant-design/icons-vue'
import {MenuProps, message} from 'ant-design-vue'
import { useRouter } from "vue-router";
import {userLogoutUsingPost} from "@/api/userController.ts";
const router = useRouter();
const loginUserStore = useLoginUserStore()
// 路由跳转事件
const doMenuClick = ({ key }: { key: string }) => {
  router.push({
    path: key,
  });
};

const current = ref<string[]>(['home'])
// 监听路由变化，更新当前选中菜单
router.afterEach((to, from, next) => {
  current.value = [to.path];
});

const items = ref<MenuProps['items']>([
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: '/admin/userManage',
    icon: () => h(HolderOutlined),
    label: '用户管理',
    title: '用户管理',
  },
  {
    key: '/addPicture',
    icon: () => h(HolderOutlined),
    label: '上传图片',
    title: '上传图片',
  },
  {
    key: '/pictureManage',
    icon: () => h(HolderOutlined),
    label: '图片管理',
    title: '图片管理',
  },
])

// 用户注销
const doLogout = async () => {
  const res = await userLogoutUsingPost()
  console.log(res)
  if (res.data.code === 0) {
    loginUserStore.setLoginUser({
      userName: '未登录',
    })
    message.success('注销成功')
    await router.push('/user/login')
  } else {
    message.error('注销失败，' + res.data.message)
  }
}

</script>

<style scoped>
.title-bar {
  display: flex;
  align-items: center;
}

.title {
  color: black;
  font-size: 18px;
  margin-left: 16px;
}

.logo {
  height: 48px;
}
</style>
