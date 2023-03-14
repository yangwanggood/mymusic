
<!-- 播放器 最后 一个 三  点击显示歌曲播放列表 -->
<template>
    <transition name="slide-fade">
<!-- transition 只有 name 这一个属性，且只有一个子元素，这个子元素需要 v-if 或 v-show 来控制是否显示 -->
    <div class="the-aside" v-if="showAside">
     <h2 class="title">播放列表</h2>
     <ul class="menus">
        <!-- 遍历处所有后台 -->
        <li v-for="(item,index) in listOfSong" :key="index" :class="{'is-play': id==item.id}" 
        @click="toplay(item.id, item.url, item.pic, item.index, item.name, item.lyric)">
        {{replaceLName(item.name)}}
        </li>
     </ul>
    </div>
</transition>
</template>

<script>
import {getCollectOfUserId} from '../api/index' 
import {mapGetters} from 'vuex'
export default {
    name: 'the-aside',
    data() {
        return {
          
        }
    },
 
    computed:{
        ...mapGetters([
            'showAside',   //是否显示歌单播放列表
            'listOfSong' ,  //当前歌曲内容
            'id',
            'loginIn',  //是否登录
            'userId',    //当前登录用户id
            'isActive',  //当前播放的歌曲是否已收藏       
        ])
    },
    mounted(){
        let _this=this;
        //在document 中 的this 不能用 要赋个值
        document.addEventListener('click',function(){
            _this.$store.commit('setShowAside',false)
        },true);
    },
    methods:{
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

        //播放   就是调用他的文件播放地址
        toplay: function(id, url, pic, index, name, lyric) {
            this.$store.commit('setId', id);
            this.$store.commit('setUrl', this.$store.state.configure.HOST + url);
            this.$store.commit('setPicUrl', this.$store.state.configure.HOST + pic);
            this.$store.commit('setListIndex', index);
            this.$store.commit('setArtist', this.replaceFName(name));
            this.$store.commit('setTitle', this.replaceLName(name));
            this.$store.commit('setLyric',this.parseLyric(lyric));
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

        }
    }
    
 
}
</script>

<style lang="scss" scoped>
@import '../assets/css/the-aside.scss'
</style>