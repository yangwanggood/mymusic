package com.javaclimb.music.service;

import com.javaclimb.music.domain.ListSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */

public interface ListSongService {
    /**
     *增加
     */
    public boolean insert(ListSong listSong);
    /**

    /**
     *删除
     */
    public boolean delete(Integer Id);
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
