package com.itheima.web.admin;

import com.github.pagehelper.PageInfo;
import com.itheima.dao.TimeMapper;
import com.itheima.model.ResponseData.ArticleResponseData;
import com.itheima.model.ResponseData.StaticticsBo;
import com.itheima.model.domain.*;
import com.itheima.service.*;
import com.itheima.utils.FileUploadUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname AdminController
 * @Description 后台管理模块
 * @Date 2019-3-14 10:39
 * @Created by CrazyStone
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    AttachFile attachFile;
    @Autowired
    private ISiteService siteServiceImpl;
    @Autowired
    private IArticleService articleServiceImpl;
    @Autowired
    private IPictureService pictureServiceImpl;
    @Autowired
    private IWebsiteService websiteService;
    @Autowired
    private IEntertainmentService entertainmentServiceImpl;
    @Autowired
    private   TimeMapper timeMapper;
    Date date=new Date();
    int eid;
    java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    // 管理中心起始页
    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {

        //获得热度最高的文章
        List<Article> articleList = articleServiceImpl.getHeatArticles();


        //传今日学习
        String str = sdf.format(date);
        Time time=timeMapper.selectTimeWithId(str);

        // 向Request域中存储数据
        request.setAttribute("today",time);
        //传出文章
        request.setAttribute("articleList", articleList);
        return "back/index";
    }

    // 向文章发表页面跳转
    @GetMapping(value = "/article/toEditPage")
    public String newArticle( ) {
        return "back/article_edit";
    }
    // 发表文章
    @PostMapping(value = "/article/publish" ,produces = {"application/text;charset=UTF-8"})
    @ResponseBody
    public ArticleResponseData publishArticle(Article article) {
        if (StringUtils.isBlank(article.getCategories())) {
            article.setCategories("默认分类");
        }
        try {
            articleServiceImpl.publish(article);
            logger.info("文章发布成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章发布失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }
    // 跳转到后台文章列表页面
    @GetMapping(value = "/article")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "count", defaultValue = "10") int count,
                        HttpServletRequest request) {
        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(page, count);
        request.setAttribute("articles", pageInfo);
        return "back/article_list";
    }

    // 向文章修改页面跳转
    @GetMapping(value = "/article/{id}")
    public String editArticle(@PathVariable("id") String id, HttpServletRequest request) {
        Article article = articleServiceImpl.selectArticleWithId(Integer.parseInt(id));
        request.setAttribute("contents", article);
        request.setAttribute("categories", article.getCategories());
        return "back/article_edit";
    }

    // 文章修改处理
    @PostMapping(value = "/article/modify")
    @ResponseBody
    public ArticleResponseData modifyArticle(Article article) {
        try {
            articleServiceImpl.updateArticleWithId(article);
            logger.info("文章更新成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章更新失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 文章删除
    @PostMapping(value = "/article/delete")
    @ResponseBody
    public ArticleResponseData delete(@RequestParam int id) {
        try {
            articleServiceImpl.deleteArticleWithId(id);
            logger.info("文章删除成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章删除失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }



//以下是后台 关于我的娱乐 我的收藏 后台管理

    //我的娱乐后台管理
    // 跳转到后台文章列表页面
    @GetMapping(value = "/entertainment")
    public String index_e(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "count", defaultValue = "6") int count,
                        HttpServletRequest request) {
        PageInfo<Entertainment> pageInfo = entertainmentServiceImpl.selectEntertainmentWithPage(page, count);
        PageInfo<E_Video> pageInfo2 = entertainmentServiceImpl.selectE_vdieoWithPage(page, count);

        request.setAttribute("entertainments", pageInfo);
        request.setAttribute("e_videos", pageInfo2);
        return "back/entertainment_list";
    }

    // 向娱乐基本信息修改页面跳转
    @GetMapping(value = "/entertainment/{id}")
    public String editEntertainment(@PathVariable("id") String id, HttpServletRequest request) {
        Entertainment entertainment=entertainmentServiceImpl.selectEntertainmentWithId(Integer.parseInt(id));
        request.setAttribute("contents", entertainment);
        request.setAttribute("flag",1);
        return "back/entertainment_edit";
    }
    // 向娱乐视频信息修改页面跳转
    @GetMapping(value = "/e_video/{id}")
    public String editE_video(@PathVariable("id") String id, HttpServletRequest request) {
        List<E_type> e_typeList=entertainmentServiceImpl.findtype();
        request.setAttribute("e_type",e_typeList);
        System.out.println(e_typeList);
       E_Video e_video=entertainmentServiceImpl.selecte_videowithId(Integer.parseInt(id));
        request.setAttribute("contents_v", e_video);
        Entertainment entertainment=new Entertainment();
        System.out.println(entertainment);
        request.setAttribute("contents", entertainment);
        request.setAttribute("flag",2);
        return "back/entertainment_edit";
    }

    // 娱乐删除
    @PostMapping(value = "/entertainment/delete")
    @ResponseBody
    public ArticleResponseData delete_entertainment(@RequestParam int id,@RequestParam int flag)
    {
        if(flag==2)
        {
            try {
                entertainmentServiceImpl.deleteEntertainmentWithId(id);
                logger.info("娱乐删除成功");
                return ArticleResponseData.ok();
            } catch (Exception e) {
                logger.error("娱乐删除失败，错误信息: "+e.getMessage());
                return ArticleResponseData.fail();
            }
        }
        else
        {
            try {
                entertainmentServiceImpl.deleteE_videoWithId(id);
                logger.info("视频删除成功");
                return ArticleResponseData.ok();
            } catch (Exception e) {
                logger.error("视频删除失败，错误信息: "+e.getMessage());
                return ArticleResponseData.fail();
            }
        }

    }
    // 向视频发表页面跳转
    @GetMapping(value = "/entertainment/toEditPage")
    public String new_entertainment( HttpServletRequest request) {
        List<E_type> e_typeList=entertainmentServiceImpl.findtype();
        Entertainment entertainment=new Entertainment();
        request.setAttribute("contents", entertainment);
        request.setAttribute("e_type",e_typeList);
        return "back/entertainment_edit";
    }

//视频新发布
    @PostMapping(value = "/entertainment_v/publish" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ArticleResponseData publishE_V(E_Video e_video) {

        System.out.println(e_video);
        try {
         entertainmentServiceImpl.publish_v(e_video);
            logger.info("文章发布成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章发布失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }
    // 视频修改处理
    @PostMapping(value = "/entertainment_v/modify")
    @ResponseBody
    public ArticleResponseData modifyE_V(E_Video e_video)
    {

        System.out.println(e_video);
        try {
            entertainmentServiceImpl.updateE_vWithId(e_video);
            logger.info("视频修改成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章修改失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }
    //发布新的娱乐类型
    @PostMapping(value = "/entertainment/publish" ,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ArticleResponseData publishentertainment(Entertainment entertainment) {
        entertainment.setUid(1);
        System.out.println(entertainment);
        try {
          eid= entertainmentServiceImpl.publish_e(entertainment);
            logger.info("娱乐发布成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("娱乐发布失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }
    // 修改娱乐类型
    @PostMapping(value = "/entertainment/modify")
    @ResponseBody
    public ArticleResponseData modifyentertainment(Entertainment entertainment)
    {
        //entertainment.setUid(1);
        eid=entertainment.getEid();
        entertainment.setPicture("/entertainment_img/game/"+eid+".jpg");
        System.out.println(entertainment);
        System.out.println("拿到的数据");
        try {
            entertainmentServiceImpl.updateEntertainmentWithId(entertainment);
            logger.info("视频修改成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章修改失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 上传图片处理
    @PostMapping(value = "/upload")
    @ResponseBody
    public ArticleResponseData ajaxUploadFile(HttpServletRequest request,
                                              @RequestParam MultipartFile file)
    {
        Map<String,Object> map=new HashMap<>();
//修改这里字段 改成到你的绝对路径 到 blog_system/src/main/resources/static/entertainment_img/game/
        String uploadDir="E:/新建文件夹/Spring Boot配套源代码 (1)/Spring Boot配套源代码/blog_system/src/main/resources/static/entertainment_img/game/";
        System.out.println("得到的id"+eid);
        try{
            attachFile= FileUploadUtils.upload(uploadDir,file,eid);
            return ArticleResponseData.ok();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            map.put("msg","fail");
            return ArticleResponseData.fail();
        }

    }
    //测试请求
    @GetMapping(value = "/upload")
    @ResponseBody
    public  String ajaxget()
    {
        return "test!";
    }
    //收藏图片后台管理
    // 跳转到后台收藏图片列表页面
    @GetMapping(value = "/collect")
    public String index_p(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "count", defaultValue = "6") int count,
                          HttpServletRequest request) {
        PageInfo<Picture> pageInfo = pictureServiceImpl.selectPictureWithPage(page,count);
        PageInfo<Website> pageInfo1 = websiteService.selectWebsiteWithPage(page,count);
        request.setAttribute("pictures", pageInfo);
        request.setAttribute("websites", pageInfo1);
        return "/back/collect_list";
    }

    // 图片收藏删除
    @PostMapping(value = "/collect/delete_p")
    @ResponseBody
    public ArticleResponseData delete_collect_p(@RequestParam int id,@RequestParam int flag)
    {
            try {
                pictureServiceImpl.deletePictureWithId(id);
                logger.info("图片删除成功");
                return ArticleResponseData.ok();
            } catch (Exception e) {
                logger.error("图片删除失败，错误信息: "+e.getMessage());
                return ArticleResponseData.fail();
            }
    }
    // 网站收藏删除
    @PostMapping(value = "/collect/delete_w")
    @ResponseBody
    public ArticleResponseData delete_collect_w(@RequestParam int id,@RequestParam int flag)
    {
        try {
            websiteService.deleteWebsiteWithId(id);
            logger.info("网站删除成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("网站删除失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }

    }

//关于后台管理功能处理
//查询分页图片展示

    // 向收藏图片基本信息修改页面跳转
    @GetMapping(value = "/collect/pid={id}")
    public String editCollectPicture(@PathVariable("id") String id, HttpServletRequest request) {
        Picture picture=pictureServiceImpl.selectPictureWithId(Integer.parseInt(id));
        request.setAttribute("contents", picture);
        return "back/collect_p_edit";
    }

    // 向收藏网址基本信息修改页面跳转
    @GetMapping(value = "/collect/wid={id}")
    public String editCollectWebsite(@PathVariable("id") String id, HttpServletRequest request) {
        Website website=websiteService.selectWebsiteWithId(Integer.parseInt(id));
        request.setAttribute("contents", website);
        return "back/collect_w_edit";
    }
    // 向添加收藏图片页面跳转
    @GetMapping(value = "/collect/toEditPicture")
    public String new_collect_picture( HttpServletRequest request) {
        return "back/collect_p_edit";
    }

    // 向添加收藏网址页面跳转
    @GetMapping(value = "/collect/toEditWebsite")
    public String new_collect_article( HttpServletRequest request) {
        return "back/collect_w_edit";
    }

    // 网站信息修改处理
    @PostMapping(value = "/collect/modify_w",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ArticleResponseData modifyWebsite(Website website) {
        try {
            System.out.println(website);
            websiteService.updateWebsiteWithId(website);
            logger.info("网站信息更新成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("网站更新失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 网站信息提交处理
    @PostMapping(value = "/collect/publish_w",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ArticleResponseData publishWebsite(Website website) {
        Date date = new Date();
        website.setDate(date.toString());
        website.setUid(1);
        try {
            System.out.println(website);
            websiteService.uploadWebsite(website);
            logger.info("网站信息更新成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("网站更新失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 网站时间提交处理
    @PostMapping(value = "/all/updata")
    @ResponseBody
    public ArticleResponseData updatatime(@RequestParam int time,@RequestParam int type)
    {

        String str = sdf.format(date);
        System.out.println(str);
        System.out.println(time);
        if(timeMapper.selectTimeWithId(str)==null)
{Time time1=new Time();
time1.setData(str);
timeMapper.insert_time(time1);
}

    Time time1=timeMapper.selectTimeWithId(str);
    if(type==1)
    {
        time1.setStudytime(time1.getStudytime()+time);
    }
    else if(type==2)
    {
        time1.setEnttime(time1.getEnttime()+time);
    }
    else
    {
        time1.setFreetime(time1.getFreetime()+time);
    }
timeMapper.updatatime(time1);
return ArticleResponseData.ok();
    }
}

