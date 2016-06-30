package com.vanxd.data.repository;

import com.vanxd.data.entity.user.SysRole;
import org.springframework.data.repository.CrudRepository;

/**
 * 系统角色表
 */
public interface SysRoleRepository extends CrudRepository<SysRole, String>{

}
