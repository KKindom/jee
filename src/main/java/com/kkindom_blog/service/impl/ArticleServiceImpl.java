package com.kkindom_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kkindom_blog.dao.ArticleMapper;
import com.kkindom_blog.dao.CommentMapper;
import com.kkindom_blog.dao.StatisticMapper;
import com.kkindom_blog.model.domain.Article;
import com.kkindom_blog.model.domain.Statistic;
import com.kkindom_blog.service.IArticleService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname ArticleServiceImpl
 * @Description TODO
 * @Date 2019-3-14 9:47
 * @Created by CrazyStone
 */
@Service
@Transactional
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommentMapper commentMapper;

    // 分页查询文章列表
    @Override
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Article> articleList = articleMapper.selectArticleWithPage();
        //System.out.println(articleList);
        // 封装文章统计数据
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        PageInfo<Article> pageInfo=new PageInfo<>(articleList);
        return pageInfo;
    }

    // 统计前6的热度文章信息
    @Override
    public List<Article> getHeatArticles( ) {
        List<Statistic> list = statisticMapper.getStatistic();
        List<Article> articlelist=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Article article = articleMapper.selectArticleWithId(list.get(i).getArticleId());
            article.setHits(list.get(i).getHits());
            article.setCommentsNum(list.get(i).getCommentsNum());
            articlelist.add(article);

            if(i>=5){
                break;
            }
        }

        return articlelist;
    }

    // 根据id查询单个文章详情，并使用Redis进行缓存管理
    public Article selectArticleWithId(Integer id){
        Article article = null;
        Object o = redisTemplate.opsForValue().get("article_" + id);
        if(o!=null){
            article=(Article)o;
        }else{
            article = articleMapper.selectArticleWithId(id);
            if(article!=null){
                redisTemplate.opsForValue().set("article_" + id,article);
            }
        }
        return article;
    }

    // 发布文章
    @Override
    public void publish(Article article) {
        // 去除表情
        article.setContent(EmojiParser.parseToAliases(article.getContent()));
        article.setCreated(new Date());
        article.setHits(0);
        article.setCommentsNum(0);
        // 插入文章，同时插入文章统计数据
        articleMapper.publishArticle(article);
        statisticMapper.addStatistic(article);
    }

    // 更新文章
    @Override
    public void updateArticleWithId(Article article) {
        article.setModified(new Date());
        articleMapper.updateArticleWithId(article);
        redisTemplate.delete("article_" + article.getId());
    }

    // 删除文章
    @Override
    public void deleteArticleWithId(int id) {
        // 删除文章的同时，删除对应的缓存
        articleMapper.deleteArticleWithId(id);
        redisTemplate.delete("article_" + id);
        // 同时删除对应文章的统计数据
        statisticMapper.deleteStatisticWithId(id);
        // 同时删除对应文章的评论数据
        commentMapper.deleteCommentWithId(id);
    }
    //根据内容模糊查询文章并返回列表
    @Override
    public PageInfo<Article> select_content_withAll(String con,Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Article> articleList=articleMapper.select_content_withAll(con);
        PageInfo<Article> pageInfo=new PageInfo<>(articleList);
        return pageInfo;
    }
}

