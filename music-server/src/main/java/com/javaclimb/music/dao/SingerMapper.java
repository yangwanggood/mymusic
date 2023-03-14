package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *Repository spirng管理 让service层调用
 */
@Repository
public interface SingerMapper {
    /**
     * 增加
     */
    public int insert(Singer singer);
    /**
     * 修改
     */
    public int update(Singer singer);
    /**
     * 删除
     */
    public int delete(Integer id);
    /**
     * 根据主键查询整个对象
     */
    public Singer selectByPrimaryKey(Integer id);
    /**
     * 查询所有歌手
     */
    public List<Singer> allSinger();
    /**
     * 根据歌手的名字模糊查询列表
     */
    public List<Singer> SingerOfName(String name);
    /**
     * 根据性别查询
     * sex 原本是 Byte 这里用Integer 向上转型
     */
    public List<Singer> SingerOfSex(Integer sex);
}
