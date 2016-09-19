package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.admin.util.ShiroUtil;
import com.vanxd.data.dict.StatusEnum;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.mapper.user.SysPermissionMapper;
import com.vanxd.data.util.VanStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by wyd on 2016/8/25.
 */
@Service
@Transactional
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, SysPermissionMapper> implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    @Override
    public SysPermissionMapper getMapper() {
        return sysPermissionMapper;
    }

    @Override
    public int save(SysPermission entity) {
        entity.setCreateTime(new Date());
        entity.setStatus(StatusEnum.NEW.getCode());
        entity.setCreatorUserId(ShiroUtil.getSessionSysUser().getId());
        entity.setId(VanStringUtils.uuid());
        if(null == entity.getIsShow()) {
            entity.setIsShow(false);
        }
        if(StringUtils.isEmpty(entity.getParentId())) {
            entity.setParentId("0");
        }
        return super.save(entity);
    }
}
