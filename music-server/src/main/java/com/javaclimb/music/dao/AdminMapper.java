package com.javaclimb.music.dao;

import org.springframework.stereotype.Repository;

/**
 *Repository spirng管理 让service层调用
 */
@Repository
public interface AdminMapper {
    /*
    * 验证密码是否正确
    */
    public int verifyPassword(String username,String password);
}
