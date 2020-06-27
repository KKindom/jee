package com.itheima.web.client;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.Picture;
import com.itheima.model.domain.Website;
import com.itheima.service.IPictureService;
import com.itheima.service.IWebsiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WebsiteController {
    @Autowired
    private IWebsiteService websiteService;
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    // 收藏图片页面
    @GetMapping(value = "/collect_w")
    private String index(HttpServletRequest request) {
        return this.index(request, 1, 5);
    }
    @GetMapping(value = "/w_page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "5") int count) {
        //获取分页
        PageInfo<Website> website = websiteService.selectWebsiteWithPage(page,count);
        // 获取所以视频信息
        List<Website> websiteList = websiteService.getAll_Website();
        request.setAttribute("websites", website);
        request.setAttribute("websiteList", websiteList);
        logger.info("分页获取文章信息: 页码 "+page+",条数 "+count);
        return "client/websites";
    }

    //    跳转新增页面
    /*@GetMapping("/collect_w/input")
    public String input(Model model) {
        model.addAttribute("website", new Picture());
        return "client/websites-input";
    }*/
}
