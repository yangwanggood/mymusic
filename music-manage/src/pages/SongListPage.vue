<template>
    <!-- table封装在main css中 -->
    <div class="table"> 
        <div class="container">
            <div class="handle-box">
                <!--批量删除 -->
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <!-- 搜索框 -->
                <el-input v-model="select_word" size="mini" placeholder="筛选歌单名" class="handle-input"></el-input>
               
               <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加歌单</el-button>
            </div>
        </div>

        <!-- border style="width:100%" 一个边框   data数据是  -->
        <el-table size="mini" border-width style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <!-- type="selection" 多选框 批量删除-->
            <el-table-column type="selection" width="40"></el-table-column>
            <!--align 内容居中   -->
                <el-table-column label="歌单图片" width="110" >
                           <!-- slot-scope="scope" 搜索出来的单元格的数据 -->
                        <template slot-scope="scope">
                            <div class="singer-img">
                                <!--scope.row.pic 单元格.行.地址   style 该区域的属性说明-->
                                <img :src="getUrl(scope.row.pic)" style="width:100%"/>
                            </div>
                            <!--uploadUrl(scope.row.id) id是idea中需要传递一个id过去  beforeAvatorUpload 上传成功之前  handleAvatorSuccess 上传成功之后-->
                            <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" 
                            :on-success="handleAvatorSuccess">
                               <el-button size="mini">更新图片</el-button>
                            </el-upload>
                        </template>
                </el-table-column>
                <el-table-column prop="title" label="标题" width="120" :cell-style="{ textAlign: 'center' }" >
                </el-table-column>
                <el-table-column label="简介">
                    <template slot-scope="scope">
                       <p style="height:100px;overflow:scroll">{{scope.row.introduction}}</p>
                    </template>
                    </el-table-column>
                    <el-table-column prop="style" label="风格" width="120" :cell-style="{ textAlign: 'center' }" >
                    </el-table-column>
                    <el-table-column label="歌曲管理"  width="110" :cell-style="{ textAlign: 'center' }" >
                    <template slot-scope="scope">
                        <!--songEdit(scope.row.id)" 将id传过去  -->
                       <el-button size="mini" @click="songEdit(scope.row.id)">歌曲管理</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="150"  :cell-style="{ textAlign: 'center' }">
                    <template slot-scope="scope">
                       <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                       <!--type="danger" :红色按钮  -->
                       <el-button size="mini" type="danger"  @click="handleDelete(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
        </el-table>

        <div class="pagination">
             <!-- background 背景 -->
             <!-- total:总共  prev:上一页 pager:当前页，next：下一页 -->
             <!-- :current-page:当前页是多少 ="currentPage"已经配置好的currentPage 第一页 -->
             <!-- :total="tableDate.length "  总共的大小-->
             <!-- @current-change="handleCurrentChange" 方法 使翻页的时候数据跟着动 -->
            <el-pagination
            background 
            layout="total,prev,pager,next"
            :current-page="currentPage"
            :page-size="pageSize"
            :total="tableDate.length"
            @current-change="handleCurrentChange"
            ></el-pagination>
        </div>

                                         <!-- el-dialog 弹出一个框  visible:隐藏的 将 title里面的值隐藏  框的宽度400-->
        <el-dialog title="添加歌单" :visible.sync="centerDialogVisible" width="400px" center>
                                         <!-- el-form:需要提交的表单  label-width:左右显示 如果不定义 标签会自己一行-->
        <el-form :model="registerForm" ref="registerForm" label-width="80px">
                                         <!-- size="mini"字体最小的方式体现 -->
            <el-form-item prop="title" label="标题" size="mini"> 
                 <el-input v-model="registerForm.title" placeholder="标题"></el-input>
            </el-form-item>
          
            <el-form-item prop="introduction" label="简介" size="mini"> 
                <!-- type="textarea" 类型是 textarea可以拉长的简介框 -->
                 <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
            </el-form-item>
            <el-form-item prop="style" label="风格" size="mini"> 
                <!-- type="textarea" 类型是 textarea可以拉长的简介框 -->
                 <el-input v-model="registerForm.style" placeholder="风格"></el-input>
            </el-form-item>

        </el-form>
        <span slot="footer">
            <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
            <el-button size="mini" @click="addSinger">确定</el-button>
        </span>

        </el-dialog>
        <el-dialog title="修改歌单" :visible.sync="editVisible" width="400px" center>
                                         <!-- el-form:需要提交的表单  label-width:左右显示 如果不定义 标签会自己一行-->
        <el-form :model="form" ref="form" label-width="80px">
                                         <!-- size="mini"字体最小的方式体现 -->
            <el-form-item prop="title" label="标题" size="mini"> 
                 <el-input v-model="form.title" placeholder="标题"></el-input>
            </el-form-item>
            <el-form-item prop="introduction" label="简介" size="mini"> 
                <!-- type="textarea" 类型是 textarea可以拉长的简介框 -->
                 <el-input v-model="form.introduction" placeholder="简介" type="textarea"></el-input>
            </el-form-item>
            <el-form-item prop="style" label="风格" size="mini"> 
                 <el-input v-model="form.style" placeholder="风格"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button size="mini" @click="editVisible=false">取消</el-button>
            <el-button size="mini" @click="editSave">确定</el-button>
        </span>
        </el-dialog>

        <el-dialog title="删除歌单" :visible.sync="delVisible" width="400px" center>
         <div :cell-style="{ textAlign: 'center' }"> 删除不可恢复,是否确定删除?</div>       
        <span slot="footer">
            <el-button size="mini" @click="delVisible=false">取消</el-button>
            <el-button size="mini" @click="deleteRow">确定</el-button>
        </span>

        </el-dialog>

    </div>
