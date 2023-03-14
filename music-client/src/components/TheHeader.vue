<!-- 搜索栏 -->
<template>
  <div class="the-header">
    <div class="header-logo" @click="goHome">
      <svg class="icon">
          <use xlink:href="#icon-erji"></use>
      </svg>
      <span>music</span>
      </div>
    <ul class="navbar">
      
      <li :class="{active: item.name == activeName}" v-for="item in navMsg" :key="item.path"  @click="goPage(item.path,item.name)"> 
        {{item.name}}
      </li>

      <li>
        <div class="header-search">                 <!-- keyup.enter点击回车之后触发 -->
          <input type="text"  placeholder="搜索" @keyup.enter="goSearch()"  v-model="keywords">
           <!--"goSearch() 点击后触发 -->
          <div class="search-btn" @click="goSearch()">
          <svg class="icon">
            <use xlink:href="#icon-sousuo"></use>
          </svg>
          </div> 
        </div>
      </li>
      <li v-show="!loginIn" :class="{active: item.name == activeName}"  v-for="item in loginMsg" :key="item.path"  @click="goPage(item.path,item.name)"> 
        {{item.name}}
      </li>
    </ul>
    <div class="header-right" v-show="loginIn">
      <div id='user'>
        <img :src='attchImageUrl(avator)'>
      </div>
      <div class="menu">
        <li v-for="(item,index) in menuList" :key="index" @click="goMenuList(item.path)">
        {{item.name}}
        </li>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';

import {navMsg,loginMsg,menuList} from "../assets/data/header";
export default {
  name: 'the-header',
  data() {
    return {
      navMsg:[],
      keywords:'',
      loginMsg:[],
      menuList:[],
    }
  },


computed:{
   ...mapGetters([
   'activeName',  //当前需要访问的菜单名
   'loginIn',   //是否登录
   'avator'  //头像
   ])
},
created(){
    this.navMsg=navMsg;
    this.loginMsg=loginMsg;
    this.menuList=menuList;
  },

  mounted(){
    //document.querySelector('#user') 目标位子  addEventListener('click')监听  document.querySelector('.menu')执行
  document.querySelector('#user').addEventListener('click',function(e){
    document.querySelector('.menu').classList.add("show");
    e.stopPropagation(); //阻止冒泡进一步执行
  },false);

  document.querySelector('.menu').addEventListener('click',function(e){
    e.stopPropagation(); //阻止冒泡进一步执行
  },false);

  document.addEventListener('click',function(){
    document.querySelector('.menu').classList.remove("show");
  },false);
},

  methods:{
    notify(title, type) {
            this.$notify({
                title: title,
                type: type
            })
        },
      goHome(){
           this.$router.push({path:"/"})
      },
      goPage(path,name){
        if(!this.loginIn&&path=='/my-music'){//在没有登录时点击 我的音乐界面
           this.notify('请先登录','warning')
        }else{ 
          this.$store.commit('setActiveName',name);
          this.$router.push({path:path});}
      },
      goSearch(){
         this.$router.push({path:"/search",query:{keywords:this.keywords}})
         
      },
       //获取图片地址
       attchImageUrl(srcUrl) {
            return srcUrl ? this.$store.state.configure.HOST + srcUrl : '../assets/img/user.jpg';
        },
        //头像点击
        goMenuList(path){
        if(path==0){
          this.$store.commit('setLoginIn',false); //把登录改为没有登录 在configure
          this.$store.commit('setIsActive',false); //把登录改为没有登录 在configure
          this.$router.go(0);  //意思是刷新一下页面
        }else{
          this.$router.push({path:path});
        }
        }
      

  },
 
  
}
</script >

<style lang="scss" scoped>
  @import '../assets/css/the-header.scss';
.li{
  margin: $header-nav-margin;
        padding: $header-nav-padding;
        font-size: $font-size-header;
        color: $color-grey;
        text-align: center;
        border-bottom: none;
        box-sizing: border-box;
        cursor: pointer;
}
</style>
