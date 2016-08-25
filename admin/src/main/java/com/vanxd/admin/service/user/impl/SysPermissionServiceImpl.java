package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.repository.SysPermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wejoy-a on 2016/8/25.
 */
@Service
@Transactional
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private SysPermissionRepo sysPermissionRepo;

    public Page<SysPermission> list(SysPermission queryCondition, PageRequest pageRequest) {
        return sysPermissionRepo.findAll(pageRequest);
    }
}
