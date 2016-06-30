package com.vanxd.data.entity.user;

import org.apache.commons.lang.RandomStringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 系统用户表
 * Created by liuyu on 16/3/17.
 */
@Entity
@Table
public class
SysUser implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4773600964948038322L;

    /** id. */
	@Id
	@Column(length = 32)
    private String id;
    
    /** 用户名. */
	@Column(length = 100, nullable = false)
    private String username;
    
    /** 用户昵称. */
	@Column(length = 100, nullable = false)
    private String fullname;
    
    /** 邮箱. */
	@Column(length = 100, nullable = false)
    private String email;
    
    /** 手机. */
	@Column(length = 11, nullable = false)
    private String mobilePhoneNumber;

    /** 密码. */
	@Column(length = 100, nullable = false)
    private String password;
    
    /** 盐值. */
	@Column(length = 5, nullable = false)
    private String salt;
    
    /** 创建时间. */
	@Column(nullable = false)
    private Date createDate;
    
    /** 状态. */
	@Column(nullable = false)
    private int status;

    /** 是否admin用户. */
	@Column(nullable = false)
    private boolean admin;

	/**
	 * JoinTable表示中间表
	 * inverseJoinColumns表示关系被维护端对应的中间表的外键名
	 * joinColumns表示关系维护端对应的中间表的外键名
	 * @return
	 */
	@ManyToMany(cascade={CascadeType.REFRESH})
	@JoinTable(name="sys_user_role",
			inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName = "id"),
			joinColumns=@JoinColumn(name="user_id", referencedColumnName = "id"))
	private Set<SysRole> sysRoles;

	public void addRole(SysRole sysRole) {
		this.sysRoles.add(sysRole);
	}
	public void removeRole(SysRole sysRole) {
		this.sysRoles.remove(sysRole);
	}

	/**
	 * Getter for property 'sysRoles'.
	 *
	 * @return Value for property 'sysRoles'.
	 */
	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}

	/**
	 * Setter for property 'sysRoles'.
	 *
	 * @param sysRoles Value to set for property 'sysRoles'.
	 */
	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
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
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the 用户名.
	 *
	 * @return the 用户名
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the 用户名.
	 *
	 * @param username
	 *            the new 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the 用户昵称.
	 *
	 * @return the 用户昵称
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Sets the 用户昵称.
	 *
	 * @param fullname
	 *            the new 用户昵称
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Gets the 邮箱.
	 *
	 * @return the 邮箱
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the 邮箱.
	 *
	 * @param email
	 *            the new 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Gets the 手机.
	 *
	 * @return the 手机
	 */
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}
	
	/**
	 * Sets the 手机.
	 *
	 * @param mobilePhoneNumber
	 *            the new 手机
	 */
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}
	
	/**
	 * Gets the 密码.
	 *
	 * @return the 密码
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the 密码.
	 *
	 * @param password
	 *            the new 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the 盐值.
	 *
	 * @return the 盐值
	 */
	public String getSalt() {
		return salt;
	}
	
	/**
	 * Sets the 盐值.
	 *
	 * @param salt
	 *            the new 盐值
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	/**
	 * Gets the 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	
	/**
	 * Sets the 创建时间.
	 *
	 * @param createDate
	 *            the new 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * Gets the 状态.
	 *
	 * @return the 状态
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Sets the 状态.
	 *
	 * @param status
	 *            the new 状态
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Checks if is 是否admin用户.
	 *
	 * @return the 是否admin用户
	 */
	public boolean isAdmin() {
		return admin;
	}
	
	/**
	 * Sets the 是否admin用户.
	 *
	 * @param admin
	 *            the new 是否admin用户
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * 生成新的种子
	 */
	public void randomSalt() {
		setSalt(RandomStringUtils.randomAlphanumeric(10));
	}
}
