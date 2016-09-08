package com.vanxd.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 直接显示页面
 * @author wyd on 2016/9/8.
 */
@Controller
@RequestMapping("/forward")
public class ForwardController {

    @RequestMapping("/{action1}")
    public String jump(@PathVariable String action1) {
        return action1;
    }

    @RequestMapping("/{action1}/{action2}")
    public String jump(@PathVariable String action1,@PathVariable String action2) {
        return action1 + "/" + action2;
    }

    @RequestMapping("/{action1}/{action2}/{action3}")
    public String jump(@PathVariable String action1, @PathVariable String action2, @PathVariable String action3) {
        return action1 + "/" + action2 + "/" + action3;
    }
}
