package com.vanxd.admin.start;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.*;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wejoy-a on 2016/6/30.
 */
@Configuration
public class ThymeleafConfig {
    @Autowired
    private SpringTemplateEngine templateEngine;

    @PostConstruct
    public void extension() {
        templateEngine.addTemplateResolver(new UrlTemplateResolver());
        setShiroTag();

    }

    public void setShiroTag() {
        Set<IDialect> additionalDialects = new HashSet<IDialect>();
        additionalDialects.add(new ShiroDialect());
        templateEngine.setAdditionalDialects(additionalDialects);
    }
}
