<!-- 歌单和歌曲的头像 遍历出来 和名字  里面加个播放的标签 -->
<template>
    
    <div class="content-list">
        <ul class="section-content">
            <li class="content-item" v-for="(item,index) in contentList" :key="index">
                <!-- item.name item里面有item.name 和item.title 如果里面有name的就是歌手  没有就是歌曲表 -->
            <div class="kuo" @click="goAlbum(item,item.name)">  
                <img class="item-img" :src="attchImageUrl(item.pic)">
                <div class="mask">
                    <svg class="icon">
                        <use xlink:href="#icon-bofang"></use>
                    </svg>
                </div>
            </div>
           <p class="item-name">{{item.name||item.title}}</p>
        </li>
        </ul>
    </div>
</template>

<script>

import {mixin} from "../mixins/index"
export default({
    name:'content-list',
    mixins:[mixin],
     props:['contentList'],
    data() {
        return {
            
        }
    },
    methods: {
        goAlbum(item,type){
          this.$store.commit('setTempList',item);
          if(type){
            //跳转  把id传过去
            this.$router.push({path:`singer-album/${item.id}`})
          }else{
            this.$router.push({path:`song-list-album/${item.id}`})
          }
        }
    },
})
</script>
<style lang="scss" scoped>
@import '../assets/css/content-list.scss';

</style>
