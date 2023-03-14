<template>
    <!-- table封装在main css中 -->
    <div class="table"> 
        <div class="crumbs">
            <i class="el-icon-tickets"></i>歌单歌曲信息
        </div>
        <div class="container">
            <div class="handle-box">
                <!--批量删除 -->
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <!-- 搜索框 -->
                <el-input v-model="select_word" size="mini" placeholder="请输入歌手名" class="handle-input"></el-input>
               
               <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加歌曲</el-button>
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
                       <el-button size="mini" type="danger"  @click="handleDelete(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
        </el-table>

                                         <!-- el-dialog 弹出一个框  visible:隐藏的 将 title里面的值隐藏  框的宽度400-->
        <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
                                         <!-- el-form:需要提交的表单  label-width:左 右显示 如果不定义 标签会自己一行-->
        <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
                                         <!-- size="mini"字体最小的方式体现 -->
            <el-form-item prop="singerName" label="歌手名字" size="mini"> 
                 <el-input v-model="registerForm.singerName" placeholder="歌手名字"></el-input>
            </el-form-item>
            <el-form-item prop="songName" label="歌曲名字" size="mini"> 
                 <el-input v-model="registerForm.songName" placeholder="歌曲名字"></el-input>
            </el-form-item>
            
        </el-form>
        <span slot="footer">
            <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
            <el-button size="mini" @click="getSongId">确定</el-button>
        </span>

        </el-dialog>
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
import { listSongIdtail,listSongAdd,delListSong,songOfSongId,songOfSongName} from '../api/index';
import { mixin } from '../mixins/index';
export default {
    mixins:[mixin],
    data() {
        return {
            centerDialogVisible:false,  //添加框这个框是否显示 默认false 不显示
            delVisible:false,           //删除框是否显示
            registerForm:{              //添加框
                singerName:'',          //歌手名字
                songName:''             //歌曲名

            },
          
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
        this.songListId=this.$route.query.id;//6
        this.getDate();
    },
    methods:{
        //翻页时能 获取当前页
        
        //查询所有歌手
        getDate(){
            
            this.tempDate=[];
            this.tableDate=[];          //查询前先清空一下 
            listSongIdtail(this.songListId)
            .then(res=>{
                console.log(res);
                debugger
                for(let item of res){
                    this.getSong(item.songId)
                }
               
            })
           
        },
         //根据歌曲id查询歌曲对象，放到temData和tableDate中
         getSong(id){
            debugger
            songOfSongId(id)
                .then(res=>{
                    this.tempDate.push(res);
                    this.tableDate.push(res)
                })    
                .catch(err=>{
                   console.log(err);
                });
            
            },

        //添加歌曲前，获取到歌曲id
        getSongId(){
            let _this=this;
            var songOfName=_this.registerForm.singerName+"-"+_this.registerForm.songName
            songOfSongName(songOfName).then(
                res=>{
                    this.addSong(res[0].id)
                }
            )
        },
        //添加歌曲
        addSong(songId){
            let _this=this;
            let params=new URLSearchParams //往后台传的参数
            params.append('songId',songId);
            params.append('songListId',this.songListId);
            listSongAdd(params)
            .then(res=> {
                if(res.code == 1){
                    this.getDate();
                    this.notify("添加成功","success");
                }else{
                    this.notify("添加失败","error");
                }
            })
            .catch(err=>{
                console.log(err);
            });
            this.centerDialogVisible=false;
        },
        //删除功能
        deleteRow(){
            delListSong(this.idx)
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
.singer-img{
    width: 100%;
    height: 80px;
    /* 圆弧 */
    border-radius: 5px;
    margin-bottom:5px;
    /* 超出部分进行隐藏 */
    overflow: hidden;
}
.handle-input{
    width: 300px;
    display: inline-block;
}
.pagination{
    display: flex;
    justify-content: center;
}
</style>