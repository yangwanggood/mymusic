package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *Repository spirng管理 让service层调用
 */
@Repository
public interface CommentMapper {
    /**
     * 增加
     */
    public int insert(Comment comment);
    /**
     * 修改
     */
    public int update(Comment comment);
    /**
     * 删除
     */
    public int delete(Integer id);
    /**
     * 根据主键查询整个对象
     */
    public Comment selectByPrimaryKey(Integer id);
    /**
     * 查询所有歌手
     */
    public List<Comment> allComment();
    /**
     * 查询某个歌曲去所有的评论
     */
    public List<Comment> commentOfSongId(Integer songId);
    /**
     *查询某个歌单所有的评论
     */
    public List<Comment> commentOfSongListId(Integer songListId);
}
