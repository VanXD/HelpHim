package com.vanxd.admin.service.user.impl;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.admin.shiro.authc.PasswordService;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.mapper.user.SysUserMapper;
import com.vanxd.data.mapper.user.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * 系统用户业务实现类
 * Created by wyd on 2016/6/30.
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserMapper> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private PasswordService passwordService;

    @Override
    public SysUserMapper getMapper() {
        return sysUserMapper;
    }

    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public int save(SysUser entity) {
        if ( isUsernameExist(entity) ) {
            throw new BusinessException("用户名已存在！");
        }
        entity.randomSalt();
        encryptPassword(entity);
        int save = super.save(entity);
        if ( null != entity ) {
            throw new RuntimeException("");
        }
        return save;
    }

    /**
     * 判断新增的用户名是否已存在
     * @param entity
     */
    private boolean isUsernameExist(SysUser entity) {
        SysUser condition = new SysUser();
        condition.setUsername(entity.getUsername());
        return count(condition, null) > 0;
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser entity) {
        SysUser dbSysUser = sysUserMapper.selectByPrimaryKey(entity.getId());
        if(null == dbSysUser) {
            throw new BusinessException("用户不存在！");
        }
        entity.setSalt(dbSysUser.getSalt());
        entity.setUsername(dbSysUser.getUsername());
        encryptPassword(entity);
        return super.updateByPrimaryKeySelective(entity);
    }


    @Override
    public Set<String> getRoleIdentitiesByUserId(String userId) {
        return sysUserRoleMapper.selectRolesByUserId(userId);
    }

    @Override
    public Set<String> getPermissionIdentitiesByUserId(String userId) {
        return sysUserRoleMapper.selectPermissionsByUserId(userId);
    }

    /**
     * 密码加密
     * @param entity 用户对象
     */
    private void encryptPassword(SysUser entity) {
        try {
            if(!StringUtils.isEmpty(entity.getPassword())) {
                entity.setPassword(passwordService.encryptPassword(entity.getUsername(),entity.getPassword(), entity.getSalt()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
