<!-- 点击图片进入的 歌手界面 -->
<template>
    <div class="singer-album">
        <div class="album-slide">
            <div class="singer-img">
                <img :src="attchImageUrl(singer.pic)">
            </div>
            <ul class="info">
                <li v-if="singer.sex==0||singer.sex==1">{{attchSex(singer.sex)}}</li>
               <li>生日：{{attachBirth(singer.birth)}}</li>
               <li>故乡:{{singer.introduction}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="intro">
                <h3>{{singer.name}}</h3>
                <span>{{singer.introduction}}</span>
            </div>
            <div class="content">
              <album-content :songList="listOfSong">
                <template  slot="title">歌单</template>
            </album-content>
            <comment :playId="singerId" :type="0"></comment>
           </div>
        </div>
       
    </div>
</template>
<script>

import AlbumContent from '../components/AlbumContent';
import { mapGetters } from 'vuex';
import { mixin } from '../mixins';
import{songOfSingerId} from '../api/index'


export default {
mixins:[mixin],
name:'singer-album',

data() {
    return {
        singer:{}, //当前歌手信息
        singerId:'', //接收主上界面传过来的 id content-list界面 
            
    }
},
components:{
     AlbumContent
    },
computed:{
     ...mapGetters([
       'listOfSong', //当前歌曲列表
       'tempList',    //前图 传入store里面的歌手信息
       'loginIn',  //是否登录
       'userId'    //用户id
     ])
   },
   created(){
   // this.songLists=listOfSong, //歌曲列表信息
   this.singerId=this.$route.params.id;  //id
   this.singer=this.tempList,
   this.getSingerOfId()
    
   },
   methods:{
   // 据歌手id查询歌曲
    getSingerOfId(){
        songOfSingerId(this.singerId)
         .then(res=>{
            this.$store.commit('setListOfSong',res)
         }).catch(err=>{
            console.log(err);
         })
    },

    attchSex(value){
        if(value==0){
            return '女'
        }else if(value==1){
            return '男'
        }else{
            return ''
        }
    },
   }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/singer-album.scss';
</style>
