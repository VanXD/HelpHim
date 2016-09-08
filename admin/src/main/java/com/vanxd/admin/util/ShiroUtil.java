package com.vanxd.admin.util;

import com.vanxd.data.entity.user.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * @author wyd on 2016/9/8.
 */
public class ShiroUtil {
    /**
     * 获取当前登陆人的信息
     *
     * @return
     */
    public static SysUser getSessionSysUser(){
        return (SysUser) getSession().getAttribute("sysUser");
    }


    /**
     * 获取session
     *
     * @return
     */
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }
}
