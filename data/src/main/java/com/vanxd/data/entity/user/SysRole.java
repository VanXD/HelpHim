package com.vanxd.data.entity.user;

import com.vanxd.data.annotation.TableAlias;
import com.vanxd.data.entity.BaseEntity;

import java.util.Date;

/**
 * 系统角色
 */
@TableAlias(alias = "sr")
public class SysRole extends BaseEntity{
    /** 描述 */
    private String description;
    /** 角色名 */
    private String name;
    /** 角色标识 */
    private String role;
    /** 创建人ID */
    private String creatorUserId;

    /** [VO] 创建人昵称 */
    @TableAlias(alias = "su")
    private String creatorUserNickname;
    /** [VO] 是否已关联 */
    @TableAlias(isRequire = false)
    private boolean isChecked;

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

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}