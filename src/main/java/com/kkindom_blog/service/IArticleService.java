package com.kkindom_blog.service;

import com.github.pagehelper.PageInfo;
import com.kkindom_blog.model.domain.Article;
import java.util.List;
/**
 * @Classname IArticleService
 * @Description TODO
 * @Date 2019-3-14 9:46
 * @Created by CrazyStone
 */

public interface IArticleService {
    // 分页查询文章列表
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count);

    // 统计前6的热度文章信息
    public List<Article> getHeatArticles();

    // 根据文章id查询单个文章详情
    public Article selectArticleWithId(Integer id);

    // 发布文章
    public void publish(Article article);

    // 根据主键更新文章
    public void updateArticleWithId(Article article);

    // 根据主键删除文章
    public void deleteArticleWithId(int id);
    //根据内容模糊查询文章并返回列表
    public  PageInfo<Article> select_content_withAll(String con,Integer page, Integer count);
}

