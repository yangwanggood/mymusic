//放基础的

//用户相关
//里面可以放很多的缓存数据    1：放图片地址


const configure = ({
    state: {
        HOST: 'http://127.0.0.1:8888',
        activeName: '', //当前需要访问的菜单名
        showAside: false, //theAside表  是否显示视图中播放的歌曲
        loginIn: false, //是否已经登录 默认没有 
        isActive: false, //是否显示收藏 true亮


    },
    //取值
    getters: {
        activeName: state => {
            let activeName = state.activeName
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!activeName) {
                activeName = JSON.parse(window.sessionStorage.getItem('activeName'))
            }
            return activeName
        },

        showAside: state => {
            let showAside = state.showAside
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!showAside) {
                showAside = JSON.parse(window.sessionStorage.getItem('showAside'))
            }
            return showAside
        },
        loginIn: state => {
            let loginIn = state.loginIn
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!loginIn) {
                loginIn = JSON.parse(window.sessionStorage.getItem('loginIn'))
            }
            return loginIn
        },
        isActive: state => {
            let isActive = state.isActive
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!isActive) {
                isActive = JSON.parse(window.sessionStorage.getItem('isActive'))
            }
            return isActive
        },


    },
    //设置值
    mutations: {
        setShowAside: (state, showAside) => {
            state.showAside = showAside
                //放到浏览器的
            window.sessionStorage.setItem('showAside', JSON.stringify(showAside))
        },
        setLoginIn: (state, loginIn) => {
            state.loginIn = loginIn
                //放到浏览器的
            window.sessionStorage.setItem('loginIn', JSON.stringify(loginIn))
        },
        setActiveName: (state, activeName) => {
            state.activeName = activeName
                //放到浏览器的
            window.sessionStorage.setItem('activeName', JSON.stringify(activeName))
        },
        setIsActive: (state, isActive) => {
            state.isActive = isActive
                //放到浏览器的
            window.sessionStorage.setItem('isActive', JSON.stringify(isActive))
        },
    }
})

export default configure