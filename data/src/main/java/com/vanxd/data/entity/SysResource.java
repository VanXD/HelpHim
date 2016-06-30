package com.vanxd.data.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统资源表
 * Created by liuyu on 16/3/17.
 */
public class SysResource implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -334926525878690034L;

    /** id. */
    private String id;
    /** 资源名称. */
    private String name;
    /** 资源路径. */
    private String url;
    /** 父ID. */
    private String parentId;
    /** 父ID集合. */
    private String parentIds;
    /** 资源图片. */
    private String icon;
    /** 权重. */
    private int weight;
	/** 资源标识符 用于权限匹配的 如sys:resource:list */
	private String identity;
	/** 资源类型 */
	private Integer type;
	/** 删除标记 */
	private boolean deleted;
	/** 创建用户ID */
	private String createUserId;
	/** 创建时间 */
	private Date createDate;
    /** 是否在菜单中显示. */
    private boolean isshow;

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
	 * Gets the 资源名称.
	 *
	 * @return the 资源名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the 资源名称.
	 *
	 * @param name the new 资源名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 资源路径.
	 *
	 * @return the 资源路径
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the 资源路径.
	 *
	 * @param url the new 资源路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the 父ID.
	 *
	 * @return the 父ID
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Sets the 父ID.
	 *
	 * @param parentId the new 父ID
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the 父ID集合.
	 *
	 * @return the 父ID集合
	 */
	public String getParentIds() {
		return parentIds;
	}

	/**
	 * Sets the 父ID集合.
	 *
	 * @param parentIds the new 父ID集合
	 */
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	/**
	 * Gets the 资源图片.
	 *
	 * @return the 资源图片
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Sets the 资源图片.
	 *
	 * @param icon the new 资源图片
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Gets the 权重.
	 *
	 * @return the 权重
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Sets the 权重.
	 *
	 * @param weight the new 权重
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Checks if is 状态.
	 *
	 * @return the 状态
	 */
	public boolean getIsshow() {
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
	 * Gets type.
	 *
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Is deleted boolean.
	 *
	 * @return the boolean
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Sets deleted.
	 *
	 * @param deleted the deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Gets create user id.
	 *
	 * @return the create user id
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * Sets create user id.
	 *
	 * @param createUserId the create user id
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * Gets create time.
	 *
	 * @return the create time
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets create time.
	 *
	 * @param createDate the create time
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets identity.
	 *
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * Sets identity.
	 *
	 * @param identity the identity
	 */
	public void setIdentity(String identity) {
		this.identity = identity;
	}
}
