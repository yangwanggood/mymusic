package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.ListSong;
import com.javaclimb.music.domain.Rank;
import com.javaclimb.music.service.ListSongService;
import com.javaclimb.music.service.RankService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@RestController
@RequestMapping("/rank")
public class RankController {
    @Autowired
    private RankService rankService;

    /**
     *添加歌手
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addrank(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String songListId = request.getParameter("songListId");  //歌曲id
        String consumerId = request.getParameter("consumerId"); //用户id
        String score = request.getParameter("score"); //评价
        Rank rank = new Rank();
        rank.setSongListId(Integer.parseInt(songListId));
        rank.setConsumerId(Integer.parseInt(consumerId));
        rank.setScore(Integer.parseInt(score));
        boolean flag=rankService.insert(rank);
        if (flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评价成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评价失败");
        return jsonObject;
    }


    /**
     *计算平均分
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object selectSongListId(HttpServletRequest request) {
        String songListId =request.getParameter("songListId");
         return rankService.selectSongListId(Integer.parseInt(songListId));

    }
}
