package com.vanxd.admin.start;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.vanxd.admin.dialect.VanThymeleafDialect;
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
        setExtraTag();
    }

    public void setExtraTag() {
        Set<IDialect> additionalDialects = new HashSet<IDialect>();

        // shiro tag
        additionalDialects.add(new ShiroDialect());

        // van tag
        additionalDialects.add(new VanThymeleafDialect());

        templateEngine.setAdditionalDialects(additionalDialects);
    }
}
