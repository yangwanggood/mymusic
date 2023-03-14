package com.javaclimb.music.dao;

import com.javaclimb.music.domain.ListSong;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository spirng管理 让service层调用
 */
@Repository
public interface ListSongMapper {
    /**
     *增加
     */
    public int insert(ListSong listSong);
    /**
     *修改
     */

    /**
     *删除
     */
    public int delete(Integer Id);
    /**
     *根据id查询所有
     */
    public ListSong selectByPrimaryKey(Integer Id);
    /**
     *查询所有歌单歌曲
     */
    public List<ListSong> allListSong();
    /**
     *查询所有歌单id 查询所有的歌曲
     */
    public List<ListSong> listSongOfSongListId(Integer songListId);
}
