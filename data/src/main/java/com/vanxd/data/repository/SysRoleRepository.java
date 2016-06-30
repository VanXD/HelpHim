package com.vanxd.data.repository;

import com.vanxd.data.entity.SysRole;
import com.vanxd.data.entity.SysUser;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 系统角色表
 */
public interface SysRoleRepository extends CrudRepository<SysRole, String>{

}
