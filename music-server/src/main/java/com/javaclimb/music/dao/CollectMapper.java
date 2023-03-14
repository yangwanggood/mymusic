package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Collect;
import com.javaclimb.music.domain.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *Repository spirng管理 让service层调用
 */
@Repository
public interface CollectMapper {
    /**
     * 增加
     */
    public int insert(Collect collect);
    /**
     * 删除
     */
    public int delete(Integer id);
    /**
     * 删除收藏的歌曲  歌曲在song表
     */
    public int deleteIdOfUserId(@Param("userId") Integer userId,@Param("songId") Integer songId);


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
    public int existBySongId(@Param("userId") Integer userId,@Param("songId") Integer songId);

}
