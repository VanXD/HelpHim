package com.vanxd.admin.controller.user;

import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.entity.user.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by wyd on 2016/8/25.
 */
@Controller
@RequestMapping("/sysPermission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionServiceImpl;

    @RequestMapping("/list")
    public String list(Map map, SysPermission condition, Pagination pagination) {
        PageResult<SysPermission> page = sysPermissionServiceImpl.page(condition, pagination);
        map.put("page", page);
        return "user/sys_permission/list";
    }
}