</template>
<script>
import {getAllSongList,setSongList,updateSongList,delSongList} from '../api/index';
import { mixin } from '../mixins/index';
export default {
    mixins:[mixin],
    data() {
        return {
            centerDialogVisible:false,  //添加框这个框是否显示 默认false 不显示
            editVisible:false,          //编辑框这个框是否显示 默认false 不显示
            delVisible:false,           //删除框是否显示
            registerForm:{              //添加框
                   title:'',
                   introduction:'',
                   style:''
            },
            form:{              //编辑框
                  id:'',
                  title:'',
                   introduction:'',
                   style:''
            },
            tableDate:[],                //查询出来的歌单放在这 
            tempDate:[],                 //查询出来的歌单放在这 
            select_word:'',              //搜索框输入的文字
            pageSize:5,                  //每一页5个
            currentPage:1,               //默认第几页
            idx:-1,                       //当前的选择项
            multipleSelection:[],         //哪些项打勾了

        }
    },
    created(){
        this.getDate();
    },
    computed:{
        //计算当前搜索结果表里的数据
      data(){
        //slice 从那里开始获取 (开始,结束)
        return this.tableDate.slice((this.currentPage -1) * this.pageSize,this.currentPage * this.pageSize)
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
                if(item.title.includes(this.select_word)){
                    this.tableDate.push(item)
                    //push到tableDate
                }
            }
           }
        }
    },
    methods:{
        //翻页时能 获取当前页
        handleCurrentChange(val){
         this.currentPage=val;
        },
        //查询所有歌单
        getDate(){
            this.tempDate=[],
            this.tableDate=[];          //查询前先清空一下 
            getAllSongList().then(res=>{
                this.tempDate=res;   //搜索功能后加
                this.tableDate=res;
                this.currentPage=1;  //查询之后进入第一页
            })
        },
        //添加歌单
        addSinger(){
            let params=new URLSearchParams //往后台传的参数
            params.append('title',this.registerForm.title);
            params.append('pic','/img/songListPic/hhhh.png');
            params.append('introduction',this.registerForm.introduction);
            params.append('style',this.registerForm.style)
            
            setSongList(params)
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
        handleEdit(row){ //row 当前行  弹出编辑页面 
            this.editVisible=true;
            this.form={
                id:row.id,
                title:row.title,
                introduction:row.introduction,
                style:row.style

            }
        },
        //修改歌单
        editSave(){   // 保存 编辑框的确定按钮
            let params=new URLSearchParams //往后台传的参数
            params.append('id',this.form.id);
            params.append('title',this.form.title);
            params.append('introduction',this.form.introduction);
            params.append('style',this.form.style)
            
            updateSongList(params)
            .then(res=> {
                if(res.code == 1){
                    this.getDate();
                    this.notify("修改成功","success");
                }else{
                    this.notify("修改失败","error");
                }
            })
            .catch(err=>{
                console.log(err);
            });
            this.editVisible=false;
        },
        uploadUrl(id){
            return `${this.$store.state.HOST}/songList/updateSongListPic?id=${id}`
        },
        //删除功能
        deleteRow(){
            delSongList(this.idx)
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
        songEdit(id,name){
            //点击之后进入song路劲 传出一个id name
            this.$router.push({path:`/ListSong`,query:{id}})
        }
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