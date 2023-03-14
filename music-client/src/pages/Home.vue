<template>
  <div class="home">
    <swiper />
    <div class="section" v-for="(item,index) in songsList" :key="index">
    <div class="section-title">{{item.name}}</div> 
    <content-list :contentList="item.list"></content-list>
    </div>
  
  </div>
</template>

<script>
import ContentList from '../components/ContentList';
import Swiper from "../components/Swiper";
import {getAllSinger,getAllSongList} from '../api/index';
import TheFooter from '../components/TheFooter.vue';
export default {
  name: 'home',
  components: {
    Swiper,
    ContentList,
    TheFooter
    
  },
  
  data () {
    return {
       songsList:[
        {name:"歌单",list:[]},
        {name:"歌手",list:[]}
       ],
    }
  },
  created () {
   this.getSongList();
   this.getSingerList();
  },
  methods: {
   getSongList(){   //歌单
    getAllSongList().then((res)=>{
      this.songsList[0].list=res.slice(0,10);
    }).catch((err)=>{
      console.log(err);
    })
   },
   getSingerList(){ //歌手
    getAllSinger().then((res)=>{
      this.songsList[1].list=res.slice(0,10);
    }).catch((err)=>{
      console.log(err);
    }) 
   }
     
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss'
</style>
