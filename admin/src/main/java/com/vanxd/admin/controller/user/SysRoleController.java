package com.vanxd.admin.controller.user;

import com.vanxd.admin.controller.BaseController;
import com.vanxd.admin.service.user.SysRoleService;
import com.vanxd.data.entity.user.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wyd on 2016/8/25.
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController<SysRole, SysRoleService>{
    @Autowired
    private SysRoleService sysRoleServiceImpl;
    @Override
    protected SysRoleService getService() {
        return sysRoleServiceImpl;
    }
}
