package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.SingerMapper;
import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Singer的实习类
 */
@Service
public class SingerServiceImpl implements SingerService {
      @Autowired
    private SingerMapper singerMapper;
    /**
     * 增加
     *
     * @param singer
     */
    @Override
    public boolean insert(Singer singer) {
        return singerMapper.insert(singer)>0;
    }

    /**
     * 修改
     *
     * @param singer
     */
    @Override
    public boolean update(Singer singer) {
        return singerMapper.update(singer)>0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return singerMapper.delete(id)>0;
    }

    /**
     * 根据主键查询整个对象
     *
     * @param id
     */
    @Override
    public Singer selectByPrimaryKey(Integer id) {
        return singerMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有歌手
     */
    @Override
    public List<Singer> allSinger() {
        return singerMapper.allSinger();
    }

    /**
     * 根据歌手的名字模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Singer> singerOfName(String name) {
        return singerMapper.SingerOfName(name);
    }

    /**
     * 根据性别查询
     * sex 原本是 Byte 这里用Integer 向上转型
     *
     * @param sex
     */
    @Override
    public List<Singer> SingerOfSex(Integer sex) {
        return singerMapper.SingerOfSex(sex);
    }
}
