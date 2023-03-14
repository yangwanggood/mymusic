package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.Comment;
import com.javaclimb.music.service.CommentService;
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
 * 评论控制类
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     */
    //HttpServletRequest 从前端传输过来
    //method = RequestMethod.POST ：add的访问方式为RequestMethod.POST
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();  //返回值
        String userId = request.getParameter("userId");       //用户id  //获取前端传过来的 name    trim()：去掉空格
        String type = request.getParameter("type");            //评论类型（0歌曲1歌单）
        String songId = request.getParameter("songId");            //歌曲id
        String songListId = request.getParameter("songListId");        //歌单id
        String content = request.getParameter("content").trim();  //歌单评论内容

        //保存到评论的对象中
        Comment comment = new Comment();
        comment.setUserId(Integer.parseInt(userId));
        //是要一个String 而sex是byte 需要强转型
        comment.setType(new Byte(type));
        //评论类型（0歌曲1歌单）
        if(new Byte(type)==0){
         comment.setSongId(Integer.parseInt(songId));
        }else {
         comment.setSongListId(Integer.parseInt(songListId));
        }
        comment.setContent(content);

        Boolean flag = commentService.insert(comment);
        if (flag){ //如果保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评论成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评论失败");
        return jsonObject;

    }
    /**
     * 修改评论
     */
    //HttpServletRequest 从前端传输过来
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();  //返回值
        String id = request.getParameter("id").trim();               //主键
        String userId = request.getParameter("userId");       //用户id  //获取前端传过来的 name    trim()：去掉空格
        String type = request.getParameter("type");            //评论类型（0歌曲1歌单）
        String songId = request.getParameter("songId");            //歌曲id
        String songListId = request.getParameter("songListId");       //歌单id
        String content = request.getParameter("content").trim();  //歌单评论内容
        //保存到评论的对象中
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUserId(Integer.parseInt(userId));
        //这是都是 String 而sex是byte 需要强转型
        comment.setType(new Byte(type));
        //这里的songid和songlistid会有一个值会为空传过来  我们不希望它们在后台变为空 所有
        if(songId!=null&&songId.equals("")){
            songId=null;
        }else {
            comment.setSongId(Integer.parseInt(songId));
        }
        if(songListId!=null&&songListId.equals("")){
            songListId=null;
        }else {
            comment.setSongListId(Integer.parseInt(songListId));
        }
        comment.setContent(content);

        Boolean flag = commentService.update(comment);
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
     * 删除评论
     */
   /* //HttpServletRequest 从前端传输过来
    @RequestMapping("/delete")
    public Object deleteComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();  //返回值
        String id = request.getParameter("id").trim();               //主键

        Boolean flag = commentService.delete(Integer.parseInt(id));
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
    public Object deleteComment(HttpServletRequest request){
        String id = request.getParameter("id").trim();               //主键
        Boolean flag = commentService.delete(Integer.parseInt(id));
        return flag;
    }
    /**
     * 根据主键查询整个对象
     */
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return commentService.selectByPrimaryKey(Integer.parseInt(id));

    }
    /**
     * 查询所有的评论
     */
    @RequestMapping(value = "/allComment",method = RequestMethod.GET)
    public Object allComment(HttpServletRequest request){
        return commentService.allComment();
    }


    /**
     * 查询某个歌曲去所有的评论
     */
    @RequestMapping(value = "/CommentOfSongId",method = RequestMethod.GET)
    public Object CommentOfSongId(HttpServletRequest request){
        String songId = request.getParameter("songId"); //歌曲id
        return commentService.commentOfSongId(Integer.parseInt(songId));
    }

    /**
     * 查询某个歌单所有的评论
     */
    @RequestMapping(value = "/CommentOfSongListId",method = RequestMethod.GET)
    public Object CommentOfSongListId(HttpServletRequest request){
        String songListId = request.getParameter("songListId"); //歌曲id
        return commentService.commentOfSongListId(Integer.parseInt(songListId));
    }

    /**
     * 给某个评论点赞
     */
    //HttpServletRequest 从前端传输过来
    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public Object like(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();  //返回值
        String id = request.getParameter("id").trim();               //主键
        String up = request.getParameter("up").trim();       //评论点赞
        //保存到评论的对象中
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUp(Integer.parseInt(up));
        Boolean flag = commentService.update(comment);
        if (flag){ //如果保存成功
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"点赞成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"点赞失败");
        return jsonObject;

    }








}
