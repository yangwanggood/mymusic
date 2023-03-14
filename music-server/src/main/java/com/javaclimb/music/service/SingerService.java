package com.javaclimb.music.service;

import com.javaclimb.music.domain.Singer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 歌手service接口
 */
public interface SingerService {
    /**
     * 增加
     */
    public boolean insert(Singer singer);
    /**
     * 修改
     */
    public boolean update(Singer singer);
    /**
     * 删除
     */
    public boolean  delete(Integer id);
    /**
     * 根据主键查询整个对象
     */
    public Singer selectByPrimaryKey(Integer id);
    /**
     * 查询所有歌手
     */
    public List<Singer> allSinger();
    /**
     * 根据歌曲的名字模糊查询列表
     */
    public List<Singer> singerOfName(String name);
    /**
     * 根据性别查询
     * sex 原本是 Byte 这里用Integer 向上转型
     */
    public List<Singer> SingerOfSex(Integer sex);
}
