<template>
  <div class="song-list">
   <ul class="song-list-header">
        <li v-for="(item,index) in songStyle" :key="index" @click="handSongStyleView(item.name)"
        :class="{active:item.name==activeName}">
         {{item.name}}
        </li>
   </ul>
   <div>
     <content-list :contentList="data"></content-list>
      <div class="pagination">
        <!-- current-change 当前页 翻页的  background 带颜色 layout:要显示的内容=total(总数) prev(当前页)-->
        <!--total(总数) pager 表示页码列表  prev 表示上一页， next 为下一页 :current-page="currentPage"当前页 :page-size=页面总的大小 :total="albumDatas"总的大小-->
        <el-pagination @current-change="handleCurrentChange" background  layout="total,prev,pager,next"
              :current-page="currentPage" :page-size="pageSize"  :total="albumDatas.length"> 
        </el-pagination>
      </div>
    </div>

      
  </div>
</template>

<script>

import ContentList from '../components/ContentList'
import {getAllSongList,getSongStyleList} from '../api/index'
import { mixin } from '../mixins';
import {songStyle} from '../assets/data/songList'

export default {
  mixins:[mixin],
  name:'song-list',
  components: { ContentList },
  data() {
      return {
          albumDatas:[],  //需要搜索内容的暂存数组
          pageSize:15,  //页面大小 一页15个 
          currentPage:1, //当前页  默认第一页
          songStyle:[],  //风格的存储
          activeName:'全部歌单' //当前显示的风格的导航栏
       }
  },
  computed:{
    data(){
      // (this.currentPage-1)：从第0页开始                       最后一页*15
     return this.albumDatas.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
    }
  },
  mounted(){
      this.getSongList();
      this.songStyle=songStyle;
     
  },
  methods:{
    //查询所有内容
      getSongList(){
        getAllSongList()
               .then(res=>{
                this.currentPage=1;
                this.albumDatas=res;
                })
          
      },
      handleCurrentChange(val){
      },
      
       //根据style显示出该风格内容
       handSongStyleView(name){
        this.activeName=name;
         this.albumDatas=[]
          if(name=='全部歌曲'){
           this.getSongList();
          }else{
            this.getSongStyleList(name);
          }
      },
       //根据style搜索歌单内容
       getSongStyleList(style){
        getSongStyleList(style)
        .then(res=>{
          this.currentPage=1;
          this.albumDatas=res;
        })
      } 
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss'
</style>