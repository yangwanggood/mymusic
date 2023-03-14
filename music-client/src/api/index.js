import Axios from "axios";
import {get, post } from "./http";

// ====================歌手相关=========

//查询歌手
export const getAllSinger = () => get(`singer/allSinger`);
//根据性别查询歌手
export const getSingerOfSex = (type) => get(`singer/singerOfSex?sex=${type}`);
// ====================歌曲相关=========
//根据歌手id查询歌曲
export const songOfSingerId = (id) => get(`song/singer/detail?singerId=${id}`);

//根据歌曲id查询歌曲信息
export const songOfSongId = (id) => get(`song/detail?songId=${id}`);

//根据歌曲名字查询歌曲对象
export const likeSongOfSongName = (keywords) => get(`song/likeSongOfSongName?songName=${keywords}`);

//根据歌曲名查询歌曲对象
export const songOfSongName = (songName) => get(`song/songOfSongName?songName=${songName}`);
//根据id名查询所有
export const allSong = () => get(`song/all`);

// ====================歌单相关=========
//根据分格查询歌单内容
export const getSongStyleList = (style) => get(`songList/likeStyle?style=${style}`);
//根据歌单id查询歌曲列表
export const listSongIdtail = (songListId) => get(`listSong/listSongOfSongListId?songListId=${songListId}`);
//根据标题的查询 搜索 出歌单的信息
export const getlikeTitle = (keywords) => get(`songList/likeTitle?title=${keywords}`);
//查询歌单
export const getAllSongList = () => get(`songList/allSongList`);

// ====================用户相关========
//根据主键id查询用户信息
export const getUserId = (id) => get(`consumer/selectByPrimaryKye?id=${id}`);
//查询用户
export const getAllConsumer = () => get(`consumer/allConsumer`);
//添加用户
export const addUser = (params) => post(`consumer/add`, params);
// 修改用户信息
export const updateUserMsg = (params) => post(`consumer/update`, params);
//登录
export const loginIn = (params) => post(`consumer/login`, params)

// ====================评分相关=========

//新增评价
export const setRank = (params) => post(`rank/add`, params);
//平均分
export const getRankSongListId = (songListId) => get(`rank/detail?songListId=${songListId}`);
// ====================评论相关=========

//新增评论
export const setComment = (params) => post(`comment/add`, params);
//给评论点赞评论
export const setLike = (params) => post(`/comment/like`, params);
//跟据歌曲id或歌手id查询评论 评论类型（0歌曲1歌单）
export const getAllComment = (type, id) => {
        if (type == 0) {
            return get(`comment/CommentOfSongId?songId=${id}`);
        } else {
            return get(`comment/CommentOfSongListId?songListId=${id}`);
        }
    }
    //=====================收藏===============================
    //新增到收藏
export const setCollect = (params) => post(`/collect/add`, params)
    //指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`collect/CollectOfUserId?userId=${userId}`);

//下载音乐
export const download = (url) => Axios({
    method: 'get',
    url: url,
    //存入一个blob类型的 直接放入到本地
    responseType: 'blob'
});