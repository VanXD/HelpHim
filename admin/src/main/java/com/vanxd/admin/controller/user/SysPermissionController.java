package com.vanxd.admin.controller.user;

import com.vanxd.admin.controller.BaseController;
import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.entity.user.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wyd on 2016/8/25.
 */
@Controller
@RequestMapping("/system/permission")
public class SysPermissionController extends BaseController<SysPermission, SysPermissionService>{
    @Autowired
    private SysPermissionService sysPermissionServiceImpl;
    @Override
    protected SysPermissionService getService() {
        return sysPermissionServiceImpl;
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public PageResult<SysPermission> list(SysPermission condition, Pagination pagination) {
        PageResult<SysPermission> pageResult = getService().page(condition, pagination);
        return pageResult;
    }
}
