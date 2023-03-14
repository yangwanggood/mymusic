package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.domain.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *Repository spirng管理 让service层调用
 */
@Repository
public interface SongMapper {
    /**
     * 增加
     */
    public int insert(Song song);
    /**
     * 修改
     */
    public int update(Song song);
    /**
     * 删除
     */
    public int delete(Integer id);
    /**
     * 根据主键查询整个对象
     */
    public Song selectByPrimaryKey(Integer id);
    /**
     * 查询所有歌手
     */
    public List<Song> allSong();
    /**
     * 根据歌手的名字模精确询列表
     */
    public List<Song> songOfName(String name);

    /**
     * 根据歌手的名字模糊询列表
     */
    public List<Song> likeSongOfName(String name);

    /**
     * 根据性别查询
     * sex 原本是 Byte 这里用Integer 向上转型
     */
    public List<Song> songOfSingerId(Integer singerId);
}
