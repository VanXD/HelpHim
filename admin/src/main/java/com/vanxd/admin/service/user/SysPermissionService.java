package com.vanxd.admin.service.user;

import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by wyd on 2016/8/25.
 */
public interface SysPermissionService {
    List<SysPermission> list(SysPermission queryCondition, PageRequest pageRequest);
}
