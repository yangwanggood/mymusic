import axios from "axios";
//访问框架
axios.defaults.timeout = 5000; //超时时间是5秒
axios.defaults.withCredentials = true; //允许跨域
//Content-Type 响应头 
//如果后端接收的是（表单）字符串类型，post 的 headers （默认）设置为  { ‘content-type’: ’application/x-www-form-urlencoded’ }，传输给后端的数据就形如 ‘name=edward&age=25’
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'; //后端要求接收的格式
//基础url IDEA的port中的url 访问地址
axios.defaults.baseURL = "http://localhost:8888";

// 响应拦截器  （判断用户是否登入 返回的是成功码还是错误码
axios.interceptors.response.use(
    response => {
        //如果repsone里面的status是200 则是登入成功
        if (response.status == 200) {
            //Promise 异步  
            return Promise.resolve(response); // 让response 返回去 在执行
        } else {
            return Promise.reject(response); //不往下执行了
        }
    },
    //其它错误
    error => {
        //如果错误页有值的话  就可以判断一下
        if (error.response.status) {
            switch (error.response.status) {
                case 401: //未登录
                    router.replace({
                        path: '/', //根目录
                        query: { //存一下
                            redirect: router.currentRoute.fullPath
                        }
                    });
                    break;
                case 404: // 没找到
                    break;
            }
            return Promise.reject(error.response) //返回一个错误信息
        }

    }
);

/**
 * 封装get方法
 */
//params 参数 这里为空
export function get(url, params = {}) {
    return new Promise((resolve, reject) => {
        axios.get(url, { params: params }) //axios.get 方法是 传入url绝对地址 params 参数
            .then(response => {
                resolve(response.data);
            })
            .catch(err => {
                reject(err);
            })
    });
}


/**
 * 封装post方法
 */
//params 参数 这里为空
export function post(url, data = {}) {
    return new Promise((resolve, reject) => {
        axios.post(url, data) //axios.get 方法是 传入url绝对地址 params 参数
            .then(response => {
                resolve(response.data);
            })
            .catch(err => {
                reject(err);
            })
    });
}