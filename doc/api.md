## 用户相关接口

server: localhost:8864

### 接口总览&说明

| 接口      | URL                                      | 请求方法 |
| --------- | ---------------------------------------- | -------- |
| 用户登录  | `http://{server}/api/auth/login`         | POST     |
| 用户注册  | `http://{server}/api/auth/register`      | POST     |
| 刷新token | `http://{server}/api/auth/refresh-token` | POST     |
| 用户登出  | `http://{server}/api/auth/logout`        | GET      |



#### 1.用户登录

POST `http://{server}/api/auth/login`  

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
	"code": 200,
	"message": "成功",
	"data": {
		"accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJicmVhayIsImlhdCI6MTczOTAyMTQzMCwiZXhwIjoxNzM5NjI2MjMwfQ.USvX4pSyGcq7ADaQcgmna1TfTJboEjzFiBmi4K_3Lo2tTEuPEdsr-zvvRLJpCZmi",
		"tokenType": "Bearer"
	}
}
```

#### 2.用户注册

POST `http://{server}/api/auth/register`  

请求体

```json
{
    "name":"旗鼓相当的对手",
    "username":"break",
    "email":"123@gmail.com",
    "password":"root",
}
```

返回值

```json
{
	"code": 200,
	"message": "成功",
	"data": null
}
```



#### 3.刷新token

POST `http://{server}/api/auth/refresh-token`  

请求体

```json
{
	"accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczOTAwMzIwMCwiZXhwIjoxNzM5MDAzMjAwfQ.KFkPBC3iriAy1Oh1PmEUTaFHyqFp5ZOLfHnMPZXbuCwZbqgUjMHcnjAOqwhMGu2W",
	"tokenType": "Bearer"
}
```

返回值

```json
{
	"code": 200,
	"message": "成功",
	"data": null
}
```

#### 4.用户登出

GET `http://{server}/api/auth/logout`

由于使用jwt token做权限校验，登出时让前端抛弃即可，接口暂时保留
