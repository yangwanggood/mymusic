//歌手相关

//放基础的

//用户相关
//里面可以放很多的缓存数据    1：放图片地址


const song = ({
    state: {

        listOfSong: [], //当前歌曲列表
        isPlay: false, //是否播放中
        url: '', //歌曲地址
        id: '', //歌曲id
        playButtonUrl: '#icon-bofang', //播放状态的图标
        duration: 0, //音乐时长
        curTime: 0, //当前音乐的播放位置
        changeTime: 0, //指定播放时刻
        title: '', //歌名
        artist: '', //歌手名
        picUrl: '', //歌曲图片
        autoNext: true, //用于自动触发播放下一首
        lyric: [], //未处理的歌词数据
        tempList: {}, //单个歌单信息或歌手信息
        listIndex: null, //当前歌曲在歌单中的位置
        volume: 50 //音量
    },
    //取值
    getters: {
        listOfSong: state => {
            let listOfSong = state.listOfSong
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!listOfSong.length) {
                listOfSong = JSON.parse(window.sessionStorage.getItem('listOfSong') || null)
            }
            return listOfSong
        },
        isPlay: state => {
            let isPlay = state.isPlay
            if (!isPlay) {
                isPlay = JSON.parse(window.sessionStorage.getItem('isPlay') || null)
            }
            return isPlay
        },
        url: state => {
            let url = state.url
            if (!url) {
                url = JSON.parse(window.sessionStorage.getItem('url') || null)
            }
            return url
        },
        id: state => {
            let id = state.id
            if (!id) {
                id = JSON.parse(window.sessionStorage.getItem('id') || null)
            }
            return id
        },
        playButtonUrl: state => {
            let playButtonUrl = state.playButtonUrl
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!playButtonUrl) {
                playButtonUrl = JSON.parse(window.sessionStorage.getItem('playButtonUrl') || null)
            }
            return playButtonUrl
        },
        duration: state => {
            let duration = state.duration
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!duration) {
                duration = JSON.parse(window.sessionStorage.getItem('duration') || null)
            }
            return duration
        },
        curTime: state => {
            let curTime = state.curTime
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!curTime) {
                curTime = JSON.parse(window.sessionStorage.getItem('curTime') || null)
            }
            return curTime
        },
        changeTime: state => {
            let changeTime = state.changeTime
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!changeTime) {
                changeTime = JSON.parse(window.sessionStorage.getItem('changeTime') || null)
            }
            return changeTime
        },
        playButtonUrl: state => {
            let playButtonUrl = state.playButtonUrl
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!playButtonUrl) {
                playButtonUrl = JSON.parse(window.sessionStorage.getItem('playButtonUrl') || null)
            }
            return playButtonUrl
        },
        title: state => {
            let title = state.title
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!title) {
                title = JSON.parse(window.sessionStorage.getItem('title') || null)
            }
            return title
        },
        artist: state => {
            let artist = state.artist
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!artist) {
                artist = JSON.parse(window.sessionStorage.getItem('artist') || null)
            }
            return artist
        },
        picUrl: state => {
            let picUrl = state.picUrl
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!picUrl) {
                picUrl = JSON.parse(window.sessionStorage.getItem('picUrl') || null)
            }
            return picUrl
        },
        autoNext: state => {
            let autoNext = state.autoNext
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!autoNext) {
                autoNext = JSON.parse(window.sessionStorage.getItem('autoNext') || null)
            }
            return autoNext
        },
        lyric: state => {
            let lyric = state.lyric
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!lyric) {
                lyric = JSON.parse(window.sessionStorage.getItem('lyric') || null)
            }
            return lyric
        },
        tempList: state => {
            let tempList = state.tempList
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!tempList) {
                tempList = JSON.parse(window.sessionStorage.getItem('tempList') || null)
            }
            return tempList
        },
        listIndex: state => {
            let listIndex = state.listIndex
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!listIndex) {
                listIndex = JSON.parse(window.sessionStorage.getItem('listIndex') || null)
            }
            return listIndex
        },
        volume: state => {
            let volume = state.volume
                //如果activeName 没有的话 就去浏览器中拿  获取到给activeName
            if (!volume) {
                volume = JSON.parse(window.sessionStorage.getItem('volume') || null)
            }
            return volume
        },


    },
    //设置值放到浏览器的
    mutations: {
        setListOfSong: (state, listOfSong) => {
            state.listOfSong = listOfSong
                //放到浏览器的
            window.sessionStorage.setItem('listOfSong', JSON.stringify(listOfSong))
        },
        setIsPlay: (state, isPlay) => {
            state.isPlay = isPlay;
            window.sessionStorage.setItem('isPlay', JSON.stringify(isPlay))
        },
        setUrl: (state, url) => {
            state.url = url;
            window.sessionStorage.setItem('url', JSON.stringify(url))
        },
        setId: (state, id) => {
            state.id = id;
            window.sessionStorage.setItem('id', JSON.stringify(id))
        },
        setPlayButtonUrl: (state, playButtonUrl) => {
            state.playButtonUrl = playButtonUrl;
            window.sessionStorage.setItem('playButtonUrl', JSON.stringify(playButtonUrl))
        },
        setDuration: (state, duration) => {
            state.duration = duration;
            window.sessionStorage.setItem('duration', JSON.stringify(duration))
        },
        setCurTime: (state, curTime) => {
            state.curTime = curTime;
            window.sessionStorage.setItem('curTime', JSON.stringify(curTime))
        },
        setChangeTime: (state, changeTime) => {
            state.changeTime = changeTime;
            window.sessionStorage.setItem('changeTime', JSON.stringify(changeTime))
        },
        setTitle: (state, title) => {
            state.title = title;
            window.sessionStorage.setItem('title', JSON.stringify(title))
        },
        setArtist: (state, artist) => {
            state.artist = artist;
            window.sessionStorage.setItem('artist', JSON.stringify(artist))
        },
        setAutoNext: (state, autoNext) => {
            state.autoNext = autoNext;
            window.sessionStorage.setItem('autoNext', JSON.stringify(autoNext))
        },
        setPicUrl: (state, picUrl) => {
            state.picUrl = picUrl;
            window.sessionStorage.setItem('picUrl', JSON.stringify(picUrl))
        },
        setLyric: (state, lyric) => {
            state.lyric = lyric;
            window.sessionStorage.setItem('lyric', JSON.stringify(lyric))
        },
        setTempList: (state, tempList) => {
            state.tempList = tempList;
            window.sessionStorage.setItem('tempList', JSON.stringify(tempList))
        },
        setListIndex: (state, listIndex) => {
            state.listIndex = listIndex;
            window.sessionStorage.setItem('listIndex', JSON.stringify(listIndex))
        },
        setVolume: (state, volume) => {
            state.volume = volume;
            window.sessionStorage.setItem('volume', JSON.stringify(volume))
        }
    }
})

export default song