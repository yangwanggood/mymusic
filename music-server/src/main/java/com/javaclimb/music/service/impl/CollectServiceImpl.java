package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.CollectMapper;
import com.javaclimb.music.domain.Collect;

import com.javaclimb.music.service.CollectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    /**
     * 增加
     *
     * @param collect
     */
    @Override
    public boolean insert(Collect collect) {
        return collectMapper.insert(collect)>0;
    }

    /**
     * 删除
     *
     * @param
     */
    @Override
    public boolean delete(Integer id) {
        return collectMapper.delete(id)>0;
    }
    /**
     *
     *
     *
     */
    @Override
    public boolean deleteIdOfUserId(Integer userId,Integer songId) {
        return collectMapper.deleteIdOfUserId(userId,songId)>0;
    }

    /**
     * 查询所有的收藏
     */
    @Override
    public List<Collect> allCollect() {
        return  collectMapper.allCollect();
    }

    /**
     * 根据用户id查询
     *
     * @param userId
     */
    @Override
    public List<Collect> selectByUserId(Integer userId) {
        return collectMapper.selectByUserId(userId);
    }

    /**
     * 查询某个用户是否收藏了这个歌曲
     *
     * @param userId
     * @param songId
     */
    @Override
    public boolean existBySongId(Integer userId, Integer songId) {
        return collectMapper.existBySongId(userId,songId)>0;
    }
}
