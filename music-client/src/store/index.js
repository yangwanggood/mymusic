//入口  包含其它的同包下的3个
//用户相关
//里面可以放很多的缓存数据    1：放图片地址
import Vue from 'vue'
import Vuex from 'vuex'
import configure from './configure'
import user from './user'
import song from './song'
Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        configure,
        user,
        song
    }

})

export default store