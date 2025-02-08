import Vue from 'vue';
import Router from 'vue-router';
import Login from '../views/Login.vue';
import Hello from '../views/Hello.vue';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        {
            path: '/hello',
            name: 'hello',
            component: Hello
        },
        {
            path: '/',
            redirect: '/login' // 默认重定向到登录页
        }
    ]
});
