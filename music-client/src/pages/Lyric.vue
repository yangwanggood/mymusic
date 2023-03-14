<!-- 歌词显示视图 -->
<template>
    <div class="song-lyric">
       <h1 class="lyric-title">歌词</h1>
       <!-- 有歌词就显示  key 用不到-->
       <ul class="has-lyric" v-if="lyr.length" key="index">
        <li v-for="(item,index) in lyr" v-bind:key="index">
           {{item[1]}}
       </li>
       </ul>
       <!-- 没有歌词的情况下 -->
       <div v-else class="no-lyric" key="no-lyric">
        <span>暂无歌词</span>
       </div>
    </div>
</template>
<script>
import { mixin } from '../mixins';
import { mapGetters } from 'vuex';
export default {
    mixins:[mixin],
   name:'lyric',
   data() {
    return {
        lyr:[]
    }
   },
   computed:{
    ...mapGetters([
        'curTime', //歌曲播放位置
        'id',      //歌曲id
        'lyric',   //歌词
        'listIndex',//当前歌曲在歌单中的位置
        'listOfSong',//当前歌曲列表
    ])
   },
   created(){
      this.lyr=this.lyric
   },
   watch:{
    //监控id 只要一变化就执行id
    id:function(){
        this.lyr=this.parseLyric(this.listOfSong[this.listIndex].lyric)
    },
    curTime:function(){
        //歌词存在
        if (this.lyr.length) {
            //遍历出来
            for(let i=0;i<this.lyr.length;i++){
                //curTime 当前播放的时间 大于  原来这个位置的时间[i]是时间  就变黑#000 
                 if(this.curTime>=this.lyr[i][0]){
                    //j是变回原形
                    for(let j=0;j<this.lyr.length;j++){
                        document.querySelectorAll('.has-lyric li')[j].style.color='#000';
                        document.querySelectorAll('.has-lyric li')[j].style.fontSize='15px';
                    }
                    if(i>0){
                        document.querySelectorAll('.has-lyric li')[i].style.color='#95d2f6';
                        document.querySelectorAll('.has-lyric li')[i].style.fontSize='25px';
                    }
                 }
            }
        }
    }
    
   }
}
</script>


<style lang="scss" scoped>
@import '../assets/css/lyric.scss'
</style>