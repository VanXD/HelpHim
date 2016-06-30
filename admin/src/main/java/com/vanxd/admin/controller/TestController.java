package com.vanxd.admin.controller;

import com.vanxd.admin.service.TestTransactionService;
import com.vanxd.data.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wejoy-a on 2016/6/27.
 */
@RestController
public class TestController {
    @Autowired
    private TestTransactionService testTransactionService;

    @RequestMapping("/test")
    @ResponseBody
    public SysRole test() {
        SysRole save = testTransactionService.save();
        return save;
    }


}
