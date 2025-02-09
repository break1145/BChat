import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8864/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
api.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      // 修改设置 header 的方式
      config.headers = {
        ...config.headers,
        'Authorization': `Bearer ${token}`,
        'Access-Control-Allow-Headers': 'Authorization',
        'Access-Control-Allow-Methods': 'GET,POST,OPTIONS'
      }
      console.log('Sending request with headers:', JSON.stringify(config.headers))
    }
    // 对于 OPTIONS 请求特殊处理
    if (config.method === 'options') {
      config.headers = {
        ...config.headers,
        'Access-Control-Request-Headers': 'authorization'
      }
    }
    return config
  },
  error => {
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => response,
  error => {
    console.error('Response error:', error)
    if (error.response) {
      console.error('Error status:', error.response.status)
      console.error('Error data:', error.response.data)
    }
    return Promise.reject(error)
  }
)

export const authService = {
  login: async (username, password) => {
    try {
      const response = await api.post('/auth/login', { username, password })
      if (response.data.code === 200) {
        const { accessToken } = response.data.data
        localStorage.setItem('token', accessToken)
        console.log('Token saved:', accessToken)
        return response.data
      }
      throw new Error(response.data.message)
    } catch (error) {
      console.error('Login error:', error)
      throw error
    }
  },
  register: async (userData) => {
    const response = await api.post('/auth/register', userData)
    if (response.data.code === 200) {
      return response.data
    }
    throw new Error(response.data.message)
  },
  hello: async () => {
    try {
      const token = localStorage.getItem('token')
      // 直接在请求配置中设置完整的配置
      const response = await api.get('/hello', {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      return response.data
    } catch (error) {
      console.error('Hello API error:', error)
      throw error
    }
  },
  logout: async () => {
    try {
      const token = localStorage.getItem('token')
      const response = await api.post('/auth/logout', null, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      })
      return response.data
    } catch (error) {
      console.error('Logout API error:', error)
      throw error
    }
  }
}

export default api 