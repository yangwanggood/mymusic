import {get, post } from "./http";

// ====================歌手相关=========
//判断管理员是否登录成功
export const getLoginStatus = (params) => post(`admin/login/status`, params);

//查询歌手
export const getAllSinger = () => get(`singer/allSinger`);

//添加歌手
export const setSinger = (params) => post(`singer/add`, params);

//修改歌手
export const updateSinger = (params) => post(`singer/update`, params);

//删除歌手
export const delSinger = (id) => get(`singer/delete?id=${id}`);

// ====================歌曲相关=========
//根据歌手id查询歌曲
export const songOfSingerId = (id) => get(`song/singer/detail?singerId=${id}`);
//修改歌曲
export const updateSong = (params) => post(`song/update`, params);
//删除歌手
export const delSong = (id) => get(`song/delete?id=${id}`);

//根据歌曲id查询歌曲对象
export const songOfSongId = (id) => get(`song/detail?id=${id}`);
//根据歌曲名查询歌曲对象
export const songOfSongName = (songName) => get(`song/songOfSongName?songName=${songName}`);
//根据id名查询所有
export const allSong = () => get(`song/all`);

// ====================歌单相关=========
//查询歌单
export const getAllSongList = () => get(`songList/allSongList`);

//添加歌单
export const setSongList = (params) => post(`songList/add`, params);

//修改歌单
export const updateSongList = (params) => post(`songList/update`, params);

//删除歌单
export const delSongList = (id) => get(`songList/delete?id=${id}`);

//=====================歌单里面的歌曲=========
export const listSongIdtail = (songListId) => get(`listSong/listSongOfSongListId?songListId=${songListId}`);
//给歌单添加歌曲
export const listSongAdd = (params) => post(`listSong/add`, params);
//给歌单删除一条歌曲
export const delListSong = (id) => get(`listSong/delete?id=${id}`);


// ====================用户相关========
//查询用户
export const getAllConsumer = () => get(`consumer/allConsumer`);

//添加用户
export const setConsumer = (params) => post(`consumer/add`, params);

//修改用户
export const updateConsumer = (params) => post(`consumer/update`, params);

//删除用户
export const delConsumer = (id) => get(`consumer/delete?id=${id}`);
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`collect/CollectOfUserId?userId=${userId}`);
//指定用户的收藏列表
export const delCollectOfUserIdOfSongId = (userId, songId) => get(`collect/deleteSong?userId=${userId}&songId=${songId}`);