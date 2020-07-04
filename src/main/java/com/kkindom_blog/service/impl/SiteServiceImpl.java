package com.kkindom_blog.service.impl;

import com.kkindom_blog.dao.ArticleMapper;
import com.kkindom_blog.dao.CommentMapper;
import com.kkindom_blog.dao.StatisticMapper;
import com.kkindom_blog.model.domain.Article;
import com.kkindom_blog.model.domain.Statistic;
import com.kkindom_blog.service.ISiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname SiteServiceImpl
 * @Description TODO
 * @Date 2019-3-14 10:15
 * @Created by CrazyStone
 */
@Service
@Transactional
public class SiteServiceImpl implements ISiteService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public void updateStatistics(Article article) {
        Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
        statistic.setHits(statistic.getHits()+1);
        statisticMapper.updateArticleHitsWithId(statistic);
    }


}

