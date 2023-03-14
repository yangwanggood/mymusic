package com.javaclimb.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 解决跨域问题
 */
//Configuration spring boot会自动加载里面的方法
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    //那些文件可以跨域
    public void addCorsMappings(CorsRegistry registry) {
        //哪些目录可以跨域
        registry.addMapping("/**")
                .allowedOrigins("*") //哪些目录可以跨域
                .allowedMethods("*") //方法
                .allowCredentials(true); //访问的时候需不需要
    }

}
