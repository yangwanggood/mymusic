package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.AdminMapper;
import com.javaclimb.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service  //可供 controller调用
public class AdminServiceImpl implements AdminService {
    @Autowired //根据类型进行自动装配的
    private AdminMapper adminMapper; //可以调用mapper里面的东西

    @Override
    public Boolean verifyPassword(String username, String password) {
        return adminMapper.verifyPassword(username,password)>0;  //如果大于0就是select    查询到了数据
    }
}
