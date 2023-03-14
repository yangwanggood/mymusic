package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.javaclimb.music.domain.SongList;
import com.javaclimb.music.service.SongListService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 *
 */
@RestController
@RequestMapping("/songList")
public class SongListController {
    @Autowired
    private SongListService songListService;

    /**
     *添加歌单
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object insertSongList(HttpServletRequest request, HttpSession session){
    JSONObject jsonObject = new JSONObject();
    String title = request.getParameter("title").trim();                //标题
    String pic = request.getParameter("pic").trim();                    //图片
    String introduction = request.getParameter("introduction").trim();  //简介
    String style = request.getParameter("style").trim();                //风格

    SongList songList = new SongList();
    songList.setTitle(title);
    songList.setPic(pic);
    songList.setIntroduction(introduction);
    songList.setStyle(style);
    boolean flag =songListService.insert(songList);
    if (flag){
        jsonObject.put(Consts.CODE,1);
        jsonObject.put(Consts.MSG,"添加成功");
        return jsonObject;
    }
    jsonObject.put(Consts.CODE,0);
    jsonObject.put(Consts.MSG,"添加失败");
    return jsonObject;
}
    /**
     * 修改歌单
     */
     @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSongList(HttpServletRequest request,HttpSession session){
         JSONObject jsonObject = new JSONObject();
         String id = request.getParameter("id").trim();
         String title = request.getParameter("title").trim();                //标题
         String introduction = request.getParameter("introduction").trim();  //简介
         String style = request.getParameter("style").trim();                //风格
         SongList songList = new SongList();
         songList.setId(Integer.parseInt(id));
         songList.setTitle(title);
         songList.setStyle(style);
         songList.setIntroduction(introduction);
         boolean flag=songListService.update(songList);
             if (flag){
                 jsonObject.put(Consts.CODE,1);
                 jsonObject.put(Consts.MSG,"修改成功");
                 return jsonObject;
             }
             jsonObject.put(Consts.CODE,0);
             jsonObject.put(Consts.MSG,"修改失败");
             return jsonObject;
     }

    /**
     *删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSongList(HttpServletRequest request){
        String id = request.getParameter("id");
        boolean flag=songListService.delete(Integer.parseInt(id));
        return flag;
    }
    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKeySongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id");
       return songListService.selectByPrimaryKey(Integer.parseInt(id));
    }
    /**
     *  查询所有歌单
     */
    @RequestMapping(value = "/allSongList",method = RequestMethod.GET)
    public Object allSongListSongList(HttpServletRequest request){
        return songListService.allSongList();
    }
    /**
     * 根据标题精确查询歌单列表
     */
    @RequestMapping(value = "/songListOfTitle",method = RequestMethod.GET)
    public Object songListOfTitleSongList(HttpServletRequest request){
        String title = request.getParameter("title");
        return songListService.songListOfTitle(title);
    }
    /**
     * 根据标题模糊查询歌单列表
     */
    @RequestMapping(value = "/likeTitle",method = RequestMethod.GET)
    public Object likeTitleSongList(HttpServletRequest request){
        String title = request.getParameter("title");
        return songListService.likeTitle(title);
    }
    /**
     * 根据风格模糊查询歌单列表
     */
    @RequestMapping(value = "/likeStyle",method = RequestMethod.GET)
    public Object likeStyleSongList(HttpServletRequest request){
        String style = request.getParameter("style");
        return songListService.likeStyle(style);
    }


    /**
     * 更新图片
     */
    @RequestMapping(value = "/updateSongListPic",method = RequestMethod.POST)
                                    //  @RequestParam:标注在方法的参数上，用于获取url上传递的参数
    public Object updateSongListPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"图片上传失败");
            return jsonObject;
        }
        //文件名 =创建毫秒数+文件名
        String fileName=System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //文件路劲
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+
                System.getProperty("file.separator")+"songListPic";
        //如果文件不存在 则创建一个
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        //文件的实际地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存贮到数据库的相对文件地址
      String storeAvatorPath ="/img/songListPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic(storeAvatorPath);
            boolean flag=songListService.update(songList);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"图片修改成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"图片修改失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"图片修改失败"+e.getMessage());
        }finally {
            return  jsonObject;
        }

    }
}
