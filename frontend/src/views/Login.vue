<template>
  <div class="login-container">
    <h2>{{ isLogin ? '登录' : '注册' }}</h2>
    <form @submit.prevent="handleSubmit">
      <div v-if="!isLogin" class="form-group">
        <input v-model="formData.name" type="text" placeholder="姓名" required>
      </div>
      <div class="form-group">
        <input v-model="formData.username" type="text" placeholder="用户名" required>
      </div>
      <div v-if="!isLogin" class="form-group">
        <input v-model="formData.email" type="email" placeholder="邮箱" required>
      </div>
      <div class="form-group">
        <input v-model="formData.password" type="password" placeholder="密码" required>
      </div>
      <button type="submit">{{ isLogin ? '登录' : '注册' }}</button>
    </form>
    <p @click="isLogin = !isLogin" class="toggle-form">
      {{ isLogin ? '没有账号？点击注册' : '已有账号？点击登录' }}
    </p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { authService } from '../services/api'

const router = useRouter()
const isLogin = ref(true)
const formData = ref({
  username: '',
  password: '',
  name: '',
  email: ''
})

const handleSubmit = async () => {
  try {
    if (isLogin.value) {
      const response = await authService.login(formData.value.username, formData.value.password)
      if (response.code === 200) {
        router.push('/')
      }
    } else {
      const response = await authService.register(formData.value)
      if (response.code === 200) {
        isLogin.value = true
      }
    }
  } catch (error) {
    console.error('Error:', error)
  }
}
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

.form-group {
  margin-bottom: 15px;
}

input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.toggle-form {
  text-align: center;
  color: #666;
  cursor: pointer;
  margin-top: 15px;
}
</style>
