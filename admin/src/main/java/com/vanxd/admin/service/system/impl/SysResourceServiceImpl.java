package com.vanxd.admin.service.system.impl;

import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.system.SysResourceService;
import com.vanxd.data.entity.system.SysResource;
import com.vanxd.data.mapper.system.SysResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wyd on 2016/9/9.
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource, SysResourceMapper> implements SysResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Override
    public SysResourceMapper getMapper() {
        return sysResourceMapper;
    }
}
