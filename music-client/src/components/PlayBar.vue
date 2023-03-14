<!-- 播放器的页面  -->
<template>
    <!-- toggle本来是true的 前面一个！ 就代表toggle 为true不显示 -->
    <div class="play-bar" :class="{show:!toggle}">    
        <!--{turn:toggle  意思是toggle为true 才旋转   -->
        <div @click="toggle=!toggle" class="item-up" :class="{turn:toggle}">
            <svg class="icon">
                <use xlink:href="#icon-jiantou-xia-cuxiantiao"></use>
            </svg>
        </div>
        <div class="kongjian">
            <!-- 上一首的标签 -->
        <div class="item" @click="prev">
            <svg class="icon">
                <use xlink:href="#icon-ziyuanldpi"></use>
            </svg>
        </div>
        <!-- 播放 -->
        <div class="item" @click="togglePlay()">
            <svg class="icon">
                <!-- 因为 playButtonUrl 是store包下面的song.js-->
                <use :xlink:href="playButtonUrl"></use>
            </svg>
        </div>
        <!-- 下一首 -->
        <div class="item" @click="next">
            <svg class="icon">
                <use xlink:href="#icon-ziyuanldpi1"></use>
            </svg>
        </div>
        <!-- 歌曲图片 -->
        <div class="item-img" @click="goLyric">
            <!-- 暂时没写 占个位置 -->
                <img :src="picUrl"/>
        </div>
        <!-- 播放进度 playing-speed -->
        <div class="playing-speed">
            <!-- 开始播放开始时间 -->
            <div class="current-time">{{nowTime}}</div>
            <!-- 进度条样式 -->
            <div class="progress-box">
                 <div class="item-song-title">
                    <div>{{this.artist}}</div>
                    <!-- 它们两个会分开  scss中定义了 -->
                   <div>{{this.title}}</div> 
                 </div>
                <!--mousemove 移动时   外-->
                <div ref="progress" class="progress" @mousemove="mousemove">
                    <!-- 进度条内部 -->
                    <div ref="bg" class="bg" @click="updatemove">
                        <!-- 内部 -->
                        <div ref="curProgress" class="cur-progress" :style="{width:curLength+'%'}">
                        </div>
                    </div>
                    <!-- left 根据 mousedown点击时   mouseup 松开时-->
                   <div ref="idot" class="idot" :style="{left:curLength+'%'}" @mousedown="mousedown" @mouseup="mouseup"></div>
                </div>
            </div>
        
        <!-- 播放时间结束 -->
        <div class="left-item">{{songTime}}</div>
        <!-- 音量 -->
        <div class="item item-volume">
            <svg v-if="volume==0" class="icon">
                <use xlink:href="#icon-yinliangjingyinheix"></use>
            </svg>
            <svg v-else class="icon">
                <use xlink:href="#icon-yinliang1"></use>
            </svg>
            <!--el-slider 滑块标签  -->
            <el-slider class="volume" v-model="volume" :vertical="true"></el-slider>
        </div>
        <!-- 收藏  -->
        <div  class="item" @click="colletion">
            <svg :class="{active:isActive}" class="icon">
                <use xlink:href="#icon-xihuan-shi"></use>
            </svg>
        </div>
         <!-- 下载 -->
         <div class="item" @click="download">
            <svg class="icon">
                <use xlink:href="#icon-xiazai"></use>
            </svg>
          </div>
         <!-- 当前播放列表 -->
         <div class="item" @click="changeAside">
            <svg class="icon" >
                <use xlink:href="#icon-liebiao"></use>
            </svg>
          </div>
      </div>
 </div>
</div>
</template>

<script>

import {mapGetters} from 'vuex'
import { download,setCollect,getCollectOfUserId} from '../api/index' 
export default {
    name: "play-bar",

   
    data() {
        return {
            nowTime: "00:00",  //当前播放进度的时间
            songTime: "00:00",  //当前歌曲的总时间
            curLength:0, //进度条的位置
            progressLength:0, //进度条总长度
            mouseStartX:  0,  //拖拽点开始的位置 X 左边开始
            tag:false,        //拖拽是否开始
            volume: 50,       //默认音量50
            toggle:false        //播放器是否显示
            
            
        }
    },
    computed:{
            ...mapGetters([
            'isPlay', //是否播放中
            'url', //歌曲地址
            'id', //歌曲id
            'playButtonUrl', //播放状态的图标
            'picUrl',         //正在播放音乐的图片
            'title', //歌名
            'artist', //歌手名
            'duration', //音乐时长
           'curTime',//当前音乐的播放位置
           'listIndex',//当前歌曲在歌单中的位置
           'showAside',
           'listOfSong',   //当前歌曲列表
           'autoNext',
            'userId' , //用户id
           'loginIn',   //用户是否登录
            'isActive'  //是否显示收藏
            ])
    },
    mounted(){
        //getBoundingClientRect:获取元素位置   这里是获取进度条的位置宽
      this.progressLength=this.$refs.progress.getBoundingClientRect().width
      


      //音量   document.querySelector('.item-volume') 获取这个事件   add添加一个事件 click 让它执行 e当前的对象传过去
      document.querySelector('.item-volume').addEventListener('click',function(e){
        //找到class标注的  .volume   给它添加一个show-valume（在scss里面是显示音量的标签 从隐式变为显示）classList 该属性用于在元素中添加
        document.querySelector('.volume').classList.add('show-volume');
        //不在给父控件传递东西了 
        e.stopPropagation();
        //addEventListener 有三个参数  false是第3个
      },false);
      //将volume停止
      document.querySelector('.volume').addEventListener('click',function(e){
        e.stopPropagation();
      },false);
      document.addEventListener('click',function(){
        document.querySelector('.volume').classList.remove('show-volume');
      },false);
    },

    methods:{
        notify(title, type) {
            this.$notify({
                title: title,
                type: type
            })
        },
        togglePlay(){
            if(this.isPlay){
                this.$store.commit('setIsPlay',false)
            }else{
                this.$store.commit('setIsPlay',true)
            }
        },
        changeAside(){      //是否显示歌曲列表的按键 三
            this.$store.commit('setShowAside',true)
        },

        
        

        //定义一个方法解析传过来的时间  改成 时分秒
        formatSeconds(value){
             //将他变为一个整型的数值
            let theTime=parseInt(value);
            let result=''; //返回值
            let hour=parseInt(theTime / 3600); //1小时==3600秒
            let minute=parseInt((theTime / 60) % 60); //分钟
            let second=parseInt(theTime % 60);  //秒
            if(hour>0){
                if(hour<10){
                    result='0'+hour+":";
                }else{
                    result=hour+":";
                }
            }
                if(minute>0){
                    if(minute<10){
                        result+="0"+minute+":";
                    }else{
                        result+=minute+":";
                    }
                }
                //如果分钟不大于0 就不显示秒
                else{
                     result+="00:";
                }
                if(second>0){
                    if(second<10){
                        result+="0"+second;
                    }else{
                        result+=second;
                    }
                }else{
                     result+="00";
                }
                return result;
            
          
        },
        mousedown(e){
            //e.clientX mouseStartX 拖拽开始的地方= e所在的当前坐标
            this.mouseStartX=e.clientX;
            this.tag=true;
        },
        mouseup(){
            this.tag=false;
        },
        mousemove(e){
            if(!this.duration){
               return false;
            }
            if(this.tag){
                let movementX=e.clientX-this.mouseStartX; //拖拽终点减去 拖拽开始前的
                let curLength=this.$refs.curProgress.getBoundingClientRect().width;              //进度条的位置
                let newPercent=((movementX+curLength) / this.progressLength)*100;   //拖拽后的位置=上面2个相加  //本来算出来的结构是百分比 *100 才是我们想要的数据
                if(newPercent>100){   //判断是否拖拽
                    newPercent=100;
                }
                //curLength:进度条的位置
                this.curLength=newPercent;
                this.mouseStartX=e.clientX;  //开始坐标 改到 移动的地方
                this.changeTime(newPercent);
            }
        },

        changeTime(Percent){
            let newCurTime=(Percent*0.01)*this.duration;
            this.$store.commit('setChangeTime',newCurTime);
        },
        //点击进度条 切换播放位置
        updatemove(e){
            //为拖拽动作 才是点击
         if (!this.tag) {
            //去得控件的当前坐标
            let curLength = this.$refs.bg.offsetLeft;
            //获取点击移动的距离  移动的坐标 - 点击去的坐标 /progressLength 进度条的总共长度
            let newPercent=((e.clientX - curLength) / this.progressLength) *100;
            if (newPercent>100) {
                newPercent=100;
            }
            else if (newPercent<0) {
                newPercent=0;
            }
            //当前进度条的位置 = 点击移动后的位置
         this.curLength=newPercent;
         this.changeTime(newPercent)
         }
        },
       //上一首
        prev(){
            //listOfSong 歌曲列表   'listIndex',//当前歌曲在歌单中的位置
         if(this.listIndex !=-1&&this.listOfSong.length>1){     //当前处于不存在状态和 第一首就不执行
            if(this.listIndex>0){                                //大于第一首
                this.$store.commit('setListIndex',this.listIndex-1); //返回上一首
                
            }else{
                this.$store.commit('setListIndex',this.listOfSong.length-1)  //直接播放最后一首
            }
            this.toplay(this.listOfSong[this.listIndex].url)
         }
        },
        //下一首
        next(){
          if(this.listIndex !=-1&& this.listOfSong.length>1){
            if(this.listIndex<this.listOfSong.length-1){              //不是最后一首
                this.$store.commit('setListIndex',this.listIndex+1);   //直接播放下一首
            }else{
                this.$store.commit('setListIndex',0);  //最后一首
            }
            this.toplay(this.listOfSong[this.listIndex].url)
          }      
        },
        //播放   就是调用他的文件播放地址
        toplay: function(url) {
         if(url && url!=this.url) {//url 存在 并且 url不等于当前的url
             this.$store.commit('setId', this.listOfSong[this.listIndex].id);
             this.$store.commit('setUrl', this.$store.state.configure.HOST + url);
             this.$store.commit('setPicUrl', this.$store.state.configure.HOST + this.listOfSong[this.listIndex].pic);
             this.$store.commit('setArtist', this.replaceFName(this.listOfSong[this.listIndex].name));
             this.$store.commit('setTitle', this.replaceLName(this.listOfSong[this.listIndex].name));
             this.$store.commit('setLyric', this.parseLyric(this.listOfSong[this.listIndex].lyric));
             this.$store.commit('setIsActive',false);
             if(this.loginIn){
                getCollectOfUserId(this.userId)
                    .then(res=>{
                    for(let item of res){
                        if(item.songId==id){
                            this.$store.commit('setIsActive',true);
                            break;
                        }
                    }  
                    })
                }
            }
        },

        //解析歌词
        parseLyric(text) {
            let lines = text.split("\n") //按回车 解析分组(一个回车一个数组)
                //  /\正则表达式开始标志 \d{2}两个数字   (\d{3}|\d{2}) 三个或者二个数字
            let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g; //时间格式的正则表达式  将数值和文字分割开来
            let result = [];
            //对于歌词的格式不正确的直接返回
            if (!(/\[.+\]/.test(text))) {
                return [
                    [0, text]
                ]
            }
            //去掉第一行不正确的行   //slice[1]截取从开始索引到结束
            while (!pattern.test[lines[0]]) {
                lines = lines.slice(1);
            }
            //遍历每一行 ，形成 每一行带着两个元素的数组 第一行元素是时间 第二个是歌词
            for (let item of lines) {
                let time = item.match(pattern); //存在前面的数组
                let value = item.replace(pattern, ''); //把前面的时间替换成空 
                for (let item1 of time) { //时间也是一个数组  slice[1, -1] 是倒着数 ：为分割
                    let t = item1.slice[1, -1].slice(":") //取出时间 划分为数组
                    if (value != '') {
                        result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]).value])
                    }
                    //转换为整型 10进制 都转为小时算
                }
            }
            //  return a - b; //这里返回的是他们的差值，如果是大于0的值，就会将b排在前面，如果小于0,就会将a排在前面，如果是0的话，就随便。（冒泡排序法！！）

            result.sort(function(a, b) {
                return a[0] - b[0];
            });
            return result;

        },
        //search组件中 获取名字前半部分 歌手名
        replaceFName(str) {
            //- 隔开前面是0 后面是1
            let arr = str.split('-')
            return arr[0]
        },
        replaceLName(str) {
            //- 隔开前面是0 后面是1 歌曲
            let arr = str.split('-')
            return arr[1]
        },

        goLyric(){
            this.$router.push({path:`/lyric`});
        },
        //下载音乐的方法
        download(){
            download(this.url)
            .then(res=>{
                //把数据存入到content中
                let content =res.data;
                //模拟 创建a标签，用于跳转至下载链接  下面慢慢去修改设置
                let eleLink=document.createElement('a');
                //去下载 文件名为
                eleLink.download=`${this.artist}-${this.title}.mp3`;
               //隐藏起来下载操作
               eleLink.style.display='none';
               //把上面传来的字符串内容转换为blob地址
               let blob=new Blob([content]);
               //链接地址为
               eleLink.href=URL.createObjectURL(blob);
               //.appendChild() 方法从一个元素向另一个元素移动元素。
               document.body.appendChild(eleLink);
               eleLink.click();
               //然后移除掉
               document.body.removeChild(eleLink); 

            })
            .catch(err=>{
                console.log(err);
            })
        },
        colletion(){
            if(this.loginIn){
                let params=new URLSearchParams();
                params.append('userId',this.userId);
                params.append('type',0);
                params.append('songId',this.id);
                setCollect(params)
                   .then(res=>{
                   if(res.code==1){
                    this.$store.commit('setIsActive',true);
                    this.notify('收藏成功','success');
                   }else if(res.code==2){
                    this.notify('已经收藏','warning');
                   }else{
                    this.notify('收藏失败','error');
                   }
                   })

            }else{
                this.notify('请先登录','warning')
            }
        }
    },


    watch:{
       isPlay(){
        if(this.isPlay){
                this.$store.commit('setPlayButtonUrl','#icon-zanting')
            }else{
                this.$store.commit('setPlayButtonUrl','#icon-bofang')
            }
       },
       
       curTime(){
        this.nowTime=this.formatSeconds(this.curTime);  //歌曲播放的开始位置=curTime 当前音乐的播放位置
        this.songTime=this.formatSeconds(this.duration); //歌曲总时间 =音乐时长
        //当前时间 / 总时间 *100
        this.curLength=(this.curTime / this.duration)*100 
       },
       volume(){
            //音量的值传递过去  百分比
          this.$store.commit('setVolume',this.volume / 100);
        },

        autoNext(){  //监控autoNext  只要他有变化就变化
            this.next();    
        },

        
    },
    
   
    
    
}
</script>


<style lang="scss" scoped>
@import '../assets/css/play-bar.scss';
</style>