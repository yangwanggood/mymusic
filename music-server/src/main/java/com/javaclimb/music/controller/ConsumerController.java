package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Consumer;
import com.javaclimb.music.domain.Singer;
import com.javaclimb.music.service.ConsumerService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    /*
     *添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addConsumer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username").trim();   //账号
        String password = request.getParameter("password").trim();   //密码
        String sex = request.getParameter("sex").trim();   //性别
        String phoneNum = request.getParameter("phoneNum").trim();   //电话号码
        String email = request.getParameter("email").trim();   //邮箱
        String birth = request.getParameter("birth").trim();   //生日
        String introduction = request.getParameter("introduction").trim();   //前面
        String location = request.getParameter("location").trim();   //地区
        String avator = request.getParameter("avator").trim();   //头像

        if (username == null || username.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户账号不能为空");
            return jsonObject;
        }
        if (password == null || password.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户密码不能为空");
            return jsonObject;
        }
        Consumer consumer1 = consumerService.selectByUserName(username);
        if (consumer1!=null){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户已存在");
            return jsonObject;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = simpleDateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvator(avator);
        boolean flag = consumerService.insert(consumer);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "添加失败");
        return jsonObject;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateConsumer(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String username = request.getParameter("username").trim();   //账号
        String password = request.getParameter("password").trim();   //密码
        String sex = request.getParameter("sex").trim();   //性别
        String phoneNum = request.getParameter("phoneNum").trim();   //电话号码
        String email = request.getParameter("email").trim();   //邮箱
        String birth = request.getParameter("birth").trim();   //生日
        String introduction = request.getParameter("introduction").trim();   //前面
        String location = request.getParameter("location").trim();   //地区


        if (username == null || username.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户账号不能为空");
            return jsonObject;
        }
        if (password == null || password.equals("")) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "用户密码不能为空");
            return jsonObject;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = simpleDateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Consumer consumer = new Consumer();
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);

        boolean flag = consumerService.update(consumer);
        if(flag){
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteConsumer(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flag = consumerService.delete(Integer.parseInt(id));
        return flag;

    }

    /**
     * 根据主键查询
     */
    @RequestMapping(value = "/selectByPrimaryKye", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request) {
        String id = request.getParameter("id");
        return consumerService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /**
     * 查询表格中的所有数据
     */
    @RequestMapping(value = "/allConsumer", method = RequestMethod.GET)
    public Object allConsumer(HttpServletRequest request) {
        String id = request.getParameter("id");
        return consumerService.allConsumer();
    }

    /**
     * 根据账号查询用户
     */
    @RequestMapping(value = "/selectByUserName", method = RequestMethod.GET)
    public Object selectByUserName(HttpServletRequest request) {
        String username = request.getParameter("username");
        return consumerService.selectByUserName(username);
    }

    /**
     * 更新歌手图片
     */
    @RequestMapping(value = "/updateConsumerPic", method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id") int id) {
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");
            return jsonObject;
        }
        //System.currentTimeMillis() 当前时间到毫秒，avatorFile.getOriginalFilename(); 原文件名
        //System.currentTimeMillis()+avatorFile.getOriginalFilename(); ：作用防止文件名相同而被覆盖
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
     //   String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatorImages";
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"avatorImages";

        File file1 = new File(filePath);
        //判断文件是否存在 如果不存在的话 就创建一个
        if (!file1.exists()) {
            file1.mkdir();
        }
        //实际的文件地址  文件地址+/+名字
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/avatorImages/" + fileName;
        //上传过程 io异常  磁盘满了
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            boolean flag = consumerService.update(consumer);
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

 /*   @RequestMapping(value = "/updateConsumer",method = RequestMethod.POST)
    public Object updateConsumer(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "上传失败");
            return jsonObject;
            //System.currentTimeMillis() 当前时间到毫秒，avatorFile.getOriginalFilename(); 原文件名
            //System.currentTimeMillis()+avatorFile.getOriginalFilename(); ：作用防止文件名相同而被覆盖
            String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
            String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatorImages";

            File file1 = new File(filePath);
            //判断文件是否存在 如果不存在的话 就创建一个
            if (!file1.exists()) {
                file1.mkdir();
            }
            //实际的文件地址  文件地址+/+名字
            File dest = new File(filePath + System.getProperty("file.separator") + fileName);
            //存储到数据库里的相对文件地址
            String storeAvatorPath = "/img/singerPic/" + fileName;
            //上传过程 io异常  磁盘满了
            try {
                avatorFile.transferTo(dest);
                Consumer consumer = new Consumer();
                consumer.setId(id);
                consumer.setAvator(storeAvatorPath);
                boolean flag = consumerService.update(consumer);
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
        }*/
 /*
  *前端用户登录
  */
 @RequestMapping(value = "/login", method = RequestMethod.POST)
 public Object loginIn(HttpServletRequest request) {
     JSONObject jsonObject = new JSONObject();
     String username = request.getParameter("username").trim();   //账号
     String password = request.getParameter("password").trim();   //密码

     if (username == null || username.equals("")) {
         jsonObject.put(Consts.CODE, 0);
         jsonObject.put(Consts.MSG, "用户账号不能为空");
         return jsonObject;
     }
     if (password == null || password.equals("")) {
         jsonObject.put(Consts.CODE, 0);
         jsonObject.put(Consts.MSG, "用户密码不能为空");
         return jsonObject;
     }


     Consumer consumer = new Consumer();
     consumer.setUsername(username);
     consumer.setPassword(password);
     boolean flag = consumerService.verifyPassword(username,password);
     if (flag) {
         jsonObject.put(Consts.CODE, 1);
         jsonObject.put(Consts.MSG, "登录成功");
         jsonObject.put("userMsg",consumerService.selectByUserName(username));
         return jsonObject;
     }
     jsonObject.put(Consts.CODE, 0);
     jsonObject.put(Consts.MSG, "用户名或密码错误");
     return jsonObject;
 }

}