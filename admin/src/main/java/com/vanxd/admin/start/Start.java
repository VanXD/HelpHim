package com.vanxd.admin.start;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.vanxd.admin.shiro.realm.SysUserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wejoy-a on 2016/6/27.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.vanxd.admin")
@Configuration
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class);
    }

//    /**
//     * @ConditionalOnClass({FastJsonHttpMessageConverter.class})
//     * 配置使用
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean({FastJsonHttpMessageConverter.class})
//    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        ValueFilter valueFilter = new ValueFilter() {
//            /**
//             * 对特定的值进行处理
//             *
//             * @param o 类
//             * @param key 键
//             * @param value 值
//             * @return
//             */
//            public Object process(Object o, String key, Object value) {
//                if (null == value){
//                    value = "";
//                }
//                return value;
//            }
//        };
//        fastJsonConfig.setSerializeFilters(valueFilter);
//        converter.setFastJsonConfig(fastJsonConfig);
//        return converter;
//    }
}
