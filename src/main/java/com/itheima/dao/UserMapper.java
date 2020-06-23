package com.itheima.dao;

import com.itheima.model.domain.Authority;
import com.itheima.model.domain.Comment;
import com.itheima.model.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface UserMapper {
    // 注册用户
    @Insert("INSERT INTO t_user (username,password,email,created)" +
            " VALUES (#{username}, #{password},#{email},#{date})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public Integer register(User user);

    // 查询用户是否存在
    @Select("SELECT * FROM t_user where username=#{user_name}")
    public User findUserByName(String user_name);

}
