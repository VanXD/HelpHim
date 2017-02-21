package com.vanxd.data.mapper.user;

import com.vanxd.data.mapper.BaseMapper;
import com.vanxd.data.entity.user.SysPermission;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 通过权限表示获取权限信息
     * @param permission
     * @return
     */
    SysPermission selectByPermission(String permission);
}