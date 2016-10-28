package com.vanxd.data.entity.user;

import com.vanxd.data.annotation.TableAlias;
import com.vanxd.data.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户菜单权限
 *
 * @author wyd
 */
@TableAlias(alias = "sp")
public class SysPermission extends BaseEntity{
    /** 描述 */
    private String description;
    /** 权限标识 */
    private String permission;
    /** 名称 */
    private String name;
    /** 连接地址 */
    private String url;
    /** 图标 */
    private String icon;
    /** 权重 */
    private Integer weight;
    /** 类型 */
    private Integer type;
    /** 创建人ID */
    private String creatorUserId;
    /** 是否显示 */
    private Boolean isShow;
    /** 父ID */
    private String parentId;

    /** [VO] 角色ID */
    @TableAlias (alias = "srp")
    private String roleId;
    /** [VO]创建人昵称 */
    @TableAlias (alias = "su")
    private String creatorUserNickname;
    /** [VO] 是否已关联 */
    @TableAlias(isRequire = false)
    private boolean isChecked;
    /** [VO] 该菜单的子菜单 */
    @TableAlias(isRequire = false)
    private List<SysPermission> subPermissions = new ArrayList<SysPermission>();

    public List<SysPermission> getSubPermissions() {
        return subPermissions;
    }

    public void setSubPermissions(List<SysPermission> subPermissions) {
        this.subPermissions = subPermissions;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCreatorUserNickname() {
        return creatorUserNickname;
    }

    public void setCreatorUserNickname(String creatorUserNickname) {
        this.creatorUserNickname = creatorUserNickname;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}