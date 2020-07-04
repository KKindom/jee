package com.kkindom_blog.dao;

import com.kkindom_blog.model.domain.E_Video;
import com.kkindom_blog.model.domain.E_type;
import com.kkindom_blog.model.domain.Entertainment;
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
    @Select("SELECT * FROM t_video WHERE e_id=#{id}")
    public List<E_Video> selectEnt_VideoWithId(Integer id);

    //返回所有的视频信息
    @Select("SELECT * FROM t_video ORDER BY id DESC")
    public List<E_Video> selectAll_E_video();

    // 发表我的娱乐基本信息，同时使用@Options注解获取自动生成的主键id
    @Insert("INSERT INTO t_ent (u_id,title,picture,address,name)" +
            " VALUES (#{uid}, #{title}, #{picture}, #{address},#{name})")
    @Options(useGeneratedKeys=true, keyProperty="eid", keyColumn="e_id")
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

    //根据id更新视频信息
    public Integer updataE_vWithId(E_Video e_video);


    //根据自增字段添加相应图片
    @Update("update t_ent set picture=#{picture} where e_id=#{id} ")
    public  Integer updataE_pic(String picture,Integer id);



    //查询娱乐表中所有类别
    @Select("SELECT e_id,name FROM t_ent")
    public List<E_type> findE_Type();

    //模糊查询娱乐
    @Select("SELECT * FROM t_ent WHERE title LIKE '%${con}%' OR name LIKE '%${con}%'")
    public List<Entertainment> select_content_withAll(@Param("con") String con);

    //模糊查询娱乐视频
    @Select("SELECT * FROM t_video WHERE title LIKE '%${con}%'")
    public List<E_Video> select_content_withall_v(@Param("con") String con);

}
