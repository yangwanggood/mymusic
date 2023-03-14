package com.javaclimb.music.service;

import com.javaclimb.music.domain.Collect;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */

public interface CollectService {
    /**
     * 增加
     */
    public boolean insert(Collect collect);
    /**
     * 删除
     */
    public boolean delete(Integer id);
    /**
     * 删除收藏的歌曲  歌曲在song表
     */
    public boolean deleteIdOfUserId(Integer userId,Integer songId);
    /**
     * 查询所有的收藏
     */
    public List<Collect> allCollect();
    /**
     * 根据用户id查询
     */
    public List<Collect> selectByUserId(Integer userId);
    /**
     * 查询某个用户是否收藏了这个歌曲
     */
    public boolean existBySongId(@Param("userId") Integer userId, @Param("songId") Integer songId);

}
