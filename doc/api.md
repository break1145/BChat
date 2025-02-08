## 用户相关接口

server: localhost:8864

### 接口总览&说明

| 接口      | URL                                      | 请求方法 |
| --------- | ---------------------------------------- | -------- |
| 用户登录  | `http://{server}/api/auth/login`         | POST     |
| 用户注册  | `http://{server}/api/auth/register`      | POST     |
| 刷新token | `http://{server}/api/auth/refresh-token` | POST     |



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
	"accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczOTAwMzIwMCwiZXhwIjoxNzM5MDAzMjAwfQ.KFkPBC3iriAy1Oh1PmEUTaFHyqFp5ZOLfHnMPZXbuCwZbqgUjMHcnjAOqwhMGu2W",
	"tokenType": "Bearer"
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
    "msg": "ok"
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
    "msg": "ok"
}
```

