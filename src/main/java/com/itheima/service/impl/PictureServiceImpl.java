package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.EntertainmentMapper;
import com.itheima.dao.PictureMapper;
import com.itheima.model.domain.*;
//import com.itheima.service.IPictureService;
import com.itheima.service.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements IPictureService {
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    //分页查询图片
    @Override
    public PageInfo<Picture> selectPictureWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Picture> pictureList=pictureMapper.ALLlistPicture();
        PageInfo<Picture> pageInfo=new PageInfo<>(pictureList);
        System.out.println(pageInfo);
        return pageInfo;
    }

    //根据图片id查询单个图片详情
    @Override
    public Picture selectPictureWithId(Integer id) {
        Picture picture=null;
        Object o=redisTemplate.opsForValue().get("picture_"+id);
        if(o!=null){
            picture=(Picture) o;
        }else{
            picture = pictureMapper.getPicture(id);
            if(picture!=null){
                redisTemplate.opsForValue().set("picture_" + id,picture);
            }
        }
        return picture;
    }

    //获取登录用户所有图片信息
    @Override
    public List<Picture> getAll_Picture() {
        List<Picture> pictureList=pictureMapper.listPicture();
        System.out.println(pictureList);
        return  pictureList;
    }

    //上传图片
    @Override
    public void uploadpicture(Picture picture) {

    }

    //编辑图片信息
    @Override
    public void updatePictureWithId(Picture picture) {

    }

    //根据图片id删除图片
    @Override
    public void deletePictureWithId(int id) {
        // 删除文章的同时，删除对应的缓存
        pictureMapper.deletePicture(id);
        redisTemplate.delete("picture_" + id);
    }

    //根据内容模糊查询图片并返回列表
    @Override
    public PageInfo<Picture> select_content_withAll_p(String con,Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Picture> PictureList=pictureMapper.select_content_withAll_P(con);
        System.out.println(PictureList);
        PageInfo<Picture> pageInfo=new PageInfo<>(PictureList);
        System.out.println(pageInfo);
        return pageInfo;
    }
}
