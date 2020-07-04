package com.kkindom_blog.service;

import com.github.pagehelper.PageInfo;
import com.kkindom_blog.model.domain.Picture;

import java.util.List;

public interface IPictureService {
    // 分页查询收藏图片基本信息列表
    public PageInfo<Picture> selectPictureWithPage(Integer page, Integer count);

    // 根据图片id查询单个图片详情
    public Picture selectPictureWithId(Integer id);

    //获取所有的图片信息
    public List<Picture> getAll_Picture();

    // 上传图片
    public void uploadpicture(Picture picture);

    // 根据主键更新图片
    public void updatePictureWithId(Picture picture);

    // 根据主键删除图片
    public void deletePictureWithId(int id);

    //模糊查询图片
    public PageInfo<Picture> select_content_withAll_p(String con,Integer page, Integer count);
}
