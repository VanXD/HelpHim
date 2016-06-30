package com.vanxd.admin.start;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

/**
 * Created by wejoy-a on 2016/6/27.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.vanxd.admin")
@EntityScan(basePackages = "com.vanxd.data.entity")
@EnableJpaRepositories(basePackages= { "com.vanxd.data.repository" })
@ConditionalOnClass({FastJsonHttpMessageConverter.class})
public class Start extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter{
    public static void main(String[] args) {
        SpringApplication.run(Start.class);
    }

    @Bean
    @ConditionalOnMissingBean({FastJsonHttpMessageConverter.class})
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        ValueFilter valueFilter = new ValueFilter() {
            /**
             * 对特定的值进行处理
             *
             * @param o 类
             * @param key 键
             * @param value 值
             * @return
             */
            public Object process(Object o, String key, Object value) {
                if (null == value){
                    value = "";
                }
                return value;
            }
        };
        fastJsonConfig.setSerializeFilters(valueFilter);
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }
}
