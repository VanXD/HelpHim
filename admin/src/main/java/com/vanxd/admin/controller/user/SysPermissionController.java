package com.vanxd.admin.controller.user;

import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.entity.user.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public String list(Map map, SysPermission queryCondition, Pagination pagination) {
        Page<SysPermission> list = sysPermissionServiceImpl.list(queryCondition, new PageRequest(pagination.getStart(), pagination.getPageSize()));
        map.put("list", list);
        return "user/sys_permission/list";
    }
}
