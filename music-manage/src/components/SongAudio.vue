<template>
    <div class="song-audio">
      <!--controls="controls" 控制   preload="true"是不是预先加载-->
        <audio id="player" 
        :src="url"
        controls="controls"
        preload="true"
        @canplay="startPlay"
        @ended="ended"
>
    </audio>
    </div>
</template>

<script>
import { mapGetters } from 'Vuex';
export default {
  name:'song-audio',
  computed:{
    ...mapGetters([
        'id',
        'url',
        'isPlay'
    ])
  },
  watch:{
    isPlay:function(){
        this.togglePlay();
    }
  },
    methods:{
        startPlay(){
                let player=document.querySelector('#player');
                //开始播放
                player.play();
            },
            //播放完成触发
            ended(){
                this.isPlay=false
            },
             //暂停和播放
            togglePlay(){
                let player=document.querySelector('#player');
                if(this.isPlay){ 
                        player.play();
                }else{
                    player.pause();
                }
            }
    }
}
</script>

<style>
  .song-audio{
    display: none;
  }
</style>