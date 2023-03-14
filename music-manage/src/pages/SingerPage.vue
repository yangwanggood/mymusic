<template>
    <!-- table封装在main css中 -->
    <div class="table"> 
        <div class="container">
            <div class="handle-box">
                <!--批量删除 -->
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <!-- 搜索框 -->
                <el-input v-model="select_word" size="mini" placeholder="请输入歌手名" class="handle-input"></el-input>
               
               <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加歌手</el-button>
            </div>
        </div>

        <!-- border style="width:100%" 一个边框   data数据是  -->
        <el-table size="mini" border-width style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <!-- type="selection" 多选框 批量删除-->
            <el-table-column type="selection" width="40"></el-table-column>
            <!--align 内容居中   -->
                <el-table-column label="歌手图片" width="110" >
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
                <el-table-column prop="name" label="歌手" width="120" :cell-style="{ textAlign: 'center' }" >
                </el-table-column>
                <el-table-column label="性别" width="50" :cell-style="{ textAlign: 'center' }">
                    <!-- slot-scope="scope" 取到当前这行的数据  -->
                    <template slot-scope="scope">
                        {{changeSex(scope.row.sex)}}
                    </template>
                </el-table-column>
                <!-- 生日 -->
                <el-table-column prop="birth" label="生日" width="120" :cell-style="{ textAlign: 'center' }" >
                    <template slot-scope="scope">
                        {{attachBirth(scope.row.birth)}}
                    </template>
                </el-table-column>
                <!-- 地区 -->
                <el-table-column prop="location" label="地区" width="100" :cell-style="{ textAlign: 'center' }"></el-table-column>
               
                <el-table-column label="简介"  :cell-style="{ textAlign: 'center' }" >
                    <template slot-scope="scope">
                       <p style="height:100px;overflow:scroll">{{scope.row.introduction}}</p>
                    </template>
                    </el-table-column>
                    <el-table-column label="歌曲管理"  width="110" :cell-style="{ textAlign: 'center' }" >
                    <template slot-scope="scope">
                        <!--songEdit(scope.row.id)" 将id传过去  -->
                       <el-button size="mini" @click="songEdit(scope.row.id,scope.row.name)">歌曲管理</el-button>
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
        <el-dialog title="添加歌手" :visible.sync="centerDialogVisible" width="400px" center>
                                         <!-- el-form:需要提交的表单  label-width:左右显示 如果不定义 标签会自己一行-->
        <el-form :model="registerForm" ref="registerForm" label-width="80px">
                                         <!-- size="mini"字体最小的方式体现 -->
            <el-form-item prop="name" label="歌手名" size="mini"> 
                 <el-input v-model="registerForm.name" placeholder="歌手名"></el-input>
            </el-form-item>
            <el-form-item label="性别" size="mini" > 
                 <el-radio-group v-model="registerForm.sex">
                 <el-radio :label="0">女</el-radio>
                 <el-radio :label="1">男</el-radio>
                 <el-radio :label="2">组合</el-radio>
                 <el-radio :label="3">不明</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="生日" size="mini"> 
                 <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth" style="width:100%"></el-date-picker>
            </el-form-item>
            <el-form-item prop="location" label="地区" size="mini"> 
                 <el-input v-model="registerForm.location" placeholder="地区"></el-input>
            </el-form-item>
            <el-form-item prop="introduction" label="简介" size="mini"> 
                <!-- type="textarea" 类型是 textarea可以拉长的简介框 -->
                 <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
            <el-button size="mini" @click="addSinger">确定</el-button>
        </span>

        </el-dialog>
        <el-dialog title="修改歌手" :visible.sync="editVisible" width="400px" center>
                                         <!-- el-form:需要提交的表单  label-width:左右显示 如果不定义 标签会自己一行-->
        <el-form :model="form" ref="form" label-width="80px">
                                         <!-- size="mini"字体最小的方式体现 -->
            <el-form-item prop="name" label="歌手名" size="mini"> 
                 <el-input v-model="form.name" placeholder="歌手名"></el-input>
            </el-form-item>
            <el-form-item label="性别" size="mini" > 
                 <el-radio-group v-model="form.sex">
                 <el-radio :label="0">女</el-radio>
                 <el-radio :label="1">男</el-radio>
                 <el-radio :label="2">组合</el-radio>
                 <el-radio :label="3">不明</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item prop="birth" label="生日" size="mini"> 
                 <el-date-picker type="date" placeholder="选择日期" v-model="form.birth" style="width:100%"></el-date-picker>
            </el-form-item>
            <el-form-item prop="location" label="地区" size="mini"> 
                 <el-input v-model="form.location" placeholder="地区"></el-input>
            </el-form-item>
            <el-form-item prop="introduction" label="简介" size="mini"> 
                <!-- type="textarea" 类型是 textarea可以拉长的简介框 -->
                 <el-input v-model="form.introduction" placeholder="简介" type="textarea"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button size="mini" @click="editVisible=false">取消</el-button>
            <el-button size="mini" @click="editSave">确定</el-button>
        </span>
        </el-dialog>

        <el-dialog title="删除歌手" :visible.sync="delVisible" width="400px" center>
         <div :cell-style="{ textAlign: 'center' }"> 删除不可恢复,是否确定删除?</div>       
        <span slot="footer">
            <el-button size="mini" @click="delVisible=false">取消</el-button>
            <el-button size="mini" @click="deleteRow">确定</el-button>
        </span>

        </el-dialog>

    </div>
