package com.vanxd.admin.service.system.impl;

import com.vanxd.admin.service.system.MenuService;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.admin.util.ShiroUtil;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.mapper.user.SysPermissionMapper;
import com.vanxd.data.vo.system.MenuTreeVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyd on 2016/9/9.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 获得左侧导航栏，现在只有1级dropdown
     * todo 待优化
     * @return
     */
    public List<MenuTreeVO> getMenu() {
        Session session = ShiroUtil.getSession();
        Object thymeleafMenu = session.getAttribute(GlobalKey.THYMELEAF_MENU);
        if(null != thymeleafMenu) {
            return (List<MenuTreeVO>) thymeleafMenu;
        }
        Subject subject = SecurityUtils.getSubject();

        List<SysPermission> modules = getMenuByParentId("0");
        List<SysPermission> subMenus = null;
        List<MenuTreeVO> moduleMenus = new ArrayList<MenuTreeVO>();
        List<MenuTreeVO> childrenMenus = null;
        MenuTreeVO menuTreeVO = null;
        for(SysPermission sysPermission : modules) {
            if(!subject.isPermitted(sysPermission.getPermission())) {
                continue;
            }
            menuTreeVO = new MenuTreeVO(sysPermission);
            subMenus = getMenuByParentId(sysPermission.getId());
            childrenMenus = new ArrayList<MenuTreeVO>();
            for(SysPermission subSysResource : subMenus) {
                if(!subject.isPermitted(subSysResource.getPermission())) {
                    continue;
                }
                childrenMenus.add(new MenuTreeVO(subSysResource));
            }
            menuTreeVO.setChildren(childrenMenus);
            moduleMenus.add(menuTreeVO);
        }
        session.setAttribute(GlobalKey.THYMELEAF_MENU, moduleMenus);
        return moduleMenus;
    }

    private List<SysPermission> getMenuByParentId(String parentId) {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setParentId(parentId);
        return sysPermissionMapper.page(sysPermission, null, null);
    }


}
