package com.kkindom_blog.service;

import com.github.pagehelper.PageInfo;
import com.kkindom_blog.model.domain.Website;

import java.util.List;

public interface IWebsiteService {
        // 分页查询收藏图片基本信息列表
        public PageInfo<Website> selectWebsiteWithPage(Integer page, Integer count);

        // 根据图片id查询单个图片详情
        public Website selectWebsiteWithId(Integer id);

        //获取所有的图片信息
        public List<Website> getAll_Website();

        // 上传图片
        public void uploadWebsite(Website website);

        // 根据主键更新图片
        public void updateWebsiteWithId(Website website);

        // 根据主键删除图片
        public void deleteWebsiteWithId(int id);

        //模糊查询网站
        public PageInfo<Website> select_content_withAll_w(String con, Integer page, Integer count);
}
