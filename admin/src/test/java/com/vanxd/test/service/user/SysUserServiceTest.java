package com.vanxd.test.service.user;

import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.data.entity.user.SysUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import spring.SpringTestParent;

import java.util.Date;

/**
 * @author wyd on 2016/9/8.
 */
public class SysUserServiceTest extends SpringTestParent{
    @Autowired
    private SysUserService sysUserServiceImpl;

    @Test
    @Rollback(true)
    public void testAdd() {
        SysUser sysUser = new SysUser();
        sysUser.setCreateTime(new Date());
        sysUser.setEmail("123");
        sysUser.setNickname("123");
        sysUser.setMobilePhone("123");
        sysUser.setPassword("123");
        sysUser.setUsername("123");
        sysUserServiceImpl.save(sysUser);
    }
}
