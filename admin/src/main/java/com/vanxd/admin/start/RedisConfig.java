package com.vanxd.admin.start;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置
 *
 * Created by wyd on 2017-02-24.
 */
@Configuration
public class RedisConfig {

    /**
     * 设置Redis对key的序列化方法
     * @param template
     * @return
     */
    @Bean
    public RedisTemplate getTemplate(@Qualifier(value = "redisTemplate") RedisTemplate template) {
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
