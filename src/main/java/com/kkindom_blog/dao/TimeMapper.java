package com.kkindom_blog.dao;

import com.kkindom_blog.model.domain.Time;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TimeMapper
{
    // 根据id查询文章信息
    @Select("SELECT * FROM t_time WHERE data=#{data}")
    public Time selectTimeWithId(String data);
    @Update("UPDATE t_time set ent_time=#{enttime},free_time=#{freetime},study_time=#{studytime} WHERE data=#{data}")
    public void updatatime(Time time);

    @Insert("INSERT INTO t_time (free_time,ent_time,study_time,data) " +
            "VALUES (#{freetime}, #{enttime}, #{studytime}, #{data})")
    public void insert_time(Time time);
}
