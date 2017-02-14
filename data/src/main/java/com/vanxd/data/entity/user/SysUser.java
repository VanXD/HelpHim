package com.vanxd.data.entity.user;

import com.vanxd.data.annotation.TableAlias;
import com.vanxd.data.entity.BaseEntity;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Set;

@TableAlias(alias = "su")
public class SysUser extends BaseEntity{
    private String email;

    private String nickname;

    private String mobilePhone;

    private String password;

    private String salt;

    private String username;

    /** [VO] 角色标识 */
    @TableAlias(isRequire = false)
    private Set<String> roleIdentities;
    /** [VO] 权限标识 */
    @TableAlias(isRequire = false)
    private Set<String> permissionIdentities;

    public Set<String> getRoleIdentities() {
        return roleIdentities;
    }

    public void setRoleIdentities(Set<String> roleIdentities) {
        this.roleIdentities = roleIdentities;
    }

    public Set<String> getPermissionIdentities() {
        return permissionIdentities;
    }

    public void setPermissionIdentities(Set<String> permissionIdentities) {
        this.permissionIdentities = permissionIdentities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 生成新的种子
     */
    public void randomSalt() {
        setSalt(RandomStringUtils.randomAlphanumeric(5));
    }
}