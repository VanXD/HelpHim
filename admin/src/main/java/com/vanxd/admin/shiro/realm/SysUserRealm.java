package com.vanxd.admin.shiro.realm;

import com.vanxd.admin.service.user.impl.SysUserServiceImpl;
import com.vanxd.admin.shiro.authc.CustomCredentialsMatcher;
import com.vanxd.data.entity.user.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统用户认证及授权
 * @author wyd
 */
public class SysUserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserServiceImpl sysUserService;


    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) getAvailablePrincipal(principals);
        SysUser sysUser = sysUserService.getByUsername(username);
        //获取用户的所有资源
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(sysUserService.getRoleIdentities(sysUser));
        info.setStringPermissions(sysUserService.getPermissionIdentities(sysUser));
        return info;
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

        SysUser sysUser = sysUserService.getByUsername(username);
        if(sysUser == null){
            throw new UnknownAccountException();//账号没找到
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                sysUser.getUsername(), //用户名
                sysUser.getPassword(), //密码
                ByteSource.Util.bytes(sysUser.getUsername() + sysUser.getSalt()),//salt=username+salt
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
        return new CustomCredentialsMatcher();
    }
}
