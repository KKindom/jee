package com.kkindom_blog.dao;

import com.kkindom_blog.model.domain.Authority;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface AuthorityMapper {
    //插入用户权限
    @Insert("INSERT INTO t_user_authority (user_id,authority_id) "+
            "VALUES (#{user_id},#{authority_id})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public Integer authority(Authority authority);
}
