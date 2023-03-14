//里面可以放很多的缓存数据    1：放图片地址
import Vue from "vue";
import Vuex from "Vuex";
//Vue.use(Vuex) 放在这是因为Vuex会在import 之前运行 import store from './store/index' 要在vuex之后运行
Vue.use(Vuex)

const store = new Vuex.Store({
    //state 用于存储数据
    state: {
        HOST: 'http://127.0.0.1:8888',
        isPlay: false, //是否播放中
        url: '', //歌曲地址
        id: '' //歌曲id

    },
    //取值
    getters: {
        isPlay: state => state.isPlay,
        url: state => state.url,
        id: state => state.id
    },
    //设置值
    //可以直接操作state里面的对象的值
    mutations: {
        setIsPlay: (state, isPlay) => { state.isPlay = isPlay },
        setUrl: (state, url) => { state.url = url },
        setId: (state, id) => { state.id = id }
    }
})

export default store