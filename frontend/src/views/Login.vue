<template>
  <div class="login">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <div>
        <label for="username">Username:</label>
        <input type="text" v-model="username" id="username" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="password" id="password" required />
      </div>
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: ''
    };
  },
  methods: {
    async login() {
      const credentials = {
        username: this.username,
        password: this.password
      };

      try {
        const response = await fetch('http://localhost:8864/api/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(credentials)
        });

        if (response.ok) {
          const data = await response.json();
          localStorage.setItem('accessToken', data.accessToken);  // 保存 token
          this.$router.push({name: 'hello'});  // 登录成功，跳转到 hello 页面
        } else {
          alert('Login failed. Please try again.');
        }
      } catch (error) {
        console.error('Error:', error);
        alert('Login failed. Please try again.');
      }
    }
  }
};
</script>
