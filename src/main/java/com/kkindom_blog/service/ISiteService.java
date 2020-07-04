package com.kkindom_blog.service;

import com.kkindom_blog.model.domain.Article;

/**
 * @Classname ISiteService
 * @Description 博客站点统计服务
 * @Date 2019-3-14 10:13
 * @Created by CrazyStone
 */
public interface ISiteService {

    // 更新某个文章的统计数据
    public void updateStatistics(Article article);
}

