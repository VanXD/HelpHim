package com.vanxd.data.repository;

import com.vanxd.data.entity.user.SysUser;
import org.springframework.data.repository.CrudRepository;

/**
 * 系统角色表
 */
public interface SysUserRepository extends CrudRepository<SysUser, String>{

    SysUser findByUsername(String username);
}
