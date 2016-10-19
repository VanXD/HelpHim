package com.vanxd.admin.shiro.authc;

import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.data.entity.user.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 验证密码服务,=生成session
 */
@Component
public class CustomCredentialsMatcher extends HashedCredentialsMatcher {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PasswordService passwordService;

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		boolean result = false;
		try {
			result = passwordService.contrastPassword(token, info);
		} catch (Exception e) {
			throw new AuthenticationException("认证失败!");
		}
		return result;
	}

}
