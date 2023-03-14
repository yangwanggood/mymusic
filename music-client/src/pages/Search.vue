<template>
    <div class="search">
        <nav class="searchList-nav" ref="change">
            <span :class="{isActive: toggle=='Songs'}" @click="handleChangeView('Songs')">歌曲</span>
            <span :class="{isActive: toggle=='SongList'}" @click="handleChangeView('SongList')">歌单</span>
            
        </nav>
        <component :is="currentView"></component>
    </div>
</template>

<script>

import searchSongs from '../components/search/SearchSongs'
import searchSongList from '../components/search/SearchSongList'
export default {
    name:'search',
    components:{
        searchSongs,
        searchSongList
    },
    data() {
        return {
            //toggle () 方法用于绑定两个或多个事件处理器函数，以响应被选元素的轮流的 click 事件
            toggle:'Songs',
            currentView: 'searchSongs'  //默认视图
            
        }
    },
    methods: {
        handleChangeView(component){
            this.currentView='search'+component;
            this.toggle=component;
        }
    },
}
</script>
<style lang="scss" scoped>
@import '../assets/css/search.scss';
</style>
