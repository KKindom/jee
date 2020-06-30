package com.itheima.web.client;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.E_Video;
import com.itheima.model.domain.Entertainment;
import com.itheima.model.domain.Picture;
import com.itheima.service.IEntertainmentService;
import com.itheima.service.IPictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
