package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysRoleService;
import com.vanxd.admin.util.ShiroUtil;
import com.vanxd.data.dict.StatusEnum;
import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.mapper.user.SysRoleMapper;
import com.vanxd.data.util.VanStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wyd on 2016/8/25.
 */
@Service
@Transactional
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, SysRoleMapper> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Override
    public SysRoleMapper getMapper() {
        return sysRoleMapper;
    }

    @Override
    public int save(SysRole entity) {
        entity.setCreateTime(new Date());
        entity.setStatus(StatusEnum.NEW.getCode());
        entity.setCreatorUserId(ShiroUtil.getSessionSysUser().getId());
        entity.setId(VanStringUtils.uuid());
        return super.save(entity);
    }
}
