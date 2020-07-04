package com.kkindom_blog.dao;

import com.kkindom_blog.model.domain.User;
import org.apache.ibatis.annotations.*;

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

    //重置用户密码
    @Update("UPDATE t_user set password = '$2a$10$5ooQI8dir8jv0/gCa1Six.GpzAdIPf6pMqdminZ/3ijYzivCyPlfK' where id = #{id};")
    public int updateUser(User user);
}
