package com.vanxd.data.entity.user;

import com.vanxd.data.annotation.TableAlias;
import com.vanxd.data.entity.BaseEntity;
@TableAlias(alias = "srp")
public class SysRolePermission extends BaseEntity{
    private String roleId;

    private String permissionId;

    /** [VO] 权限标识 */
    @TableAlias(alias = "sp")
    private String permission;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}