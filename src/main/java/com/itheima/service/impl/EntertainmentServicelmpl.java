package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.EntertainmentMapper;
import com.itheima.dao.StatisticMapper;
import com.itheima.model.domain.E_Video;
import com.itheima.model.domain.Entertainment;
import com.itheima.service.IEntertainmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class EntertainmentServicelmpl implements IEntertainmentService {

    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private EntertainmentMapper entertainmentMapper;
//获得数据库所有视频
    @Override
    public List<E_Video> getAll_Video() {
        List<E_Video> e_videoList=entertainmentMapper.selectAll_E_video();
        System.out.println(e_videoList);
        return  e_videoList;
    }
    //分页查询我的娱乐视频信息列表
    @Override
    public PageInfo<E_Video> selectE_vdieoWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);

        List<E_Video> e_videoList=entertainmentMapper.selectE_videoWithPage();
        // System.out.println(entertainmentList);
        PageInfo<E_Video> pageInfo=new PageInfo<>(e_videoList);
        System.out.println(pageInfo);
        return pageInfo;
    }

    // 分页查询我的娱乐基本信息列表
    @Override
    public PageInfo<Entertainment> selectEntertainmentWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);

        List<Entertainment> entertainmentList=entertainmentMapper.selectEntertainmentWithPage();
       // System.out.println(entertainmentList);
        PageInfo<Entertainment> pageInfo=new PageInfo<>(entertainmentList);
       System.out.println(pageInfo);
        return pageInfo;
    }
    // 根据文章id查询单个娱乐详情
    @Override
    public Entertainment selectEntertainmentWithId(Integer id) {
        Entertainment entertainment=null;
        Object o=redisTemplate.opsForValue().get("entertainment_"+id);
        if(o!=null){
            entertainment=(Entertainment) o;
        }else{
            entertainment = entertainmentMapper.selectEntWithId(id);
            if(entertainment!=null){
                redisTemplate.opsForValue().set("entertainment_" + id,entertainment);
            }
        }
        return entertainment;
    }
    // 发布娱乐信息
    @Override
    public void publish(Entertainment entertainment) {

        // 插入我的娱乐，
        entertainmentMapper.publishEntertainment(entertainment);
    }
    // 根据主键更新娱乐
    @Override
    public void updateEntertainmentWithId(Entertainment entertainment) {
        entertainmentMapper.updateEntertainmentWithId(entertainment);
        redisTemplate.delete("entertainment_" + entertainment.getEid());
    }
    // 根据主键删除娱乐
    @Override
    public void deleteEntertainmentWithId(int id) {
        // 删除文章的同时，删除对应的缓存
        entertainmentMapper.deleteEntertainmentWithId(id);
        entertainmentMapper.deleteE_VideoWithId(id);
        redisTemplate.delete("entertainment_" + id);

    }
}
