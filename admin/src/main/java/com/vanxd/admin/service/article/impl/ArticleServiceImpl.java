package com.vanxd.admin.service.article.impl;

import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.article.ArticleService;
import com.vanxd.data.entity.article.Article;
import com.vanxd.data.mapper.article.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article, ArticleMapper> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ArticleMapper getMapper() {
        return articleMapper;
    }
}
