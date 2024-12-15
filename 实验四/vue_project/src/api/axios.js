// src/api/axios.js
import axios from 'axios';
import router from '@/router'
// 创建axios实例
const instance = axios.create({
    baseURL: 'http://localhost:8082/',
    timeout: 10000,
});

// 添加响应拦截器
instance.interceptors.response.use(response => {
    if (response.data.msg === 'NOT_LOGIN'){
        if (router.currentRoute.path !== '/login') {
            router.push("/login");
        }
    
    }
    return response;
}, error => {
    // 处理请求错误
    return Promise.reject(error);
});
export default instance;