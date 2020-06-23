package com.itheima.dao;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.E_Video;
import com.itheima.model.domain.Entertainment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EntertainmentMapper
{
    // 根据id查询我的娱乐基本信息
    @Select("SELECT * FROM t_ent WHERE u_id=#{id}")
    public Entertainment selectEntWithId(Integer id);
    // 根据id查询我的娱乐视频信息
    @Select("SELECT * FROM t_video WHERE id=#{id}")
    public List<E_Video> selectEnt_VideoWithId(Integer id);

    //返回所有的视频信息
    @Select("SELECT * FROM t_video ORDER BY id DESC")
    public List<E_Video> selectAll_E_video();

    // 发表我的娱乐基本信息，同时使用@Options注解获取自动生成的主键id
    @Insert("INSERT INTO t_ent (u_id,e_id,e_title,e_picture,e_address,name)" +
            " VALUES (#{u_id},#{e_id}, #{e_title}, #{e_picture}, #{e_address},#{name})")
    @Options(useGeneratedKeys=true, keyProperty="e_id", keyColumn="e_id")
    public Integer publishEntertainment(Entertainment entertainment);

    //发表我的娱乐视频
    @Insert("INSERT INTO t_video (id,code,created)" +
            " VALUES (#{id},#{code}, #{created})")
    public Integer publishE_Video(E_Video e_video);


    // 我的娱乐分页查询
    @Select("SELECT * FROM t_ent ORDER BY e_id DESC")
        public List<Entertainment> selectEntertainmentWithPage();

    // 通过id删除我的娱乐基本信息
    @Delete("DELETE FROM t_ent WHERE e_id=#{id}")
    public void deleteEntertainmentWithId(int id);

    // 通过id删除我的娱乐视频信息
    @Delete("DELETE FROM t_video WHERE id=#{id}")
    public void deleteE_VideoWithId(int id);

    // 站点服务统计，统计娱乐数量
    @Select("SELECT COUNT(1) FROM t_ent")
    public Integer countEntertainment();

    // 通过id更新娱乐信息
    public Integer updateEntertainmentWithId(Entertainment entertainment);
}
