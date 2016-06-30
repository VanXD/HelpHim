package com.vanxd.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wejoy-a on 2016/6/27.
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public void test() {
        System.out.println("123");
    }
}
