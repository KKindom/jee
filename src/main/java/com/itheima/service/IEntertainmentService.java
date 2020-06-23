package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.Article;
import com.itheima.model.domain.E_Video;
import com.itheima.model.domain.Entertainment;

import java.util.List;

public interface IEntertainmentService {
    // 分页查询我的娱乐列表
    public PageInfo<Entertainment> selectEntertainmentWithPage(Integer page, Integer count);


//获取所有的视频信息
    public List<E_Video> getAll_Video();

    // 根据文章id查询单个娱乐详情
    public Entertainment selectEntertainmentWithId(Integer id);

    // 发布娱乐信息
    public void publish(Entertainment entertainment);

    // 根据主键更新娱乐
    public void updateEntertainmentWithId(Entertainment entertainment);

    // 根据主键删除娱乐
    public void deleteEntertainmentWithId(int id);
}
