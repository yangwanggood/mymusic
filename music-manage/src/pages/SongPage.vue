<template>

        
    <!-- table封装在main css中 -->
    <div class="table"> 
        <div class="crumbs">
            <i class="el-icon-tickets"></i> 歌曲信息
    </div>

        <div class="container">
            <div class="handle-box">
                <!--批量删除 -->
                <el-button type="primary" size="mini" @click="delAll">批量删除</el-button>
                <!-- 搜索框 -->
                <el-input v-model="select_word" size="mini" placeholder="请输入歌曲名" class="handle-input"></el-input>
               
               <el-button type="primary" size="mini" @click="centerDialogVisible=true">添加歌曲</el-button>
            </div>
        </div>

        <!-- border style="width:100%" 一个边框   data数据是  -->
        <el-table size="mini" ref="multipleTable" border-width style="width:100%" height="680px" :data="data" @selection-change="handleSelectionChange">
            <!-- type="selection" 多选框 批量删除-->
            <el-table-column type="selection" width="40"></el-table-column>
            <!--align 内容居中   -->
                <el-table-column label="歌曲图片" width="110" >
                           <!-- slot-scope="scope" 搜索出来的单元格的数据 -->
                        <template slot-scope="scope">
                            <div class="song-img">
                                <!--scope.row.pic 单元格.行.地址   style 该区域的属性说明-->
                                <img :src="getUrl(scope.row.pic)" style="width:100%"/>
                            </div>
                            
                            <!--uploadUrl(scope.row.id) id是idea中需要传递一个id过去  beforeAvatorUpload 上传成功之前  handleAvatorSuccess 上传成功之后-->
                           <div class="play" @click="setSongUrl(scope.row.url,scope.row.name)">
                            <div v-if="toggle==scope.row.name">
                            <svg class="icon">
                                <use xlink:href="#icon-zanting"></use>
                            </svg>
                            </div>
                            <div v-if="toggle !=scope.row.name">
                            <svg class="icon">
                                <use xlink:href="#icon-bofanganniu"></use>
                            </svg>
                            </div>
                           </div>
                        </template>
                </el-table-column>
                <el-table-column prop="name" label="歌手-歌名" width="120" :cell-style="{ textAlign: 'center' }" >
                </el-table-column>
                <el-table-column prop="introduction" label="专辑" width="150" :cell-style="{ textAlign: 'center' }" ></el-table-column>
                    <el-table-column label="歌词"   :cell-style="{ textAlign: 'center' }" >
                    <template slot-scope="scope">
                        <ul style="height:100px; overflow:scroll;"> 
                        <li v-for="(item,index) in parseLyric(scope.row.lyric)" :key="index">
                            {{item}}
                        </li>
                        </ul>
                      
                    </template>
                </el-table-column>
                <el-table-column label="资源更新"  width="100" :cell-style="{ textAlign: 'center' }" >
                    <template slot-scope="scope">
                        <el-upload :action="uploadUrl(scope.row.id)" :before-upload="beforeAvatorUpload" 
                            :on-success="handleAvatorSuccess">
                               <el-button size="mini">更新图片</el-button>
                            </el-upload> 
                            <br/>
                            <el-upload :action="uploadSongUrl(scope.row.id)" :before-upload="beforeSongUpload" 
                            :on-success="handleSongSuccess">
                               <el-button size="mini">更新歌曲</el-button>
                            </el-upload>
                      
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
        <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
                                         <!-- el-form:需要提交的表单  label-width:左右显示 如果不定义 标签会自己一行-->
        <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf"> 
         <div>
            <label>歌名</label>
            <el-input type="text" name="name"></el-input>
         </div>
         <div>
            <label>专辑</label>
            <el-input type="text" name="introduction"></el-input>
         </div>
         <div>
            <label>歌词</label>
            <el-input type="textarea" name="lyric"></el-input>
         </div>
         <div>
            <label>歌曲上传</label>
            <input type="file" name="file">
         </div>
        </el-form>
        <span slot="footer">
            <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
            <el-button size="mini" @click="addSong">确定</el-button>
        </span>

        </el-dialog>
        <el-dialog title="修改歌曲" :visible.sync="editVisible" width="400px" center>
                                         <!-- el-form:需要提交的表单  label-width:左右显示 如果不定义 标签会自己一行-->
        <el-form :model="form" ref="form" label-width="80px">
                                         <!-- size="mini"字体最小的方式体现 -->
            <el-form-item prop="name" label="歌手-歌名" size="mini"> 
                 <el-input v-model="form.name" placeholder="歌手-歌名"></el-input>
            </el-form-item>
            <el-form-item prop="introduction" label="专辑" size="mini"> 
                <!-- type="textarea" 类型是text 单行 -->
                 <el-input v-model="form.introduction" placeholder="专辑"></el-input>
            </el-form-item>
            <el-form-item prop="lyric" label="歌词" size="mini"> 
                <!-- type="textarea" 类型是可拉长的文件框 -->
                 <el-input v-model="form.lyric" placeholder="歌词" type="textarea"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer">
            <el-button size="mini" @click="editVisible=false">取消</el-button>
            <el-button size="mini" @click="editSave">确定</el-button>
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

