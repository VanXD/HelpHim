package com.vanxd.admin.shiro.authc;

import com.vanxd.admin.service.user.SysUserService;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.data.entity.user.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
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
		String username = (String) token.getPrincipal();

		boolean result = false;
		SysUser sysUser = sysUserService.getByUsername(username);
		try {
			result = passwordService.contrastPassword(sysUser,
					new String(((UsernamePasswordToken)token).getPassword()));
		} catch (Exception e) {
			throw new AuthenticationException("认证失败!");
		}
		if (result) {
			Session session = SecurityUtils.getSubject().getSession();
			session.setTimeout(10800000);//3小时
			session.setAttribute(GlobalKey.SESSION_USER, sysUser);
		}
		return result;
	}

}
