import { likeSongOfSongName, getCollectOfUserId } from "../api/index";
import { mapGetters } from 'vuex'
export const mixin = {
    computed: {
        ...mapGetters([
            'loginIn', //是否登录
            'userId', //当前登录用户id

        ])
    },
    methods: {
        notify(title, type) {
            this.$notify({
                title: title,
                type: type
            })
        },
        //获取图片地址
        attchImageUrl(srcUrl) {
            return srcUrl ? this.$store.state.configure.HOST + srcUrl : this.$store.state.configure.HOST + '/img/qq.jpg';
            //return srcUrl ? this.$store.state.configure.HOST + srcUrl : '../assets/img/user.jpg';
        },
        //根据歌手名字模糊查询歌曲
        getSong() {
            if (!this.$route.query.keywords) {
                this.$store.commit('setListOfSong', [])
                    //warning 警告
                this.notify('你输入的内容为空', 'warning');
            } else {
                likeSongOfSongName(this.$route.query.keywords).then(res => {

                    if (!res.length) {
                        this.$store.commit('setListOfSong', [])
                        this.notify('系统暂无符合条件的歌曲', 'warning');
                    } else {
                        this.$store.commit('setListOfSong', res)
                    }
                }).catch(err => {
                    console.log(err);
                })
            }
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

        //播放   就是调用他的文件播放地址
        toplay: function(id, url, pic, index, name, lyric) {
            this.$store.commit('setId', id);
            this.$store.commit('setUrl', this.$store.state.configure.HOST + url);
            this.$store.commit('setPicUrl', this.$store.state.configure.HOST + pic);
            this.$store.commit('setListIndex', index);
            this.$store.commit('setArtist', this.replaceFName(name));
            this.$store.commit('setTitle', this.replaceLName(name));
            //this.$store.commit('setLyric', this.parseLyric(lyric));
            this.$store.commit('setIsActive', false);
            if (this.loginIn) {
                getCollectOfUserId(this.userId)
                    .then(res => {
                        for (let item of res) {
                            if (item.songId == id) {
                                this.$store.commit('setIsActive', true);
                                break;
                            }
                        }
                    })
            }
        },

        //获取生日
        attachBirth(val) {
            return val.substr(0, 10)
        }
        // //解析歌词
        // parseLyric(text) {
        //     let lines = text.split("\n") //按回车 解析分组(一个回车一个数组)
        //         //  /\正则表达式开始标志 \d{2}两个数字   (\d{3}|\d{2}) 三个或者二个数字
        //     let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g; //时间格式的正则表达式  将数值和文字分割开来
        //     let result = [];
        //     //对于歌词的格式不正确的直接返回
        //     if (!(/\[.+\]/.test(text))) {
        //         return [
        //             [0, text]
        //         ]


        //     }
        //     //去掉第一行不正确的行   //slice[1]截取从开始索引到结束
        //     while (!pattern.test[lines[0]]) {
        //         lines = lines.slice(1);
        //     }
        //     //遍历每一行 ，形成 每一行带着两个元素的数组 第一行元素是时间 第二个是歌词
        //     for (let item of lines) {
        //         let time = item.match(pattern); //存在前面的数组
        //         let value = item.replace(pattern, ''); //把前面的时间替换成空 
        //         for (let item1 of time) { //时间也是一个数组  slice[1, -1] 是倒着数 ：为分割
        //             let t = item1.slice[1, -1].slice(":") //取出时间 划分为数组
        //             if (value != '') {
        //                 result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]).value])
        //             }
        //             //转换为整型 10进制 都转为小时算
        //         }
        //     }
        //     //  return a - b; //这里返回的是他们的差值，如果是大于0的值，就会将b排在前面，如果小于0,就会将a排在前面，如果是0的话，就随便。（冒泡排序法！！）

        //     result.sort(function(a, b) {
        //         return a[0] - b[0];
        //     });
        //     return result;

        // }]



        //[15:59.02]南山南
    },
}