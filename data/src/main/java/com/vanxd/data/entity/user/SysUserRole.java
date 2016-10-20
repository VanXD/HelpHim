package com.vanxd.data.entity.user;

import com.vanxd.data.annotation.TableAlias;
import com.vanxd.data.entity.BaseEntity;

@TableAlias(alias = "sur")
public class SysUserRole extends BaseEntity{
    private String userId;

    private String roleId;

    /** [VO] 角色标识 */
    @TableAlias(alias = "sr")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}