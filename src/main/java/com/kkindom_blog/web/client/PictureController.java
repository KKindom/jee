package com.kkindom_blog.web.client;

import com.github.pagehelper.PageInfo;
import com.kkindom_blog.model.domain.Picture;
import com.kkindom_blog.service.IPictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PictureController {
    @Autowired
    private IPictureService pictureService;
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    // 收藏图片页面
    @GetMapping(value = "/collect_p")
    private String index(HttpServletRequest request) {
        return this.index(request, 1, 12);
    }
    @GetMapping(value = "/p_page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "12") int count) {
        //获取分页
        PageInfo<Picture> picture = pictureService.selectPictureWithPage(page,count);
        request.setAttribute("pictures", picture);
        logger.info("分页获取文章信息: 页码 "+page+",条数 "+count);
        return "client/pictures";
    }


}
