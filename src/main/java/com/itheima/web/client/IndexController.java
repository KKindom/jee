package com.itheima.web.client;

import com.github.pagehelper.PageInfo;
import com.itheima.dao.EntertainmentMapper;
import com.itheima.model.domain.*;
import com.itheima.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Classname IndexController
 * @Description TODO
 * @Date 2019-3-14 9:49
 * @Created by CrazyStone
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IArticleService articleServiceImpl;
    @Autowired
    private ICommentService commentServiceImpl;
    @Autowired
    private ISiteService siteServiceImpl;

    @Autowired
    private IEntertainmentService entertainmentServiceImpl;

    @Autowired
    private EntertainmentMapper entertainmentMapper;
    @Autowired
    private IPictureService pictureService;
    @Autowired
    private IWebsiteService websiteService;
    // 博客首页，会自动跳转到文章页
    @GetMapping(value = "/")
    private String index(HttpServletRequest request) {
        return this.index(request, 1, 8);
    }

    // 文章页
    @GetMapping(value = "/page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "8") int count) {
        PageInfo<Article> articles = articleServiceImpl.selectArticleWithPage(page, count);
        // 获取文章热度统计信息
        List<Article> articleList = articleServiceImpl.getHeatArticles();
        request.setAttribute("articles", articles);
        request.setAttribute("articleList", articleList);



        List<E_type> e_typeList=entertainmentServiceImpl.findtype();
        System.out.println(e_typeList);
        logger.info("分页获取文章信息: 页码 "+page+",条数 "+count);
        return "client/index_1";
    }

    // 文章详情查询
    @GetMapping(value = "/article/{id}")
    public String getArticleById(@PathVariable("id") Integer id, HttpServletRequest request){
        Article article = articleServiceImpl.selectArticleWithId(id);
        if(article!=null){
            // 查询封装评论相关数据
            getArticleComments(request, article);
            // 更新文章点击量
            siteServiceImpl.updateStatistics(article);
            request.setAttribute("article",article);
            return "client/articleDetails_1";
        }else {
            logger.warn("查询文章详情结果为空，查询文章id: "+id);
            // 未找到对应文章页面，跳转到提示页
            return "comm/error_404";
        }
    }

    // 查询文章的评论信息，并补充到文章详情里面
    private void getArticleComments(HttpServletRequest request, Article article) {
        if (article.getAllowComment()) {
            // cp表示评论页码，commentPage
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp) ? "1" : cp;
            request.setAttribute("cp", cp);
            PageInfo<Comment> comments = commentServiceImpl.getComments(article.getId(),Integer.parseInt(cp),3);
            request.setAttribute("cp", cp);
            request.setAttribute("comments", comments);
        }
    }
    //搜索内容并返回查询结果
//con是搜索内容
    //type是指所在的页面，根据type类型不同返回不同数据与跳转不同的页面
    @PostMapping(value = "/Search_All")
    public String add(HttpServletRequest request, @RequestParam String con,@RequestParam int type)
    {
        System.out.println(con);
        PageInfo<Article> articles = articleServiceImpl.select_content_withAll(con,1,8);
        request.setAttribute("articles", articles);
        //type=1 为在后台文章管理搜索并返回 文章后台管理页面
        if(type==1)
                {
                    return "back/article_list";
                }
        //type=2 为在首页文章显示页面搜索并返回文章首页界面
                else if(type==2)
                {
                    // 获取文章热度统计信息
                    List<Article> articleList = articleServiceImpl.getHeatArticles();
                    request.setAttribute("articleList", articleList);
                   // List<E_type> e_typeList=entertainmentServiceImpl.findtype();
                    //System.out.println(e_typeList);
                    return "client/index_1";
                }
                //type=3为前台查询娱乐基本信息
                else if(type==3)
        {//获取分页
            PageInfo<Entertainment> entertainmentList = entertainmentServiceImpl.select_content_withAll_e(con);
            List<Entertainment> entertainmentList1= entertainmentMapper.select_content_withAll(con);
            // 获取所以视频信息
            List<E_Video> e_videoList = entertainmentServiceImpl.select_e_v(entertainmentList1);
            System.out.println(entertainmentList1);
            request.setAttribute("entertainers", entertainmentList);
            request.setAttribute("e_videoList", e_videoList);
            return "client/ENT_Details";

        }
                //type=4为后台查询娱乐信息
                else if(type==4)
        {
            PageInfo<Entertainment> pageInfo = entertainmentServiceImpl.select_content_withAll_e(con);
            PageInfo<E_Video> pageInfo2 = entertainmentServiceImpl.selectE_vdieoWithPage(1, 8);

            request.setAttribute("entertainments", pageInfo);
            request.setAttribute("e_videos", pageInfo2);
            return "back/entertainment_list";
        }
        //type=5为后台查询娱乐视频信息
                else if(type==5)
        {
            PageInfo<Entertainment> pageInfo = entertainmentServiceImpl.selectEntertainmentWithPage(1, 8);
            PageInfo<E_Video> pageInfo2 = entertainmentServiceImpl.select_content_withAll_v(con);

            request.setAttribute("entertainments", pageInfo);
            request.setAttribute("e_videos", pageInfo2);
            return "back/entertainment_list";
        }
                //type=6为前台查询图片收藏信息
                else if(type==6)
        {
            PageInfo<Picture> pageInfo = pictureService.select_content_withAll_p(con,1,8);
            request.setAttribute("pictures", pageInfo);
            return  "client/pictures";
        }
                //type=7为前台查询网站收藏信息
                else if(type==7)
        {
            PageInfo<Website> pageInfo = websiteService.select_content_withAll_w(con,1,5);
            request.setAttribute("websites", pageInfo);
            return  "client/websites";
        }
                //type=8为后台查询图片收藏信息
                else if(type==8)
        {
            PageInfo<Website> pageInfo1 = websiteService.selectWebsiteWithPage(1,6);
            request.setAttribute("websites", pageInfo1);
            PageInfo<Picture> pageInfo = pictureService.select_content_withAll_p(con,1,6);
            request.setAttribute("pictures", pageInfo);
            return  "/back/collect_list";
        }
              //type=9为后台查询网站收藏信息
              else if(type==9)
        {
            PageInfo<Picture> pageInfo = pictureService.selectPictureWithPage(1,6);
            request.setAttribute("pictures", pageInfo);
            PageInfo<Website> pageInfo1 = websiteService.select_content_withAll_w(con,1,6);
            request.setAttribute("websites", pageInfo1);
            return  "/back/collect_list";
        }
        return "back/article_list";
    }





    //主页面例如文章列表页面或者收藏图片页面

//跳转到显示图片页面

}

