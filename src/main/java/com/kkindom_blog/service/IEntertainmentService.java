package com.kkindom_blog.service;

import com.github.pagehelper.PageInfo;
import com.kkindom_blog.model.domain.E_Video;
import com.kkindom_blog.model.domain.E_type;
import com.kkindom_blog.model.domain.Entertainment;

import java.util.List;

public interface IEntertainmentService {
    // 分页查询我的娱乐基本信息列表
    public PageInfo<Entertainment> selectEntertainmentWithPage(Integer page, Integer count);

    //分页查询我的娱乐视频信息列表
    public PageInfo<E_Video> selectE_vdieoWithPage(Integer page, Integer count);

    //获取所有的视频信息
    public List<E_Video> getAll_Video();

    // 根据文章id查询单个娱乐详情
    public Entertainment selectEntertainmentWithId(Integer id);

    // 发布娱乐信息
    public int publish_e(Entertainment entertainment);

    // 发布娱乐视频信息
    public void publish_v(E_Video e_video);



    // 根据主键更新娱乐
    public void updateEntertainmentWithId(Entertainment entertainment);
//根据主键更新视频信息
    public void updateE_vWithId(E_Video e_video);
    // 根据主键删除娱乐
    public void deleteEntertainmentWithId(int id);
//根据id查询我的娱乐视频
    public E_Video selecte_videowithId(Integer id);
    //根据id删除我的娱乐视频
    public void deleteE_videoWithId(int id);
    //根据e_id删除我的娱乐视频多个
    public void deleteE_videolistWithe_Id(int id);


    //查找娱乐类型
    public List<E_type> findtype();


    //根据内容模糊查询娱乐并返回列表
    public PageInfo<Entertainment> select_content_withAll_e(String con);

    //根据娱乐内容查找相关视频并返回列表或单个值
    public List<E_Video> select_e_v(List<Entertainment> entertainments);

    //根据内容模糊查询娱乐视频并返回列表
    public PageInfo<E_Video> select_content_withAll_v(String con);
}
