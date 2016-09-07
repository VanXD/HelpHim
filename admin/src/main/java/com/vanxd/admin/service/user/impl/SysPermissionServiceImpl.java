package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.service.user.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wejoy-a on 2016/8/25.
 */
@Service
@Transactional
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    public List<SysPermission> list(SysPermission queryCondition, PageRequest pageRequest) {
        SysPermission sysPermission = sysPermissionMapper.selectByPrimaryKey("2");
        ArrayList<SysPermission> sysPermissions = new ArrayList<SysPermission>();
        sysPermissions.add(sysPermission);
        return sysPermissions;
    }
}
