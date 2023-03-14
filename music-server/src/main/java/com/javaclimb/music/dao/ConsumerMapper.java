package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Consumer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface ConsumerMapper {
    /**
     * 增加
     */
public int insert(Consumer consumer);
    /**
     * 修改
     */
public int update(Consumer consumer);

    /**
     * 删除
     */
    public int delete(Integer id);

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
    public int verifyPassword(@Param("username") String username,@Param("password") String password);
    /**
     * 根据账号查询用户
     */
    public Consumer selectByUserName(String username);

}
