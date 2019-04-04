package com.yb.shiro.mapper;

import com.yb.shiro.model.UserRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author yangbiao
 * @Description:
 * @Date $date$ $time$
 */
@Repository
@Mapper
public interface ShiroDemoMapper {

    @Transactional
    @Insert(value = "INSERT INTO `user_info` VALUES(#{id},#{username},#{password}) ")
    public void save(String username, String password);

    @Select(value = "SELECT * FROM `user_info` WHERE username=#{username}")
    public UserRequest findUserByUsername(String username);
}