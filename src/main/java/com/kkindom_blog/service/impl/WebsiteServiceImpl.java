package com.kkindom_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kkindom_blog.dao.WebsiteMapper;
import com.kkindom_blog.model.domain.Website;
import com.kkindom_blog.service.IWebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteServiceImpl implements IWebsiteService {
    @Autowired
    private WebsiteMapper websiteMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    //分页查询图片
    @Override
    public PageInfo<Website> selectWebsiteWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Website> websiteList=websiteMapper.ALLlistWebsite();
        PageInfo<Website> pageInfo=new PageInfo<>(websiteList);
        System.out.println(pageInfo);
        return pageInfo;
    }

    //根据图片id查询单个图片详情
    @Override
    public Website selectWebsiteWithId(Integer id) {
        Website website=null;
        Object o=redisTemplate.opsForValue().get("website_"+id);
        if(o!=null){
            website=(Website) o;
        }else{
            website = websiteMapper.getWebsite(id);
            if(website!=null){
                redisTemplate.opsForValue().set("website_" + id,website);
            }
        }
        return website;
    }

    //获取登录用户所有图片信息
    @Override
    public List<Website> getAll_Website() {
        List<Website> websiteList=websiteMapper.listWebsite();
        System.out.println(websiteList);
        return  websiteList;
    }

    //上传图片
    @Override
    public void uploadWebsite(Website website) {
        websiteMapper.saveWebsite(website);
    }

    //编辑图片信息
    @Override
    public void updateWebsiteWithId(Website website) {
        websiteMapper.updateWebsite(website);
        redisTemplate.delete("website_" + website.getId());
    }

    //根据图片id删除图片
    @Override
    public void deleteWebsiteWithId(int id) {
        // 删除文章的同时，删除对应的缓存
        websiteMapper.deleteWebsite(id);
        redisTemplate.delete("Website_" + id);
    }

    //根据内容模糊查询图片并返回列表
    @Override
    public PageInfo<Website> select_content_withAll_w(String con, Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Website> WebsiteList=websiteMapper.select_content_withAll_W(con);
        System.out.println(WebsiteList);
        PageInfo<Website> pageInfo=new PageInfo<>(WebsiteList);
        System.out.println(pageInfo);
        return pageInfo;
    }
}
