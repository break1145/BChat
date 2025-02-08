## 登录
POST `http://localhost:8864/api/auth/login`  
请求体
```json
{
    "username":"admin",
    "password":"admin"
}
```
返回值
```json
{
	"accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczOTAwMzIwMCwiZXhwIjoxNzM5MDAzMjAwfQ.KFkPBC3iriAy1Oh1PmEUTaFHyqFp5ZOLfHnMPZXbuCwZbqgUjMHcnjAOqwhMGu2W",
	"tokenType": "Bearer"
}
```