</template>
<script>
import { setSinger,getAllSinger,updateSinger,delSinger} from '../api/index';
import { mixin } from '../mixins/index';
export default {
    mixins:[mixin],
    data() {
        return {
            centerDialogVisible:false,  //添加框这个框是否显示 默认false 不显示
            editVisible:false,          //编辑框这个框是否显示 默认false 不显示
            delVisible:false,           //删除框是否显示
            registerForm:{              //添加框
                   name:'',
                   sex:'',
                   birth:'',
                   location:'',
                   introduction:''
            },
            form:{              //编辑框
                  id:'',
                   name:'',
                   sex:'',
                   birth:'',
                   location:'',
                   introduction:''
            },
            tableDate:[],                //查询出来的歌手放在这 
            tempDate:[],                 //查询出来的歌手放在这 
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
                if(item.name.includes(this.select_word)){
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
        //查询所有歌手
        getDate(){
            this.tempDate=[],
            this.tableDate=[];          //查询前先清空一下 
            getAllSinger().then(res=>{
                this.tempDate=res;   //搜索功能后加
                this.tableDate=res;
                this.currentPage=1;  //查询之后进入第一页
            })
        },
        //添加歌手
        addSinger(){
            let d=this.registerForm.birth;
            let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();  //d.getMonth()+1 这里的Month是从0月开始的
            let params=new URLSearchParams //往后台传的参数
            params.append('name',this.registerForm.name);
            params.append('sex',this.registerForm.sex);
            params.append('pic','/img/singerPic/hhh.png');
            params.append('birth',datetime);
            params.append('location',this.registerForm.location);
            params.append('introduction',this.registerForm.introduction);
            
            setSinger(params)
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
                name:row.name,
                sex:row.sex,
                birth:row.birth,
                location:row.location,
                introduction:row.introduction,

            }
        },
        //修改歌手
        editSave(){   // 保存 编辑框的确定按钮
            let d=new Date(this.form.birth);
            let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();  //d.getMonth()+1 这里的Month是从0月开始的
            let params=new URLSearchParams //往后台传的参数
            params.append('id',this.form.id);
            params.append('name',this.form.name);
            params.append('sex',this.form.sex);
            params.append('birth',datetime);
            params.append('location',this.form.location);
            params.append('introduction',this.form.introduction);
            
            updateSinger(params)
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
            return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`
        },
        //删除功能
        deleteRow(){
            delSinger(this.idx)
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
            this.$router.push({path:`/Song`,query:{id,name}})
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