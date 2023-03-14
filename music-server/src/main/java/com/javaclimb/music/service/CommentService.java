package com.javaclimb.music.service;

import com.javaclimb.music.domain.Comment;

import java.util.List;

/**
 * 评论 接口
 */

public interface CommentService {
    /**
     * 增加
     */
    public boolean insert(Comment comment);
    /**
     * 修改
     */
    public boolean update(Comment comment);
    /**
     * 删除
     */
    public boolean delete(Integer id);
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
