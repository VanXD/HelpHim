package com.vanxd.admin.service.system.impl;

import com.vanxd.admin.service.system.MenuService;
import com.vanxd.data.entity.system.SysResource;
import com.vanxd.data.mapper.system.SysResourceMapper;
import com.vanxd.data.vo.MenuTreeVO;
import org.apache.shiro.SecurityUtils;
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
    private SysResourceMapper sysResourceMapper;

    /**
     * 获得左侧导航栏，现在只有1级dropdown
     * todo 待优化
     * @return
     */
    public List<MenuTreeVO> getMenu() {
        Subject subject = SecurityUtils.getSubject();

        List<SysResource> modules = getMenuByParentId("0");
        List<SysResource> subMenus = null;
        List<MenuTreeVO> moduleMenus = new ArrayList<MenuTreeVO>();
        List<MenuTreeVO> childrenMenus = null;
        MenuTreeVO menuTreeVO = null;
        for(SysResource sysResource : modules) {
            if(!subject.isPermitted(sysResource.getPermissionIdentity())) {
                continue;
            }
            menuTreeVO = new MenuTreeVO(sysResource);
            subMenus = getMenuByParentId(sysResource.getId());
            childrenMenus = new ArrayList<MenuTreeVO>();
            for(SysResource subSysResource : subMenus) {
                if(!subject.isPermitted(subSysResource.getPermissionIdentity())) {
                    continue;
                }
                childrenMenus.add(new MenuTreeVO(subSysResource));
            }
            menuTreeVO.setChildren(childrenMenus);
            moduleMenus.add(menuTreeVO);
        }
        return moduleMenus;
    }

    private List<SysResource> getMenuByParentId(String parentId) {
        SysResource sysResource = new SysResource();
        sysResource.setParentId(parentId);
        return sysResourceMapper.page(sysResource, null);
    }


}
