package com.vanxd.test.service.user;

import com.vanxd.admin.service.user.SysUserRoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spring.SpringTestParent;

/**
 * @author wyd on 2016/11/28.
 */
public class SysUserRoleServiceImplTest extends SpringTestParent{
    @Autowired
    private SysUserRoleService sysUserRoleServiceImpl;

    @Test
    public void findByUserIdAndChecked() throws Exception {
        sysUserRoleServiceImpl.findByUserIdAndChecked("97426f411dc7403c9ef78a2a8bc65c0e");
    }
}