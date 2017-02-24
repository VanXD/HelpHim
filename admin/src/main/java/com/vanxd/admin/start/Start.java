package com.vanxd.admin.start;

import com.vanxd.admin.controller.AppErrorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by wejoy-a on 2016/6/27.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.vanxd.admin")
@EnableTransactionManagement
@EnableCaching
public class Start {
    @Autowired
    private ServerProperties properties;

    public static void main(String[] args) {
        SpringApplication.run(Start.class);
    }

    /**
     * 统一异常处理
     *
     * @see org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration
     * @param errorAttributes
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
    public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
        return new AppErrorController(errorAttributes, this.properties.getError());
    }
}
