package com.itheima.web.admin;

import com.github.pagehelper.PageInfo;
import com.itheima.model.ResponseData.ArticleResponseData;
import com.itheima.model.ResponseData.StaticticsBo;
import com.itheima.model.domain.*;
import com.itheima.service.IArticleService;
import com.itheima.service.IEntertainmentService;
import com.itheima.service.IPictureService;
import com.itheima.service.ISiteService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    private IEntertainmentService entertainmentServiceImpl;
    @Autowired
    private IPictureService pictureServiceImpl;
    // 管理中心起始页
    @GetMapping(value = {"", "/index"})
    public String index(HttpServletRequest request) {
        // 获取最新的5篇博客、评论以及统计数据
        List<Article> articles = siteServiceImpl.recentArticles(5);
        List<Comment> comments = siteServiceImpl.recentComments(5);
        StaticticsBo staticticsBo = siteServiceImpl.getStatistics();
        //获得热度最高的文章
        List<Article> articleList = articleServiceImpl.getHeatArticles();
        // 向Request域中存储数据
        request.setAttribute("comments", comments);
        request.setAttribute("articles", articles);
        request.setAttribute("statistics", staticticsBo);
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
    // 向文章发表页面跳转
    @GetMapping(value = "/entertainment/toEditPage")
    public String new_entertainment( HttpServletRequest request) {
        List<E_type> e_typeList=entertainmentServiceImpl.findtype();
        request.setAttribute("e_type",e_typeList);
        return "back/entertainment_edit";
    }


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
    // 文章修改处理
    @PostMapping(value = "/entertainment_v/modify")
    @ResponseBody
    public ArticleResponseData modifyE_V(E_Video e_video) {
        System.out.println(e_video);
        return ArticleResponseData.fail();
    }

    //收藏图片后台管理
    // 跳转到后台收藏图片列表页面
    @GetMapping(value = "/collect")
    public String index_p(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "count", defaultValue = "6") int count,
                          HttpServletRequest request) {
        PageInfo<Picture> pageInfo = pictureServiceImpl.selectPictureWithPage(page,count);
        request.setAttribute("pictures", pageInfo);
        return "/back/collect_list";
    }

    // 收藏删除
    @PostMapping(value = "/collect/delete")
    @ResponseBody
    public ArticleResponseData delete_collect(@RequestParam int id,@RequestParam int flag)
    {
            try {
                pictureServiceImpl.deletePictureWithId(id);
                logger.info("娱乐删除成功");
                return ArticleResponseData.ok();
            } catch (Exception e) {
                logger.error("娱乐删除失败，错误信息: "+e.getMessage());
                return ArticleResponseData.fail();
            }

    }

    // 向收藏图片基本信息修改页面跳转
    @GetMapping(value = "/collect/pid={id}")
    public String editCollectPicture(@PathVariable("id") String id, HttpServletRequest request) {
        Picture picture=pictureServiceImpl.selectPictureWithId(Integer.parseInt(id));
        request.setAttribute("contents", picture);
        return "back/collect_p_edit";
    }

    // 向文章发表页面跳转
    @GetMapping(value = "/collect/toEditPicture")
    public String new_collect_picture( HttpServletRequest request) {
        return "back/collect_p_edit";
    }

    // 向文章发表页面跳转
    @GetMapping(value = "/collect/toEditArticle")
    public String new_collect_article( HttpServletRequest request) {
        return "back/collect_a_edit";
    }
}

