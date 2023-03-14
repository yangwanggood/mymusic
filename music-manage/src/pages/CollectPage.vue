<template>
  <!-- table封装在main css中 -->
  <div class="table"> 
      <div class="crumbs">
          <i class="el-icon-tickets"></i>收藏歌单管理
      </div>
      <div class="container">
          <div class="handle-box">
              <!--批量删除 -->
              <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
              <!-- 搜索框 -->
          </div>
      </div>

      <!-- border style="width:100%" 一个边框   data数据是  -->
      <el-table size="mini" ref=" " border-width style="width:100%" height="680px" :data="tableDate" @selection-change="handleSelectionChange">
          <!-- type="selection" 多选框 批量删除-->
         
              <el-table-column prop="name" label="歌手-歌名" :cell-style="{ textAlign: 'center' }" >
              </el-table-column>
              <el-table-column label="操作" width="150"  :cell-style="{ textAlign: 'center' }">
                  <template slot-scope="scope">
                     <!--type="danger" :红色按钮  -->
                     <el-button size="mini" type="danger"  @click="handleDelete(scope.row)">删除</el-button>
                  </template>
              </el-table-column>
      </el-table>

      <el-dialog title="删除歌曲" :visible.sync="delVisible" width="400px" center>
       <div :cell-style="{ textAlign: 'center' }"> 删除不可恢复,是否确定删除?</div>       
      <span slot="footer">
          <el-button size="mini" @click="delVisible=false">取消</el-button>
          <el-button size="mini" @click="deleteRow">确定</el-button>
      </span>

      </el-dialog>

  </div>
</template>
<script>
import {songOfSongId,getCollectOfUserId,delCollectOfUserIdOfSongId} from '../api/index';
import { mixin } from '../mixins/index';
export default {
  mixins:[mixin],
  props:['id'], //接收   this.$router.push({path:'/collect',query:{id}});
  data() {
      return {
          delVisible:false,           //删除框是否显示
          tableDate:[],                //查询出来的歌手放在这 
          tempDate:[],                 //查询出来的歌曲放在这 
          select_word:'',              //搜索框输入的文字
          idx:-1,                      //当前的选择项
          multipleSelection:[],         //哪些项打勾了
          songListId:""                 //歌单id

      }
  },
 
 
  watch:{
    
      // select_word当发生变化时 会触发function
      //搜索框里面的内容发生变化的时候 ，搜索结果table内容跟着变
      select_word:function(){
         if(this.select_word==''){
          this.tableDate=this.tempDate;
         }else{
          this.tableDate=[];
          for(let item of this.tempDate){
              if(item.name.includes(this.select_word)){
                  this.tableDate.push(item)
                  //push到tableDate
              }
          }
         }
      }
  },
  created(){
      this.getDate();
  },
  methods:{
      //翻页时能 获取当前页
      
      //用户查询所有收藏的歌曲
      getDate(){
          this.tempDate=[];
          this.tableDate=[];          //查询前先清空一下 
          getCollectOfUserId(this.$route.query.id)
          .then(res=>{
              for(let item of res){
                  this.getSong(item.songId)
                 
              }
             
          })
         
      },
       //根据歌曲id查询歌曲对象，放到temData和tableDate中
       getSong(id){
        
        songOfSongId(id)
              .then(res=>{
                  this.tempDate.push(res);
                  this.tableDate.push(res)
              })    
              .catch(err=>{
                 console.log(err);
              });
          
          },

    
      //删除功能
      deleteRow(){
        
        delCollectOfUserIdOfSongId(this.$route.query.id,this.idx.id)
          .then(res=> {
              if(res){
                  this.getDate();
                  this.notify("删除成功","success");
              }else{
                  this.notify("删除失败","error");
              }
          })
          .catch(err=>{
              console.log(err);
          });
          this.delVisible=false;
      },
  }
}
</script>
<style scoped>
.handle-box{
  margin-bottom: 20px;
}
