package com.kkindom_blog.web.client;

import com.github.pagehelper.PageInfo;
import com.kkindom_blog.model.domain.E_Video;
import com.kkindom_blog.model.domain.Entertainment;
import com.kkindom_blog.service.IEntertainmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EntertainmentController
{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private IEntertainmentService entertainmentServicelmpl;
    // 娱乐首页，会自动跳转到娱乐页面
    @GetMapping(value = "/ent")
    private String index(HttpServletRequest request) {
        return this.index(request, 1, 6);
    }
    //获取我的娱乐分页
    @GetMapping(value = "/e_page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "6") int count) {

        //获取分页
        PageInfo<Entertainment> entertainmentList = entertainmentServicelmpl.selectEntertainmentWithPage(page, count);
        // 获取所以视频信息
        List<E_Video> e_videoList = entertainmentServicelmpl.getAll_Video();
        request.setAttribute("entertainers", entertainmentList);
        request.setAttribute("e_videoList", e_videoList);
        logger.info("分页获取文章信息: 页码 "+page+",条数 "+count);
        return "client/ENT_Details";
    }
}
