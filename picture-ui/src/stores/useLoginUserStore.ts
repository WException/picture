import { defineStore } from "pinia";
import { ref } from "vue";
import {getLoginUserUsingGet} from "@/api/userController.ts";

export const useLoginUserStore = defineStore("loginUser", () => {
  const loginUser = ref<API.LoginUserVO>({
    userName: '未登录',
  })


  async function fetchLoginUser() {
    const res = await getLoginUserUsingGet()
    if (res.data.code === 0 && res.data.data) {
      loginUser.value = res.data.data
    }
  }


  function setLoginUser(newLoginUser: any) {
    loginUser.value = newLoginUser;
  }

  return { loginUser, setLoginUser, fetchLoginUser };
},
{
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'loginUser',
        storage: localStorage, //可以选择对应的存储形式（localStorage或者sessionStorage）
      },
    ],
  },
});
