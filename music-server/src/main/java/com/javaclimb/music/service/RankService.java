package com.javaclimb.music.service;

import com.javaclimb.music.domain.Rank;

/**
 *
 */

public interface RankService {
    /**
     *增加
     */
    public boolean insert(Rank rank);

    /**
     *根据歌单ld查询总分
     */
    public int selectScoreSum(Integer songListId);
    /**
     *根据歌歌单ld查询评价人数
     */
    public int  selectRankSum(Integer songListId);
    /**
     *根据歌歌单ld查询平均分
     */
    public int  selectSongListId(Integer songListId);
}
