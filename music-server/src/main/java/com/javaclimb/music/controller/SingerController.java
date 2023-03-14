package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.service.SingerService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 歌手控制类
 */
@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private SingerService singerService;

    /**
     * 添加歌手
     */
    //HttpServletRequest 从前端传输过来
    //method = RequestMethod.POST ：add的访问方式为RequestMethod.POST
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();  //返回值
        String name = request.getParameter("name").trim();       //姓名  //获取前端传过来的 name    trim()：去掉空格
        String sex = request.getParameter("sex").trim();            //性别
        String pic = request.getParameter("pic").trim();            //头像
        String birth = request.getParameter("birth").trim();        //生日
        String location = request.getParameter("location").trim();  //地区
        String introduction = request.getParameter("introduction").trim(); //简介

        //birth 在数据库中是datetime 需要做一次转换 转成Date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate=dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //保存到歌手的对象中
        Singer singer = new Singer();
        singer.setName(name);
        //是要一个String 而sex是byte 需要强转型
        singer.setSex(new Byte(sex));
        singer.setPic(pic);
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        Boolean flag = singerService.insert(singer);
        if (flag){ //如果保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加失败");
        return jsonObject;

    }
    /**
     * 修改歌手
     */
    //HttpServletRequest 从前端传输过来
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();  //返回值
        String id = request.getParameter("id").trim();               //主键
        String name = request.getParameter("name").trim();          //姓名  //获取前端传过来的 name    trim()：去掉空格
        String sex = request.getParameter("sex").trim();            //性别
        String birth = request.getParameter("birth").trim();        //生日
        String location = request.getParameter("location").trim();  //地区
        String introduction = request.getParameter("introduction").trim(); //简介
        //birth 在数据库中是datetime 需要做一次转换 转成Date
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate=dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //保存到歌手的对象中
        Singer singer = new Singer();
        singer.setId(Integer.parseInt(id));
        singer.setName(name);
        //这是都是 String 而sex是byte 需要强转型
        singer.setSex(new Byte(sex));
        singer.setBirth(birthDate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        Boolean flag = singerService.update(singer);
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
     * 删除歌手
     */
   /* //HttpServletRequest 从前端传输过来
    @RequestMapping("/delete")
    public Object deleteSinger(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();  //返回值
        String id = request.getParameter("id").trim();               //主键

        Boolean flag = singerService.delete(Integer.parseInt(id));
        if (flag){ //如果删除成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"删除成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"删除失败");
        return jsonObject;

    }*/
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request){
        String id = request.getParameter("id").trim();               //主键
        Boolean flag = singerService.delete(Integer.parseInt(id));
        return flag;
    }
    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return singerService.selectByPrimaryKey(Integer.parseInt(id));

    }
    /**
     * 查询所有的歌手
     */
    @RequestMapping(value = "/allSinger",method = RequestMethod.GET)
    public Object allSinger(HttpServletRequest request){
        return singerService.allSinger();
    }

    /**
     * 根据歌手的名字模糊查询列表
     */
    @RequestMapping(value = "/singerOfName",method = RequestMethod.GET)
    public Object SingerOfName(HttpServletRequest request){
        String name = request.getParameter("name").trim(); //歌手的名字
        return singerService.singerOfName(name);
    }
    /**
     * 根据歌手的性别查询
     */
    @RequestMapping(value = "/singerOfSex",method = RequestMethod.GET)
    public Object SingerOfSex(HttpServletRequest request){
        String sex = request.getParameter("sex").trim(); //歌手的名字
        return singerService.SingerOfSex(Integer.parseInt(sex));
    }

    /**
     * 更新歌手图片
     */
    @RequestMapping(value = "/updateSingerPic",method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败失败");
            return jsonObject;
        }
        //System.currentTimeMillis() 当前时间到毫秒，avatorFile.getOriginalFilename(); 原文件名
        //System.currentTimeMillis()+avatorFile.getOriginalFilename(); ：作用防止文件名相同而被覆盖
       String fileName= System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"singerPic";
        File file1=new File(filePath);
        //判断文件是否存在 如果不存在的话 就创建一个
        if (!file1.exists()){
          file1.mkdir();
        }
        //实际的文件地址  文件地址+/+名字
        File dest=new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath="/img/singerPic/"+fileName;
        //上传过程 io异常  磁盘满了
        try {
            avatorFile.transferTo(dest);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storeAvatorPath);
            boolean flag = singerService.update(singer);
            if (flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"保存失败"+e.getMessage());
        }finally {
            return jsonObject;
        }
    }







}
