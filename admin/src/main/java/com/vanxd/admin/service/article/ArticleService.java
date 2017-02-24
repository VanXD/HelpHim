package com.vanxd.admin.service.article;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.dict.redis.key.RedisKeyConstant;
import com.vanxd.data.entity.article.Article;
import com.vanxd.data.mapper.article.ArticleMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface ArticleService extends BaseService<Article, ArticleMapper> {

    @CacheEvict(value = RedisKeyConstant.ARTICLE_DETAIL, key = "#entity.id")
    @Override
    boolean edit(Article entity);

    @Cacheable(value = RedisKeyConstant.ARTICLE_DETAIL, key = "#id")
    @Override
    Article findByPrimaryKey(String id);

    @CacheEvict(value = RedisKeyConstant.ARTICLE_DETAIL, key = "#entity.id")
    @Override
    int deleteSoftlyByPrimaryKey(String id);
}