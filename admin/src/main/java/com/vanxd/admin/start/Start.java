package com.vanxd.admin.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by wejoy-a on 2016/6/27.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.vanxd")
@EntityScan(basePackages = "com.vanxd")
@EnableJpaRepositories(basePackages= { "com.vanxd" })
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class);
    }
}
