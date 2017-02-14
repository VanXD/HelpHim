package com.vanxd.admin.shiro.realm;

import com.vanxd.admin.shiro.authc.CustomCredentialsMatcher;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.mapper.user.SysUserMapper;
import com.vanxd.data.mapper.user.SysUserRoleMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 系统用户认证及授权
 * @author wyd
 */
public class SysUserRealm extends AuthorizingRealm {

    @Autowired
    private CustomCredentialsMatcher customCredentialsMatcher;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) getAvailablePrincipal(principals);
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        //获取用户的所有资源
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roleIdentities = sysUserRoleMapper.selectRolesByUserId(sysUser.getId());
        Set<String> permissionIdentities = sysUserRoleMapper.selectPermissionsByUserId(sysUser.getId());
        info.setRoles(roleIdentities);
        info.setStringPermissions(permissionIdentities);

        setUserInSession(sysUser, roleIdentities, permissionIdentities);

        return info;
    }

    /**
     * 将用户设置进缓存
     * @param sysUser
     * @param roleIdentities
     * @param permissionIdentities
     */
    private void setUserInSession(SysUser sysUser, Set<String> roleIdentities, Set<String> permissionIdentities) {
        sysUser.setRoleIdentities(roleIdentities);
        sysUser.setPermissionIdentities(permissionIdentities);
        Session session = SecurityUtils.getSubject().getSession();
        session.setTimeout(10800000);//3小时
        session.setAttribute(GlobalKey.SESSION_USER, sysUser);
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername().trim();
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        if(sysUser == null){
            throw new UnknownAccountException();//账号没找到
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUser.getUsername(), //用户名
                sysUser.getPassword(), //密码
                ByteSource.Util.bytes(sysUser.getSalt()),
                getName()  //realm name
        );
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        return customCredentialsMatcher;
    }
}
