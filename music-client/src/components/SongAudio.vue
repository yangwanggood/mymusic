
<!-- 播放器 -->
<template>
    <div class="song-audio">
      <!--controls="controls" 控制   preload="true"是不是预先加载-->
      <!-- ref 写在了audio组件上   支持进度条拖拽
支持 PC 端和移动端
完善的文档和示例
 @timeupdate ：当前的播放位置发生改变会怎么样 会执行timeupdate函数-->
        <audio ref="player" 
        :src="url"
        controls="controls"
        preload="true"
        @canplay="startPlay"
        @ended="ended"
        @timeupdate="timeupdate"
>
    </audio>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
  name:'song-audio',
  computed:{
    // isPlay:是否播放中
    //     url:歌曲地址
    //     id:  歌曲id
    ...mapGetters([
        'id',
        'url',
        'isPlay',   //播放状态
        'listOfSong',//歌曲列表
         'curTime', //当前音乐的播放位置
       'changeTime', //指定播放时刻
        'autoNext',//用于自动触发播放下一首
         'volume' //音量
    ])
  },
  watch:{
    //暂停还是播放
    isPlay:function(){
        this.togglePlay();
    },
    //改变指定播放时刻
    changeTime(){
      this.$refs.player.currentTime=this.changeTime
    },
    //改变音量
    valume(val){
      this.$refs.player.valume=val
    }
  },
    methods:{
        startPlay(){
                let player=this.$refs.player;
                //音乐时长
                this.$store.commit('setDuration',player.duration)
                //开始播放
                player.play();
                this.$store.commit('setIsPlay',true)
            },
            //播放完成触发
            ended(){
              this.$store.commit('setIsPlay',false);
              //播放的位置
              this.$store.commit('setCurTime',0);
              //!this.autoNext 时 会去浏览器里面找下一首
              this.$store.commit('setAutoNext',!this.autoNext);
            },
             //暂停和播放
            togglePlay(){
                let player=this.$refs.player;
                if(this.isPlay){ 
                        player.play();
                }else{
                    player.pause();
                }
            },
            //音乐播放时记录音乐的播放位置
            timeupdate(){
              this.$store.commit('setCurTime',this.$refs.player.currentTime);
            }
    }
}
</script>

<style>
  .song-audio{
    display: none;
  }
</style>