package com.vanxd.data.entity.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 系统角色表
 * Created by liuyu on 16/3/17.
 */
@Entity
@Table
public class SysRole implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -755770047134738327L;

    /** id. */
	@Id
	@Column(length = 32)
    private String id;
    
    /** 角色名称. */
	@Column(length = 100, nullable = false)
    private String name;
    
    /** 角色代码. */
	@Column(length = 100, nullable = false)
    private String role;
    
    /** 描述. */
	@Column(length = 300, nullable = false)
    private String description;
    
    /** 状态. */
	@Column(nullable = false)
    private boolean isshow;

	/** 用于判断用户是否选中的标记 */
	@Transient
	private String choiced;

	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="sysRoles")
	private Set<SysUser> sysUsers;

	@ManyToMany(cascade={CascadeType.REFRESH})
	@JoinTable(name="sys_role_permission",
			inverseJoinColumns=@JoinColumn(name="permission_id", referencedColumnName = "id"),
			joinColumns=@JoinColumn(name="role_id", referencedColumnName = "id"))
	private Set<SysPermission> sysPermissions;

	public boolean addPermission(SysPermission sysPermission) {
		if(!sysPermissions.contains(sysPermission)) {
			return sysPermissions.add(sysPermission);
		}
		return false;
	}

	public boolean removePermission(SysPermission sysPermission) {
		if(sysPermissions.contains(sysPermission)) {
			return sysPermissions.remove(sysPermission);
		}
		return false;
	}

	/**
	 * Getter for property 'sysPermissions'.
	 *
	 * @return Value for property 'sysPermissions'.
	 */
	public Set<SysPermission> getSysPermissions() {
		return sysPermissions;
	}

	/**
	 * Setter for property 'sysPermissions'.
	 *
	 * @param sysPermissions Value to set for property 'sysPermissions'.
	 */
	public void setSysPermissions(Set<SysPermission> sysPermissions) {
		this.sysPermissions = sysPermissions;
	}

	/**
	 * Getter for property 'sysUsers'.
	 *
	 * @return Value for property 'sysUsers'.
	 */
	public Set<SysUser> getSysUsers() {
		return sysUsers;
	}

	/**
	 * Setter for property 'sysUsers'.
	 *
	 * @param sysUsers Value to set for property 'sysUsers'.
	 */
	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the 角色名称.
	 *
	 * @return the 角色名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the 角色名称.
	 *
	 * @param name the new 角色名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 角色代码.
	 *
	 * @return the 角色代码
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the 角色代码.
	 *
	 * @param role the new 角色代码
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the 描述.
	 *
	 * @return the 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the 描述.
	 *
	 * @param description the new 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks if is 状态.
	 *
	 * @return the 状态
	 */
	public boolean isIsshow() {
		return isshow;
	}

	/**
	 * Sets the 状态.
	 *
	 * @param isshow the new 状态
	 */
	public void setIsshow(boolean isshow) {
		this.isshow = isshow;
	}


	/**
	 * Gets choiced.
	 *
	 * @return the choiced
	 */
	public String getChoiced() {
		return choiced;
	}

	/**
	 * Sets choiced.
	 *
	 * @param choiced the choiced
	 */
	public void setChoiced(String choiced) {
		this.choiced = choiced;
	}
}
