package com.javaclimb.music.service;

import com.javaclimb.music.domain.Consumer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface ConsumerService {
    /**
     * 增加
     */
    public boolean insert(Consumer consumer);
    /**
     * 修改
     */
    public boolean update(Consumer consumer);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 跟据主键查询
     */
    public Consumer selectByPrimaryKey(Integer id);
    /**
     * 查询表格中的所有数据
     */
    public List<Consumer> allConsumer();

    /**
     * 验证密码
     */
    public boolean verifyPassword(@Param("username") String username, @Param("password") String password);
    /**
     * 根据账号查询用户
     */
    public Consumer selectByUserName(String username);
}
