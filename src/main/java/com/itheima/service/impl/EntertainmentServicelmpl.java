package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.EntertainmentMapper;
import com.itheima.dao.StatisticMapper;
import com.itheima.model.domain.E_Video;
import com.itheima.model.domain.E_type;
import com.itheima.model.domain.Entertainment;
import com.itheima.service.IEntertainmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    int e_id;
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
    public int publish_e(Entertainment entertainment) {

        // 插入我的娱乐，
      entertainmentMapper.publishEntertainment(entertainment);
        String picture="/entertainment_img/game/"+entertainment.getEid()+".jpg";
        int id=entertainment.getEid();
        entertainmentMapper.updataE_pic(picture,id);
                System.out.println(entertainment);
                return id;
    }
    // 根据主键更新娱乐
    @Override
    public void updateEntertainmentWithId(Entertainment entertainment) {
        entertainmentMapper.updateEntertainmentWithId(entertainment);
        //redisTemplate.delete("entertainment_" + entertainment.getEid());
    }
    // 根据主键删除娱乐
    @Override
    public void deleteEntertainmentWithId(int id) {
        // 删除文章的同时，删除对应的缓存
        entertainmentMapper.deleteEntertainmentWithId(id);
        entertainmentMapper.deleteE_VideolistWithe_Id(id);
        redisTemplate.delete("entertainment_" + id);
    }
    //根据id删除我的娱乐视频
    @Override
    public void deleteE_videoWithId(int id) {
        entertainmentMapper.deleteE_VideoWithId(id);
        redisTemplate.delete("e_video_" + id);

    }

    //根据id查询我的娱乐视频
    @Override
    public E_Video selecte_videowithId(Integer id) {
        E_Video e_video=null;
        Object o=redisTemplate.opsForValue().get("e_video_"+id);
        if(o!=null){
            e_video=(E_Video) o;
        }else{
            e_video = entertainmentMapper.selectE_videotWithId(id);
            if(e_video!=null){
                redisTemplate.opsForValue().set("e_video" + id,e_video);
            }
        }
        return e_video;
    }
    //根据e_id删除我的娱乐视频多个
    @Override
    public void deleteE_videolistWithe_Id(int id) {
        entertainmentMapper.deleteE_VideolistWithe_Id(id);
        redisTemplate.delete("e_video_" + id);
    }
    // 发布娱乐视频信息
    @Override
    public void publish_v(E_Video e_video) {
        //拼接bilibili视频代码
        String code=e_video.getCode();
        code=code.replace("<iframe","<iframe  style=\"position: absolute; width: 100%; height: 100%; left: 0; top: 0;\"");
        e_video.setCode(code);
        e_video.setCreated(new Date());
        //e_video.setEid(3);
        System.out.println(e_video);
        entertainmentMapper.publishE_Video(e_video);
    }
    //查找娱乐类型
    @Override
    public List<E_type> findtype() {
        return entertainmentMapper.findE_Type();
    }
    //根据主键更新视频信息
    @Override
    public void updateE_vWithId(E_Video e_video) {
        entertainmentMapper.updataE_vWithId(e_video);
        redisTemplate.delete("E_video" + e_video.getId());
    }

    //根据内容模糊查询娱乐并返回列表
    @Override
    public PageInfo<Entertainment> select_content_withAll_e(String con) {
        PageHelper.startPage(1, 8);
        List<Entertainment> entertainmentList=entertainmentMapper.select_content_withAll(con);
        // System.out.println(entertainmentList);
        PageInfo<Entertainment> pageInfo=new PageInfo<>(entertainmentList);
        System.out.println(pageInfo);
        return pageInfo;
    }
//根据模糊查询的娱乐项查找拼接相关娱乐视频
    @Override
    public List<E_Video> select_e_v(List<Entertainment> entertainments)
    {List<E_Video> e_videoList=new ArrayList<>() ;
        for (Entertainment entertainment : entertainments) {
            e_videoList.addAll(entertainmentMapper.selectEnt_VideoWithId(entertainment.getEid()));
        }
        return e_videoList;
    }
    //根据内容模糊查询娱乐视频并返回列表
    @Override
    public PageInfo<E_Video> select_content_withAll_v(String con) {
        PageHelper.startPage(1, 8);
        List<E_Video> entertainmentList=entertainmentMapper.select_content_withall_v(con);
        // System.out.println(entertainmentList);
        PageInfo<E_Video> pageInfo=new PageInfo<>(entertainmentList);
        return pageInfo;
    }
}
