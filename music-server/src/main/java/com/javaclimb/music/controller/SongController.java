package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.service.SongService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
@RestController
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongService songService;
    /**
     * 添加歌曲
     */
    @RequestMapping(value ="/add",method = RequestMethod.POST)
    //MultipartFile 文件上传下载
    public Object addSong(HttpServletRequest request, @RequestParam("file")MultipartFile mpFile){
        JSONObject jsonObject = new JSONObject();
        String singerId=request.getParameter("singerId").trim();          //歌手id
        String name=request.getParameter("name").trim();                  //歌名
        String introduction=request.getParameter("introduction").trim();  //简介
        String pic="/img/songPic/dd.png";                                    //默认图片
        String lyric=request.getParameter("lyric").trim();                //歌词
        if (mpFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"歌曲上传失败");
            return jsonObject;
        }
        //System.currentTimeMillis() 当前时间到毫秒，avatorFile.getOriginalFilename(); 原文件名
        //System.currentTimeMillis()+avatorFile.getOriginalFilename(); ：作用防止文件名相同而被覆盖
        String fileName= System.currentTimeMillis()+mpFile.getOriginalFilename();
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"song";

        File file1=new File(filePath);
        //判断文件是否存在 如果不存在的话 就创建一个
        if (!file1.exists()){
            file1.mkdir();
        }
        //实际的文件地址  文件地址+/+名字
        File dest=new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeUrlPath="/song/"+fileName;
        //上传过程 io异常  磁盘满了
        try {
            mpFile.transferTo(dest);
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setPic(pic);
            song.setLyric(lyric);
            song.setUrl(storeUrlPath);

            boolean flag = songService.insert(song);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"保存成功");
                jsonObject.put("avator",storeUrlPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }
    /**
     *根据歌手id查询歌曲
     */
    @RequestMapping(value = "/singer/detail",method = RequestMethod.GET)
    public Object songOfSingerId(HttpServletRequest request){
        String singerId=request.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }


    /**
     *修改歌手
     */
    //HttpServletRequest 从前端传输过来
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();  //返回值
        String id = request.getParameter("id").trim();               //主键
        String name = request.getParameter("name").trim();          //姓名  //获取前端传过来的 name    trim()：去掉空格
        String introduction = request.getParameter("introduction").trim(); //简介
        String lyric= request.getParameter("lyric").trim();
        //birth 在数据库中是datetime 需要做一次转换 转成Date
        //保存到歌手的对象中
        Song song = new Song();
        song.setId(Integer.parseInt(id));
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        Boolean flag = songService.update(song);
        if (flag){ //如果保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");
        return jsonObject;

    }
    /**
     *删除歌手歌曲
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    //-TODO 先查询到数据库中对应的文件地址 删除掉它在进行下面的代码
    public Object deleteSong(HttpServletRequest request){
        String id = request.getParameter("id").trim();               //主键
        Boolean flag = songService.delete(Integer.parseInt(id));
        return flag;
    }
    /**
     *更新歌手图片
     */
    @RequestMapping(value = "/updateSongPic",method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id) {
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败失败");
            return jsonObject;
        }
        //System.currentTimeMillis() 当前时间到毫秒，avatorFile.getOriginalFilename(); 原文件名
        //System.currentTimeMillis()+avatorFile.getOriginalFilename(); ：作用防止文件名相同而被覆盖
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "songPic";
        File file1 = new File(filePath);
        //判断文件是否存在 如果不存在的话 就创建一个
        if (!file1.exists()) {
            file1.mkdir();
        }
        //实际的文件地址  文件地址+/+名字
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/songPic/" + fileName;
        //上传过程 io异常  磁盘满了
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            boolean flag = songService.update(song);
            if (flag) {
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "上传成功");
                jsonObject.put("pic", storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");

            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "保存失败" + e.getMessage());
        } finally {
            return jsonObject;
        }
    }

    /**
     *更新歌曲
     */
    @RequestMapping(value = "/updateSongUrl",method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id) {
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败失败");
            return jsonObject;
        }
        //System.currentTimeMillis() 当前时间到毫秒，avatorFile.getOriginalFilename(); 原文件名
        //System.currentTimeMillis()+avatorFile.getOriginalFilename(); ：作用防止文件名相同而被覆盖
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file1 = new File(filePath);
        //判断文件是否存在 如果不存在的话 就创建一个
        if (!file1.exists()) {
            file1.mkdir();
        }
        //实际的文件地址  文件地址+/+名字
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/song/" + fileName;
        //上传过程 io异常  磁盘满了
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storeAvatorPath);
            boolean flag = songService.update(song);
            if (flag) {
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "上传成功");
                jsonObject.put("avator", storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");

            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "保存失败" + e.getMessage());
        } finally {
            return jsonObject;
        }
    }
    /**
     *根据歌曲id查询歌曲对象
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String songId=request.getParameter("id");
        return songService.selectByPrimaryKey(Integer.parseInt(songId));
    }
    /**
     *根据歌曲name查询歌曲对象
     */
    @RequestMapping(value = "/songOfSongName",method = RequestMethod.GET)
    public Object songOfSongName(HttpServletRequest request){
        String songName=request.getParameter("songName");
        return songService.songOfName(songName);
    }

    /**
     *根据歌曲name查询模糊歌曲对象
     */
    @RequestMapping(value = "/likeSongOfSongName",method = RequestMethod.GET)
    public Object likeSongOfSongName(HttpServletRequest request){
        String songName=request.getParameter("songName");
        System.out.println(songName);
        return songService.likeSongOfName(songName);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public Object allSong(HttpServletRequest request){
        return songService.allSong();
    }
}
