package com.vanxd.admin.service.system;

import com.vanxd.data.entity.user.SysPermission;

import java.util.List;

/**
 * @author wyd on 2016/9/9.
 */
public interface MenuService {
    List<SysPermission> getMenu();
}
