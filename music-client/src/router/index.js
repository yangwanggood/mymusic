import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import MyMusic from '@/pages/MyMusic'
import Singer from '@/pages/Singer'
import SongList from '@/pages/SongList'
import Search from '@/pages/Search'
import Lyric from '@/pages/Lyric'
import SignUp from '@/pages/SignUp'
import LoginIn from '@/pages/LoginIn'
import Setting from '@/pages/Setting'
import SongListAlbum from '@/pages/SongListAlbum'
import SingerAlbum from '@/pages/SingerAlbum'



Vue.use(Router)

export default new Router({
    //routes 是一个数组 作用 定义hash 地址与组件之间的对应关系 
    routes: [{
            path: '/',
            name: 'home',
            component: Home
                //component 要展示的组件名  
        },
        {
            path: '/my-music',
            name: 'my-music',
            component: MyMusic
        },
        {
            path: '/singer',
            name: 'singer',
            component: Singer
        },
        {
            path: '/song-list',
            name: 'song-list',
            component: SongList
        },
        {
            path: '/search',
            name: 'search',
            component: Search
        },
        {
            path: '/lyric',
            name: 'lyric',
            component: Lyric
        },
        {
            path: '/login-in',
            name: 'login-in',
            component: LoginIn
        },
        {
            path: '/sign-up',
            name: 'sign-up',
            component: SignUp
        },
        {
            path: '/setting',
            name: 'setting',
            component: Setting
        },
        {
            path: '/song-list-album/:id',
            name: 'song-list-album',
            component: SongListAlbum
        },
        {
            path: '/singer-album/:id',
            name: 'singer-album',
            component: SingerAlbum
        },

    ],
    scrollBehavior(to, from, savedPosition) {
        return { x: 0, y: 0 }
    }
})
const VueRouterPush = Router.prototype.push
Router.prototype.push = function push(to) {
    return VueRouterPush.call(this, to).catch(err => err)
}