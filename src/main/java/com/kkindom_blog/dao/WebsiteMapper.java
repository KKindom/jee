package com.kkindom_blog.dao;

import com.kkindom_blog.model.domain.Website;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WebsiteMapper {
    //查询用户id照片
    @Select("select * from t_collect_website where u_id = 1")
    public List<Website> listWebsite();

    //查询所有用户照片
    @Select("select * from t_collect_website")
    public List<Website> ALLlistWebsite();
    //添加图片
    @Insert("insert into t_collect_website (u_id,address,description,date) values (#{uid},#{address},#{description},#{date})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public int saveWebsite(Website website);

    //根据id查询照片
    @Select("select * from t_collect_website  where id = #{id}")
    public Website getWebsite(Integer id);

    //编辑修改相册
    @Update("update t_collect_website set address = #{address}, description = #{description} where id = #{id};")
    public int updateWebsite(Website website);

    //删除照片
    @Delete("delete from t_collect_website where id = #{id}")
    public void deleteWebsite(Integer id);

    //模糊查询网站
    @Select("SELECT * FROM t_collect_website WHERE description LIKE '%${con}%' or address LIKE '%${con}%'")
    public List<Website> select_content_withAll_W(@Param("con") String con);
}
