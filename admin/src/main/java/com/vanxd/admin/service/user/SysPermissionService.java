package com.vanxd.admin.service.user;

import com.vanxd.data.entity.user.SysPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by wyd on 2016/8/25.
 */
public interface SysPermissionService {
    Page<SysPermission> list(SysPermission queryCondition, PageRequest pageRequest);
}
