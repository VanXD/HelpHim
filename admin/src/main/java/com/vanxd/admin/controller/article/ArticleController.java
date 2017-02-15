package com.vanxd.admin.controller.article;

import com.vanxd.admin.controller.BaseController;
import com.vanxd.admin.service.article.ArticleService;
import com.vanxd.data.entity.article.Article;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article/information")
@RequiresPermissions("article:information")
public class ArticleController extends BaseController<Article, ArticleService> {
    @Autowired
    private ArticleService articleServiceImpl;

    @Override
    protected ArticleService getService() {
        return articleServiceImpl;
    }
}
