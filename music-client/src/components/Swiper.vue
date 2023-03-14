<template>
  <!-- 轮播图 -->
    <div class="swiper">
      <!--interval 4秒一次  -->
      <el-carousel :interval="4000" type="card" height="280px">
          <el-carousel-item v-for="(item,index) in swiperList" :key="index">
           <img :src="item.PicImg" />
          </el-carousel-item>
      </el-carousel>
    </div>
  </template>
  
  <script>
  import {swiperList} from '../assets/data/swiper';
  export default {
      name:"swiper",
         data() {
          return {
              swiperList:[]
          }
      },
      created(){
      this.swiperList=swiperList;
     }
  
  }
  </script>
  
  <style lang="scss" scoped>
  @import '../assets/css/swiper.scss';
  /* .swiper {
    width: 90%;
    margin: auto;
    margin-top: 40px;
    img {
      width: 100%;
    }
  } */
  
  </style>