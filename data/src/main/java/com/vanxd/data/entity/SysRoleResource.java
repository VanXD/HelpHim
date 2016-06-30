package com.vanxd.data.entity;

import java.io.Serializable;

/**
 * 系统角色资源权限
 * Created by liuyu on 16/3/17.
 */
public class SysRoleResource implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4161384335233423074L;

    /** id. */
    private String id;

    /** 角色id. */
    private String roleId;

    /** 资源id. */
    private String resourceId;

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
     * Gets the 角色id.
     *
     * @return the 角色id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * Sets the 角色id.
     *
     * @param roleId
     *            the new 角色id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * Gets the 资源id.
     *
     * @return the 资源id
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Sets the 资源id.
     *
     * @param resourceId
     *            the new 资源id
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


}
