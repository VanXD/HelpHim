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

        List<SysPermission> subMenus = null;
        List<MenuTreeVO> moduleMenus = new ArrayList<MenuTreeVO>();
        List<MenuTreeVO> childrenMenus = null;
        MenuTreeVO menuTreeVO = null;
        boolean hasChildren = false;
        List<SysPermission> modules = getMenuByParentIdAndShow(GlobalKey.MENU_MODULE_PARENT_ID);

        for(SysPermission sysPermission : modules) {
            menuTreeVO = new MenuTreeVO(sysPermission);
            childrenMenus = new ArrayList<MenuTreeVO>();
            subMenus = getMenuByParentIdAndShow(sysPermission.getId());
            hasChildren = false;
            for(SysPermission subSysResource : subMenus) {
                if(subject.isPermitted(subSysResource.getPermission())) {
                    hasChildren = true;
                    childrenMenus.add(new MenuTreeVO(subSysResource));
                }
            }
            menuTreeVO.setChildren(childrenMenus);
            if(hasChildren) {
                moduleMenus.add(menuTreeVO);
            }
        }
        session.setAttribute(GlobalKey.THYMELEAF_MENU, moduleMenus);
        return moduleMenus;
    }

    private List<SysPermission> getMenuByParentIdAndShow(String parentId) {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setParentId(parentId);
        sysPermission.setIsShow(true);
        return sysPermissionMapper.page(sysPermission, null, null);
    }


}
