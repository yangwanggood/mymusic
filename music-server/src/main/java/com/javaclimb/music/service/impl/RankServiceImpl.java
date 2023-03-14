package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.RankMapper;
import com.javaclimb.music.domain.Rank;
import com.javaclimb.music.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RankServiceImpl implements RankService {
    @Autowired
    private RankMapper rankMapper;
    /**
     * 增加
     *
     * @param rank
     */
    @Override
    public boolean insert(Rank rank) {
        return rankMapper.insert(rank)>0;
    }

    /**
     * 根据歌单ld查询总分
     *
     * @param songListId
     */
    @Override
    public int selectScoreSum(Integer songListId) {
        return rankMapper.selectScoreSum(songListId);
    }

    /**
     * 根据歌歌单ld查询评价人数
     *
     * @param songListId
     */
    @Override
    public int selectRankSum(Integer songListId) {
        return rankMapper.selectRankSum(songListId);
    }

    /**
     * 根据歌歌单ld查询平均分
     *
     * @param songListId
     */
    @Override
    public int selectSongListId(Integer songListId) {
        int Sum=rankMapper.selectRankSum(songListId);
        if(Sum==0){
            //评价 一共10分   没有人评价就5分
            return 5;
        }
        //总分数除以总人数
        return rankMapper.selectScoreSum(songListId)/Sum;
    }
}
