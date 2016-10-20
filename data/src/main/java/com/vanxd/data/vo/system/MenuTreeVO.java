package com.vanxd.data.vo.system;

import com.vanxd.data.entity.user.SysPermission;

import java.util.Date;
import java.util.List;

/**
 * @author wyd on 2016/9/9.
 */
public class MenuTreeVO {

    private String id;
    private String name;
    /** 路径 */
    private String url;
    /** 图标 */
    private String icon;
    /** 父ID */
    private String parentId;
    /** 权限标识 */
    private String permissionIdentity;
    private Date createTime;
    /** 子集 */
    private List<MenuTreeVO> children;

    public MenuTreeVO() {
    }

    public MenuTreeVO(SysPermission sysPermission) {
        this.id = sysPermission.getId();
        this.name = sysPermission.getName();
        this.url = sysPermission.getUrl();
        this.icon = sysPermission.getIcon();
        this.parentId = sysPermission.getParentId();
        this.permissionIdentity = sysPermission.getPermission();
        this.createTime = sysPermission.getCreateTime();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPermissionIdentity() {
        return permissionIdentity;
    }

    public void setPermissionIdentity(String permissionIdentity) {
        this.permissionIdentity = permissionIdentity;
    }

    public List<MenuTreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeVO> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
