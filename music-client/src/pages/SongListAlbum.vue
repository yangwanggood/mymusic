<!-- 点击图片进入 的歌单界面 -->
<template>
    <div class="song-list-album">
        <div class="album-slide">
            <div class="album-img">
              <img :src="attchImageUrl(tempList.pic)">
            </div>
            <div class="album-info">
                <h2>简介：</h2>
                <span>{{tempList.introduction}}</span>
            </div>
        </div>
        <div class="album-content">
            <div class="album-title">
                <p>{{tempList.title}}</p>
            </div>
         <div class="album-score">
          <div>
            <h3>歌单评分</h3>
            <div>
              <!--  disabled:让el-rate的五角星标签v-model="average"用来控制 5角心 不能选择-->
              <el-rate v-model="average" disabled></el-rate>
            </div>
          </div>
          <span>{{average*2}}</span>
          <div>
            <h3>评价：</h3>
            <div @click="setRank">
              <!--allow-half可以评价半分  -->
              <el-rate v-model="rank" allow-half show-text></el-rate>
            </div>
          </div>
          </div>
            <div class="songs-body">
              <album-content :songList="listOfSong">
                <template  slot="title">歌单</template>
            </album-content>
            <comment :playId="songListId" :type="1"></comment>
           </div>
        </div>
    </div>
</template>
<script>
import AlbumContent from '../components/AlbumContent';
import { mapGetters } from 'vuex';
import { mixin } from '../mixins';
import{listSongIdtail,songOfSongId,setRank,getRankSongListId} from '../api/index'
import Comment from '../components/Comment.vue';

export default {
    name:'song-list-album',
    mixins:[mixin],
    components:{AlbumContent,Comment},
     data() {
        return {
            songLists:[],  //存放歌曲需求展示列表
            songListId:'', //接收主上界面传过来的 id content-list界面 
            rank:0,  //提交评价
            average:0//平均数
          

        }
     },
   computed:{
     ...mapGetters([
       'listOfSong', //当前歌曲列表
       'tempList',    //前图 传入store里面的歌曲或歌手信息
       'loginIn',  //是否登录
       'userId'    //用户id
     ])
   },
   created(){
   // this.songLists=listOfSong, //歌曲列表信息
    this.songListId=this.$route.params.id;  //id
    this.getSongId();
    this.getRank(this.songListId)
   },
   methods:{
    getSongId(){
        //根据歌曲id查询歌曲对象
        listSongIdtail(this.songListId)
                     .then(res=>{
                       for(let item of res){
                        this.songOfSongId(item.songId); 
                       }
                       this.$store.commit('setListOfSong',this.songLists)
                     }).catch(err=>{
                console.log(err);
              })
              
    },
     //根据歌曲id获取歌曲信息
     getSongList(id){
     songOfSongId(id)
              .then(res=>{
                this.songLists.push(res)
              }).catch(err=>{
                console.log(err);
              })
       },
       getRank(id){
        getRankSongListId(id)
        .then(res=>{
                this.average=res/2;
              }).catch(err=>{
                console.log(err);
              })
       },
       setRank(){
        if(this.loginIn){
          let params=new URLSearchParams();
          params.append('songListId',this.songListId);
          params.append('consumerId',this.userId);
          params.append('score',this.rank*2);
          setRank(params)
          .then(res=>{
                if(res.code==1){
                  this.notify('评分成功','success')
                  this.getRank(this.songListId)
                }else{
                  this.notify('只能评价一次','error')
                }
              }).catch(err=>{
                this.notify('评分失败','error')
              })
        }else{
          this.rank=null;
          //warning警告
          this.notify('请先登录','warning')
        }
       }

   }
}
</script>


<style lang="scss" scoped>
@import '../assets/css/song-list-album.scss';
</style>
