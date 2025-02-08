// api.js
const API_URL = 'http://localhost:8864/api';  // 修改为你的后端 API 地址

// 获取存储在 localStorage 中的 token
function getAuthToken() {
    return localStorage.getItem('accessToken');
}

// 创建带 token 的请求函数
async function fetchWithToken(url, options = {}) {
    const token = getAuthToken();  // 获取 token

    if (token) {
        // 在请求头中添加 Authorization
        options.headers = {
            ...options.headers,
            'Authorization': `Bearer ${token}`
        };
    }

    const response = await fetch(url, options);
    return response.json();
}

export { fetchWithToken };
