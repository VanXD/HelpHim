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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
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
     * @return
     */
    public List<SysPermission> getMenu() {
        Session session = ShiroUtil.getSession();
        Object thymeleafMenu = session.getAttribute(GlobalKey.THYMELEAF_MENU);
        if(null != thymeleafMenu) {
            return (List<SysPermission>) thymeleafMenu;
        }
        Subject subject = SecurityUtils.getSubject();
        List<SysPermission> modulePermissions = getMenuByParentIdAndShow(GlobalKey.MENU_MODULE_PARENT_ID);

        Iterator<SysPermission> iterator = modulePermissions.iterator();
        while (iterator.hasNext()) {
            SysPermission next = iterator.next();
            getSubMenu(subject, next);
            if(CollectionUtils.isEmpty(next.getSubPermissions())) {
                iterator.remove();
            }
        }
        session.setAttribute(GlobalKey.THYMELEAF_MENU, modulePermissions);
        return modulePermissions;
    }

    /**
     * 获得菜单下标记为已显示的子菜单，并根据权限进行判断
     *
     * @param subject   已登陆有丧尸
     * @param parent    父菜单
     */
    private void getSubMenu(Subject subject, SysPermission parent) {
        List<SysPermission> mens = getMenuByParentIdAndShow(parent.getId());
        List<SysPermission> showMenus = null;
        if(CollectionUtils.isEmpty(mens)) {
            return ;
        }
        if(subject.isPermitted(parent.getPermission())) {
            parent.setSubPermissions(mens);
        } else {
            List<SysPermission> parentSubPermissions = parent.getSubPermissions();
            for ( SysPermission permission : mens ) {
                if(subject.isPermitted(permission.getPermission())) {
                    parentSubPermissions.add(permission);
                } else {
                    showMenus = getMenuByParentIdAndShow(permission.getId());
                    if ( !CollectionUtils.isEmpty(showMenus) ) {
                        for( SysPermission showPerms : showMenus ) {
                            if(subject.isPermitted(showPerms.getPermission())) {
                                parentSubPermissions.add(permission);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 通过父ID获得标记为已显示的菜单
     * @param parentId  父ID
     * @return  菜单列表
     */
    private List<SysPermission> getMenuByParentIdAndShow(String parentId) {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setParentId(parentId);
        sysPermission.setIsShow(true);
        return sysPermissionMapper.page(sysPermission, null, null);
    }


}
