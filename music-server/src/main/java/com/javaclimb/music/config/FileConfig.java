package com.javaclimb.music.config;

import com.javaclimb.music.constant.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定位各种文件头像地址
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/avatorImages/**")
                .addResourceLocations(Constants.AVATOR_IMAGES_PATH);
        registry.addResourceHandler("/img/singerPic/**")
                .addResourceLocations(Constants.SINGER_PIC_PATH);
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations(Constants.SONG_PIC_PATH);
        registry.addResourceHandler("/song/**")
                .addResourceLocations(Constants.SONG_PATH);
        registry.addResourceHandler("/img/songListPic/**")
                .addResourceLocations(Constants.SONGLIST_PIC_PATH);
        registry.addResourceHandler("/img/swiper/**")
                .addResourceLocations(Constants.BANNER_PIC_PATH);


    }

//   public void  addResourceHandlers(ResourceHandlerRegistry registry) {
//
//
////           registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
////           registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//
//       //对歌手头像地址的定位
//       ///img/singerPic/** 这里是 Vue中的     addResourceLocations访问到：
//       registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
//               //在Windows中和idea中的斜杠不一样 所有
//               //System.getProperty("user.dir") 可以取到music-server层的目录
//               //System.getProperty("file.separator")  相当于 斜杠 /
//               "file" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
//                       + System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator")
//       );
//
//       //对歌单图片地址的定位
//       registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
//
//               "file"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
//                       +System.getProperty("file.separator")+"songListPic"+System.getProperty("file.separator")
//       );
//
//       //对歌曲图片地址的定位
//       registry.addResourceHandler("/img/songPic/**").addResourceLocations(
//
//               "file"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
//                       +System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator")
//       );
//      // 歌曲地址
//       registry.addResourceHandler("/song/**").addResourceLocations(
//              "file"+System.getProperty("user.dir")+System.getProperty("file.separator")+"song"+System.getProperty("file.separator")
//
//       );
//   }


}
