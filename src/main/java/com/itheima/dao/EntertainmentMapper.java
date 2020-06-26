package com.itheima.dao;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.E_Video;
import com.itheima.model.domain.E_type;
import com.itheima.model.domain.Entertainment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EntertainmentMapper
{
    // 根据id查询我的娱乐基本信息
    @Select("SELECT * FROM t_ent WHERE e_id=#{id}")
    public Entertainment selectEntWithId(Integer id);
    // 根据id查询我的娱乐基本信息
    @Select("SELECT * FROM t_video WHERE id=#{id}")
    public E_Video selectE_videotWithId(Integer id);
    // 根据id查询我的娱乐视频信息
    @Select("SELECT * FROM t_video WHERE id=#{id}")
    public List<E_Video> selectEnt_VideoWithId(Integer id);

    //返回所有的视频信息
    @Select("SELECT * FROM t_video ORDER BY id DESC")
    public List<E_Video> selectAll_E_video();

    // 发表我的娱乐基本信息，同时使用@Options注解获取自动生成的主键id
    @Insert("INSERT INTO t_ent (u_id,e_title,picture,address,name)" +
            " VALUES (#{id}, #{title}, #{picture}, #{address},#{name})")
    @Options(useGeneratedKeys=true, keyProperty="e_id", keyColumn="e_id")
    public Integer publishEntertainment(Entertainment entertainment);

    //发表我的娱乐视频
    @Insert("INSERT INTO t_video (e_id,code,created,title)" +
            " VALUES (#{eid},#{code}, #{created},#{title})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    public Integer publishE_Video(E_Video e_video);


    // 我的娱乐基本信息分页查询
    @Select("SELECT * FROM t_ent ORDER BY e_id DESC")
        public List<Entertainment> selectEntertainmentWithPage();
    // 我的娱乐视频信息分页查询
    @Select("SELECT * FROM t_video ORDER BY id DESC")
    public List<E_Video> selectE_videoWithPage();

    // 通过id删除我的娱乐基本信息
    @Delete("DELETE FROM t_ent WHERE e_id=#{id}")
    public void deleteEntertainmentWithId(Integer id);

    // 通过id删除我的娱乐视频信息
    @Delete("DELETE FROM t_video WHERE id=#{id}")
    public void deleteE_VideoWithId(Integer id);
    // 通过e_id删除我的娱乐视频多个视频
    @Delete("DELETE FROM t_video WHERE e_id=#{id}")
    public void deleteE_VideolistWithe_Id(Integer id);
    // 站点服务统计，统计娱乐数量
    @Select("SELECT COUNT(1) FROM t_ent")
    public Integer countEntertainment();

    // 通过id更新娱乐信息
    public Integer updateEntertainmentWithId(Entertainment entertainment);

    //查询娱乐表中所有类别
    @Select("SELECT e_id,name FROM t_ent")
    public List<E_type> findE_Type();

}
