package com.javaclimb.music.service;

/**
 *
 */
public interface AdminService {
    /*
     * 验证密码是否正确
     */
    public Boolean verifyPassword(String username,String password);
}
