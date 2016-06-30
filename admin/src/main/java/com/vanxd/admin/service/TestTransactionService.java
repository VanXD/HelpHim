package com.vanxd.admin.service;

import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.repository.SysRoleRepository;
import com.vanxd.data.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wejoy-a on 2016/6/30.
 */
@Transactional
@Service
public class TestTransactionService {
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private SysUserRepository sysUserRepository;

    public SysRole save() {
        SysRole sysRole = new SysRole();
        sysRole.setChoiced("1");
        sysRole.setIsshow(true);
        sysRole.setDescription("123");
        sysRole.setName("123");
        sysRole.setId("fsd");
        sysRole.setRole("fds");
        sysRoleRepository.save(sysRole);
        if(sysRole.getRole().equals("fds")) {
            throw new RuntimeException("123");
        }
        SysUser sysUser = new SysUser();
        sysUser.setId("1");
        sysUser.setAdmin(true);
        sysUser.setCreateDate(new Date());
        sysUser.setEmail("fdfd");
        sysUser.setFullname("fds");
        sysUser.setSalt("gdfgd");
        sysUser.setUsername("usefds");
        sysUser.setStatus(1);
        sysUserRepository.save(sysUser);
        return sysRole;
    }

}
