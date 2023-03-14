//用户相关
//里面可以放很多的缓存数据    1：放图片地址
//放基础的

//用户相关
//里面可以放很多的缓存数据    1：放图片地址


const user = ({
    state: {
        userId: '', //用户id
        username: '', //用户账号
        avator: '', //用户头像


    },
    //取值
    getters: {
        userId: state => {
            let userId = state.userId
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!userId.length) {
                userId = JSON.parse(window.sessionStorage.getItem('userId'))
            }
            return userId
        },

        username: state => {
            let username = state.username
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!username.length) {
                username = JSON.parse(window.sessionStorage.getItem('username'))
            }
            return username
        },

        avator: state => {
            let avator = state.avator
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!avator) {
                avator = JSON.parse(window.sessionStorage.getItem('avator') || null)
            }
            return avator
        },


    },
    //设置值
    mutations: {
        setUserId: (state, userId) => {
            state.userId = userId
                //放到浏览器的
            window.sessionStorage.setItem('userId', JSON.stringify(userId))
        },
        setUsername: (state, username) => {
            state.username = username
                //放到浏览器的
            window.sessionStorage.setItem('username', JSON.stringify(username))
        },
        setAvator: (state, avator) => {
            state.avator = avator
                //放到浏览器的
            window.sessionStorage.setItem('avator', JSON.stringify(avator))
        },

    }
})

export default user