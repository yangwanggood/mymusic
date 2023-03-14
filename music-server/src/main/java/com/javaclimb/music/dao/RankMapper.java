package com.javaclimb.music.dao;


import com.javaclimb.music.domain.Rank;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface RankMapper {
    /**
     *增加
     */
    public int insert(Rank rank);

    /**
     *根据歌单ld查询总分
     */
    public int selectScoreSum(Integer songListId);
    /**
     *根据歌歌单ld查询评价人数
     */
    public int  selectRankSum(Integer songListId);

}
