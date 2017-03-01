package com.vanxd.test.service.user;


import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.data.entity.user.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spring.SpringTestParent;

import java.util.Date;

/**
 * 用户测试类
 * @author wyd on 2016/9/8.
 */
public class SysUserServiceTest extends SpringTestParent{
    @Autowired
    private SysUserService sysUserServiceImpl;

    @Test
    public void testAdd() {
        SysUser sysUser = new SysUser();
        sysUser.setCreateTime(new Date());
        sysUser.setEmail("123123");
        sysUser.setNickname("123123");
        sysUser.setMobilePhone("123213");
        sysUser.setPassword("123123");
        sysUser.setUsername("junittest");
        Assert.assertTrue( 1 == sysUserServiceImpl.save(sysUser));
    }
}
