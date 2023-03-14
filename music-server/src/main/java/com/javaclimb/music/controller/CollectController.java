package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Collect;
import com.javaclimb.music.service.CollectService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 收藏控制类
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 添加收藏
     */
    //HttpServletRequest 从前端传输过来
    //method = RequestMethod.POST ：add的访问方式为RequestMethod.POST
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addCollect(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();  //返回值
        String userId = request.getParameter("userId");       //用户id  //获取前端传过来的 name    trim()：去掉空格
        String type = request.getParameter("type");            //收藏类型（0歌曲1歌单）
        String songId = request.getParameter("songId");            //歌曲id
       if (songId==null&songId.equals("")){
           jsonObject.put(Consts.CODE,0);
           jsonObject.put(Consts.MSG,"收藏歌曲为空");
           return jsonObject;
       }
       if (collectService.existBySongId(Integer.parseInt(userId),Integer.parseInt(songId))){
           jsonObject.put(Consts.CODE,2);
           jsonObject.put(Consts.MSG,"收藏成功");
           return jsonObject;
       }


        //保存到收藏的对象中
        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(userId));
        //是要一个String 而sex是byte 需要强转型
        collect.setType(new Byte(type));
        collect.setSongId(Integer.parseInt(songId));
        //收藏类型（0歌曲1歌单）
        Boolean flag = collectService.insert(collect);
        if (flag){ //如果保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"收藏成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"收藏失败");
        return jsonObject;

    }

    /**
     * 删除收藏
     */

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteCollect(HttpServletRequest request){
        String id = request.getParameter("id").trim();               //主键
        Boolean flag = collectService.delete(Integer.parseInt(id));
        return flag;
    }
    /**
     * 删除收藏的歌曲
     */

    @RequestMapping(value = "/deleteSong",method = RequestMethod.GET)
    public Object deleteIdOfUserId(HttpServletRequest request){
        String userId = request.getParameter("userId");            //主键
        String songId = request.getParameter("songId");           //主键
        Boolean flag = collectService.deleteIdOfUserId(Integer.parseInt(userId),Integer.parseInt(songId));
        return flag;
    }

    /**
     * 查询所有的收藏
     */
    @RequestMapping(value = "/allCollect",method = RequestMethod.GET)
    public Object allCollect(HttpServletRequest request){
        return collectService.allCollect();
    }


    /**
     * 根据用户id查询收藏
     */
    @RequestMapping(value = "/CollectOfUserId",method = RequestMethod.GET)
    public Object CollectOfUserId(HttpServletRequest request){
        String userId = request.getParameter("userId"); //歌曲id
        return collectService.selectByUserId(Integer.parseInt(userId));
    }




}
