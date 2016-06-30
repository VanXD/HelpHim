package com.vanxd.data.entity.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 系统权限实体
 *
 * Created by wyd on 2016/6/30.
 */
@Entity
@Table
public class SysPermission implements Serializable{

    @Id
    @Column(length = 32)
    private String id;
    @Column(length = 50, nullable = false)
    private String permission;
    @Column(length = 100, nullable = false)
    private String description;
    @Column(nullable = false)
    private byte status;

    @ManyToMany(cascade=CascadeType.REFRESH,mappedBy="sysPermissions")
    private Set<SysRole> sysRoles;

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
     * Getter for property 'id'.
     *
     * @return Value for property 'id'.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for property 'id'.
     *
     * @param id Value to set for property 'id'.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for property 'permission'.
     *
     * @return Value for property 'permission'.
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Setter for property 'permission'.
     *
     * @param permission Value to set for property 'permission'.
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Getter for property 'description'.
     *
     * @return Value for property 'description'.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for property 'description'.
     *
     * @param description Value to set for property 'description'.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for property 'status'.
     *
     * @return Value for property 'status'.
     */
    public byte getStatus() {
        return status;
    }

    /**
     * Setter for property 'status'.
     *
     * @param status Value to set for property 'status'.
     */
    public void setStatus(byte status) {
        this.status = status;
    }
}
