<template>
  <div class="hello">
    <h1>Welcome to the Hello Page</h1>
    <p>You are successfully logged in!</p>
  </div>
</template>

<script>
import {fetchWithToken} from '../api.js';  // 引入封装好的请求函数

export default {
  created() {
    // 检查用户是否已登录
    if (!localStorage.getItem('accessToken')) {
      this.$router.push({name: 'login'});  // 如果没有 token，则跳转到登录页面
    } else {
      this.getUserData();
    }
  },
  methods: {
    async getUserData() {
      try {
        const data = await fetchWithToken('http://localhost:8864/api/user/data');
        console.log(data);  // 打印获取到的数据
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    }
  }
};
</script>
