package com.vanxd.admin.controller.system;

import com.vanxd.admin.controller.BaseController;
import com.vanxd.admin.service.system.SysResourceService;
import com.vanxd.admin.service.system.impl.SysResourceServiceImpl;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.entity.system.SysResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wyd on 2016/9/9.
 */
@Controller
@RequestMapping("/system/resource")
public class SysResourceController extends BaseController<SysResource, SysResourceService>{
    @Autowired
    private SysResourceService sysResourceServiceImpl;

    @Override
    protected SysResourceService getService() {
        return sysResourceServiceImpl;
    }

    @Override
    protected ModelAndView pageView(ModelAndView mv, SysResource condition, Pagination pagination) {
        mv.setViewName("system/resource/page");
        return mv;
    }

    @Override
    protected ModelAndView editView(ModelAndView mv, SysResource entity) {
        return null;
    }
}
