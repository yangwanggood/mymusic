<template>
  <div class="singer">
   <ul class="singer-header">
        <li v-for="(item,index) in singerSex" :key="index" @click="handSingerStyleView(item)"
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
import {getAllSinger,getSingerOfSex} from '../api/index'
import { mixin } from '../mixins';
import { singerSex } from '../assets/data/singerList';

export default {
  mixins:[mixin],
  name:'singer',
  components: { ContentList },
  data() {
      return {
          albumDatas:[],  //需要搜索内容的暂存数组
          pageSize:15,  //页面大小 一页15个 
          currentPage:1, //当前页  默认第一页
          singerSex:[],  //性别类型的存储
          activeName:'全部歌手' //当前显示的风格的导航栏
       }
  },
  computed:{
    data(){
      // (this.currentPage-1)：从第0页开始                       最后一页*15
     return this.albumDatas.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
    }
  },
  mounted(){
      this.getSingerList();
      this.singerSex=singerSex;
     
  },
  methods:{
    //查询所有内容
      getSingerList(){
        getAllSinger()
               .then(res=>{
                this.currentPage=1;
                this.albumDatas=res;
                })
          
      },
      handleCurrentChange(val){
        this.currentPage=val;
      },
      
       //根据type显示出该风格内容
       handSingerStyleView(item){
        this.activeName=item.name;
         this.albumDatas=[]
          if(item.name=='全部歌手'){
           this.getSingerList();
          }else{
            this.getSongStyleList(item.type);
          }
      },
       //根据style搜索歌单内容
       getSongStyleList(sex){
        getSingerOfSex(sex)
        .then(res=>{
          this.currentPage=1;
          this.albumDatas=res;
        })
      } 
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css//singer.scss'
</style>