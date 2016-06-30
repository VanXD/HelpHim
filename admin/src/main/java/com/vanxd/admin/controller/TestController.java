package com.vanxd.admin.controller;

import com.vanxd.data.entity.SysRole;
import com.vanxd.data.entity.SysUser;
import com.vanxd.data.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by wejoy-a on 2016/6/27.
 */
@RestController
public class TestController {
    @Autowired
    private SysUserRepository sysUserRepository;

    @RequestMapping("/test")
    @ResponseBody
    public SysUser test() {
        SysUser one = sysUserRepository.findOne("1");
        Set<SysRole> sysRoles = one.getSysRoles();
        return one;
    }


}
