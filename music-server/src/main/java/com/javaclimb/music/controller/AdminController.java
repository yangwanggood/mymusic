package com.javaclimb.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.service.AdminService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 */

@RestController //注入 和 Json字符串的形式返回给客户端
public class AdminController {
    @Autowired
    private AdminService adminService;

    /*
    *判断是否成功
    */

    @RequestMapping(value ="/admin/login/status",method = RequestMethod.POST) //RequestMethod.POST 提交方式是RequestMethod.POST
   public Object loginStatus(HttpServletRequest request,  HttpSession session){ //从前端获取name 和 password
        JSONObject jsonObject=new JSONObject();  //前后端传输
        String name=request.getParameter("name");
        String password=request.getParameter("password");
          boolean flag =adminService.verifyPassword(name,password); //判断结果
          if (flag){
              jsonObject.put(Consts.CODE,1);
              jsonObject.put(Consts.MSG,"登录成功");
              session.setAttribute(Consts.Name,name); //向session中存储一个数据
              return jsonObject;
          }
          jsonObject.put(Consts.CODE,0);
          jsonObject.put(Consts.MSG,"用户名或密码错误");
          return jsonObject;
    }
    }


