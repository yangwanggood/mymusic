package com.javaclimb.music.service;

import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.domain.Song;

import java.util.List;

/**
 * 歌曲service接口
 */
public interface SongService {
    /**
     * 增加
     */
    public boolean insert(Song song);
    /**
     * 修改
     */
    public boolean update(Song song);
    /**
     * 删除
     */
    public boolean delete(Integer id);
    /**
     * 根据主键查询整个对象
     */
    public Song selectByPrimaryKey(Integer id);
    /**
     * 查询所有歌手
     */
    public List<Song> allSong();
    /**
     * 根据歌手的名字精确查询列表
     */
    public List<Song> songOfName(String name);

    /**
     * 根据歌手的名字模糊查询列表
     */
    public List<Song> likeSongOfName(String name);
    /**
     * 根据性别查询
     * sex 原本是 Byte 这里用Integer 向上转型
     */
    public List<Song> songOfSingerId(Integer singerId);
}
