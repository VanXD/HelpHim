package com.vanxd.admin.controller.system;

import com.vanxd.admin.controller.BaseController;
import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.entity.user.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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


}
