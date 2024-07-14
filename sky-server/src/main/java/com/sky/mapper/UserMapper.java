package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openId
     * @return
     */
    @Select("select * from user where openid = #{openid};")
    User getByOpenId(String openId);

    /**
     * 向用户表user插入数据
     * @param user
     */
    void insert(User user);

    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id};")
    User getById(Long userId);
}
