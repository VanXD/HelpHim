package com.vanxd.admin.shiro.authc;

import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.util.Md5Utils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Service;

/**
 * 密码服务
 */
@Service
public class PasswordService {


    /**
     * 对比输入的密码和服务器存的密码是否一致
     * @param sysUser   系统用户
     * @param password  密码
     * @return
     * @throws Exception
     */
    public boolean contrastPassword(SysUser sysUser, String password) throws Exception {
    	if(sysUser.getPassword().equals(password)){
    		return true;
    	}
        password = this.encryptPassword(sysUser.getUsername(), password, sysUser.getSalt());
        if(password.equals(sysUser.getPassword())){
            return true;
        }
        return false;
    }

    /**
     * 对比输入的密码和服务器密码
     * @param token
     * @param info
     * @return
     * @throws Exception
     */
    public boolean contrastPassword(AuthenticationToken token, AuthenticationInfo info) throws Exception {
        UsernamePasswordToken t = (UsernamePasswordToken)token;
        SimpleAuthenticationInfo i = (SimpleAuthenticationInfo) info;
        String salt = i.getCredentialsSalt().toBase64();
        String password = this.encryptPassword(t.getUsername(), new String(i.getCredentials().toString()), salt);
        if(password.equals(info.getCredentials())){
            return true;
        }
        return false;
    }

    public String encryptPassword(String username, String password, String salt) throws Exception {
        return encryptPassword(username + password + salt);
    }

    /**
     * 加密加密
     * @param str   源
     * @return
     * @throws Exception
     */
    public String encryptPassword(String str) throws Exception {
        return Md5Utils.hash(str);
    }

}