import { mixin } from '../mixins/index';
import { mapGetters } from 'Vuex';
import '@/assets/js/iconfont.js';
import {songOfSingerId,updateSong,delSong} from '../api/index';
export default {
    mixins:[mixin],
    data() {
        return {
            singerId:'',                //歌手id
            singerName:'',              //歌手名
            centerDialogVisible:false,  //添加框这个框是否显示 默认false 不显示
            editVisible:false,          //编辑框这个框是否显示 默认false 不显示
            delVisible:false,           //删除框是否显示
            registerForm:{              //添加框
                   name:'',
                   singerName:'',      //歌唱者
                   introduction:'',
                   lyric:'',

            },
            form:{              //编辑框
                  id:'',
                  name:'',
                  singerName:'',      //歌唱者
                  introduction:'',
                  lyric:'',
            },
            tableDate:[],                //查询出来的歌手放在这 
            tempDate:[],                 //查询出来的歌手放在这 
            select_word:'',              //搜索框输入的文字
            pageSize:5,                  //每一页5个
            currentPage:1,               //默认第几页
            idx:-1,                       //当前的选择项
            multipleSelection:[],         //哪些项打勾了
            toggle:false                  //默认显示图标的状态

        }
    },
    created(){
        this.singerId=this.$route.query.id;
        this.singerName=this.$route.query.name;
        this.getDate();
    },
    destroyed(){
        this.$store.commit('setIsPlay',false);
    },
    computed:{
        ...mapGetters([
        'id',
        'url',
        'isPlay'
    ]),
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
            this.tempDate=[];
            this.tableDate=[];          //查询前先清空一下 
            songOfSingerId(this.singerId)
            .then(res=>{ 
                this.tempDate=res;   //搜索功能后加
                this.tableDate=res;
                this.currentPage=1;
            })
        },
        //添加歌手
        addSong(){
            let _this=this;
            //FormData还支持直接从html中的表单生成数据，就是在html页面中已经有数据了，然后FormData可以直接把这个表单的数据写入这个对象，然后直接提交给后台。
            var form =new FormData(document.getElementById('tf'));
            form.append('singerId',this.singerId);
            form.set('name',this.singerName+'-'+form.get('name'));
            if(!form.get('lyric')){
                //歌词是0小时 0分 0秒开始 
                form.set('lyric','[00:00:00]暂无歌词')
            }
            var req=new XMLHttpRequest();//对象可以与服务器交互。您可以从URL获取数据，而无需让整个的页面刷新。这使得Web页面可以只更新页面的局部
            req.onreadystatechange=function(){
                if(req.readyState==4 && req.status==200){
                    let res=JSON.parse(req.response);
                    if(res.code){
                        _this.getDate();
                        _this.registerForm={}; //添加框为空
                        _this.notify(res.msg,'success')    
                    }else{
                        _this.notify('上传失败','error');
                    }
                } 
            }
            console.log("12312",`${_this.$store.state.HOST}/song/add`);
            req.open('post',`${_this.$store.state.HOST}/song/add`,false); //false异步 true同步
            req.send(form);

            _this.centerDialogVisible=false;
        },
        handleEdit(row){ //row 当前行  弹出编辑页面 
            this.editVisible=true;
            this.form={
                id:row.id,
                name:row.name,
                introduction:row.introduction,
                lyric:row.lyric,

            }
        },
        //修改歌手
        editSave(){   // 保存 编辑框的确定按钮
            let params=new URLSearchParams //往后台传的参数
            params.append('id',this.form.id);
            params.append('name',this.form.name);
            params.append('introduction',this.form.introduction);
            params.append('lyric',this.form.lyric);
            
            updateSong(params)
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
            return `${this.$store.state.HOST}/song/updateSongPic?id=${id}`
        },
        //删除功能
        deleteRow(){
            delSong(this.idx)
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
        //歌词的解析
        parseLyric(text){
            //split 分割成数值
            let lines =text.split("\n");
            let pattern=/\[\d{2}:\d{2}.(\d{3}|\d{2})]/g;
            let result=[];
            for(let item  of lines){
                let value=item.replace(pattern,"");
                result.push(value);
            }
                return result;
        },

       //  上传歌曲之前的校验
       
       beforeSongUpload(file) {
            //取文件名后面的 后缀
           var testMsg=file.name.substring(file.name.lastIndexOf('.')+1)
           if(testMsg!='mp3'){
            this.$message({
                message:'上传文件只能是mp3格式',
                type:'error'
            });
            return false;
           }
            return true;
        },
        //上传歌曲成功之后要做的工作
        handleSongSuccess(res) {
            //_this 相当于复制了handleAvatorSuccess方法的这个页=当前页面
            let _this = this;
            if (res.code == 1) {
                //上传成功后刷新一下页面  就会出现新的图片
                _this.getDate();
                _this.$notify({
                    title: '上传成功',
                    type: 'success',
                })
            } else {
                _this.$notify({
                    title: '上传失败',
                    type: 'error',
                })
            }
        },
        //更新歌曲的url
        uploadSongUrl(id){
            return `${this.$store.state.HOST}/song/updateSongUrl?id=${id}`
        },
         //切换播放歌曲
        setSongUrl(url,name){
            this.toggle=name;
            this.$store.commit('setUrl',this.$store.state.HOST+url);
            if(this.isPlay){
                this.$store.commit('setIsPlay',false);
            }else{
                this.$store.commit('setIsPlay',true);
            }
        }
    }
}
</script>
<style scoped>
.handle-box{
    margin-bottom: 20px;
}
.song-img{
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
.play{
    position: absolute;
    /*  覆盖在其它样式的上面  跟小的话 同样会覆盖自己*/
    z-index:100;
    width: 80px;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    /* 鼠标移过来的样式 */
    cursor: pointer;
    top: 18px;
    left: 25px;
}
.icon{
    widows: 2em;
    height: 2em;
    color: white;
    /* 填充 不改变颜色 */
    fill:currentColor;
    /* 超出隐藏 */
    overflow: hidden;
    
}
</style>