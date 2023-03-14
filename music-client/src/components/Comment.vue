<!-- 评论区 -->
<template>
    <div>
    <div class="comment">
        <h2>评论</h2>
        <div class="comment-msg">
            <div class="comment-img ">
            <img :src="attchImageUrl(avator)"/>
        </div>
            <el-input class="comment-input" type="textarea" :rows="3" placeholder="请输入内容" v-model="textarea"></el-input>
        </div>
        <el-button class="sub-btn"  type="primary" @click="postComment">提交</el-button>
    </div>
    <p>精彩评论：共{{commentList.length}}</p>
    <ul class="popular" v-for="(item,index) in commentList" :key="index">
         <li> 
            <div class="popular-img">
                <img :src="attchImageUrl(userPic[index])">
            </div>
            <div class="popular-msg">
                <ul>
                    <li class="name">{{userName[index]}}</li>
                    <li class="time">{{item.createTime}}</li>
                    <li class="content">{{item.content}}</li>
                    
                </ul>
            </div>
            <!--item.up当前有多少个赞 index索引 -->
            <div class="up" ref="up" @click="postUp(item.id,item.up,index)">
            <svg class="icon">
                <use xlink:href="#icon-zan"></use>
            </svg>
            {{item.up}}
            </div>
        </li>
    </ul>
</div>
</template>

<script>
import { mixin } from '../mixins';
import { mapGetters } from 'vuex';
import{setComment,setLike,getAllComment,getUserId} from '../api/index'
export default {
    mixins:[mixin],
   name:'comment', 
   computed:{
    ...mapGetters([
        'id',
        'userId', 
        'avator',
        'loginIn'
    ])
   },
   props:[
           'playId', //歌单或歌曲id
           'type'    //歌曲0 歌单1
         ],
    data() {
        return {
            textarea:'', //存入输入的内容
            commentList:[], //存放评论列表的内容
            userName:[],//存放用户姓名
            userPic:[], //存放用户头像
            
           
        }
    },
    mounted(){
        this.getComment();
    },
    methods:{
        postComment(){
          if(this.loginIn){
            let params=new URLSearchParams()
            if(this.type==0){
                params.append('songId',this.playId);
            }else{
                params.append('songListId',this.playId);
            }
            params.append('userId',this.userId);
            params.append('type',this.type);
            params.append('content',this.textarea);
            setComment(params)
              .then(res=>{
                if(res.code==1){
                    this.notify('评论成功','success');
                    this.textarea='',
                    this.getComment();
                }else{
                    this.notify('评论失败','error');
                }
              })
              .catch(err=>{
                this.notify('出错了','error');
              })
          }else{
            this.notify('请先登录','warning');
          }
        },
       //评论列表
        getComment(){
            getAllComment(this.type,this.playId)
                .then(res=>{
                    this.commentList=res;
                    for(let item of res){
                        this.getUserId(item.userId);
                    }
                    //评论列表会出现各个用户的信息 头像 和名字  所以需要搜索出用户信息
                }).catch(err=>{
                 this.notify('评论加载失败','error');
                 })
        },
        getUserId(id){
            getUserId(id)
              .then(res=>{
                this.userPic.push(res.avator)
                this.userName.push(res.username)
              }).catch(err=>{
                 this.notify('出错了','error')
                 })
        },
       
        //点赞
        postUp(id,up,index){ 
            if(this.loginIn){
              let params=new URLSearchParams()
              params.append('id',id);
              params.append('up',up+1);
              setLike(params)
                .then(res=>{
                  if(res.code==1){
                      this.$refs.up[index].children[0].style.color='#2796cd';
                      this.getComment();
                  }
                })
                .catch(err=>{
                  this.notify('点赞失败','error');
                })
            }else{
              this.notify('请先登录','warning');
            }
        }
        
    }
}
</script>



<style lang="scss" scoped>
@import '../assets/css/comment.scss';
</style>
