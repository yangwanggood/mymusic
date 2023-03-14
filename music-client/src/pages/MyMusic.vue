<template>
    <div class="my-music">
      <div class="album-slide">
        <div class="album-img">
          <img :src="attchImageUrl(avator)">
        </div>
        <ul class="album-info">
          <li>名称：{{username}}</li>
          <li>性别：{{userSex}}</li>
          <li>生日：{{birth}}</li>
          <li>故乡：{{location}}</li>
        </ul>
      </div>
      <div class="album-content">
        <div class="album-title">
        个性签名：{{introduction}}
       </div>
       <div class="songs-body">
        <album-content :songList="collectList">
                <template  slot="title">我的收藏</template>
            </album-content>
            
      </div>
      </div>
      
    </div>
    
</template>
<script>

import AlbumContent from '../components/AlbumContent';
import { mapGetters } from 'vuex';
import { mixin } from '../mixins';
import{getCollectOfUserId,getUserId,songOfSongId} from '../api/index'

export default ({
  mixins:[mixin],
  name:"my-music",
  data() {
    return {
      username:'',
        userSex:'',
        birth:'',
        avator:'', //头像
        introduction:'',//签名
        location:'', //地区
        collection:[], //收藏的歌曲列表
        collectList:[], //收藏的歌单信息列表


    }
  },
  components:{
     AlbumContent
    },
computed:{
     ...mapGetters([
       'listOfSong', //当前歌曲列表
       'userId',    //用户id
       
     ])
   },
   mounted() {
   this.getMsg(this.userId);
   this.getCollUser(this.userId);
   },
   methods: {
    getMsg(userId){   
      getUserId(userId)//主键id查询用户信息
       .then(res=>{
        this.avator=res.avator
        if(res.sex==0){
          this.userSex='女';
        }else if(res.sex==1){
          this.userSex='男';
        }
        this.birth=this.attachBirth(res.birth);
        this.introduction=res.introduction;
        this.location=res.location;
        this.username=res.username
       })
    },
    getCollUser(userId){
      getCollectOfUserId(userId)  //根据用户id 查询我的收藏列表
        .then(res=>{
          this.collection=res;
          for(let item of this.collection){
             this.songOfSongId(item.songId);
          }
        })
    },
    songOfSongId(id){
      songOfSongId(id)
       .then(res=>{
        this.collectList.push(res);
       })
       .catch(err=>{
        console.log(err);
       })
    }
    
   },
})
</script>
<style lang="scss" scoped>
@import '../assets/css/my-music.scss';
</style>
