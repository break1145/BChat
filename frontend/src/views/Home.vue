<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { authService } from '../services/api'

const router = useRouter()
const message = ref('')

const logout = async () => {
  try {
    await authService.logout()
    localStorage.removeItem('token')
    router.push('/login')
  } catch (error) {
    console.error('Logout error:', error)
  }
}

onMounted(async () => {
  try {
    const response = await authService.hello()
    message.value = response.data
  } catch (error) {
    console.error('Error fetching hello message:', error)
  }
})
</script>

<template>
  <div class="home-container">
    <h1>欢迎来到主页</h1>
    <div v-if="message" class="message">
      {{ message }}
    </div>
    <button @click="logout" class="logout-btn">退出登录</button>
  </div>
</template>

<style scoped>
.home-container {
  padding: 20px;
  text-align: center;
}

.message {
  margin: 20px 0;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.logout-btn {
  padding: 8px 16px;
  background-color: #ff4d4f;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.logout-btn:hover {
  background-color: #ff7875;
}
</style> 