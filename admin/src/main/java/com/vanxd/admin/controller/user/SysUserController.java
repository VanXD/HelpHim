package com.vanxd.admin.controller.user;

import com.vanxd.admin.controller.BaseController;
import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wyd on 2016/8/25.
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController<SysUser, SysUserService>{
    @Autowired
    private SysUserService sysUserServiceImpl;
    @Override
    protected SysUserService getService() {
        return sysUserServiceImpl;
    }
}
