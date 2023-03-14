package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.ListSongMapper;
import com.javaclimb.music.domain.ListSong;
import com.javaclimb.music.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *歌单里面的歌曲列表
 */
@Service
public class ListSongServiceImpl implements ListSongService {
    @Autowired
    private ListSongMapper listSongMapper;

    /**
     * 增加
     *
     * @param listSong
     */
    @Override
    public boolean insert(ListSong listSong) {
        return listSongMapper.insert(listSong)>0;
    }



    /**
     * 删除
     *
     * @param Id
     */
    @Override
    public boolean delete(Integer Id) {
        return listSongMapper.delete(Id)>0;
    }

    /**
     * 根据id查询
     *
     * @param Id
     */
    @Override
    public ListSong selectByPrimaryKey(Integer Id) {
        return listSongMapper.selectByPrimaryKey(Id);
    }

    /**
     * 查询所有歌单歌曲
     */
    @Override
    public List<ListSong> allListSong() {
        return listSongMapper.allListSong();
    }

    /**
     * 查询所有歌单id 查询所有的歌曲
     *
     * @param songListId
     */
    @Override
    public List<ListSong> listSongOfSongListId(Integer songListId) {
        return listSongMapper.listSongOfSongListId(songListId);
    }
}
