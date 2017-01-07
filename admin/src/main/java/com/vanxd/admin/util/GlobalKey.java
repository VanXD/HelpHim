package com.vanxd.admin.util;

/**
 * @author wyd on 2016/9/8.
 */
public class GlobalKey {
    /** 登录用户在session中的key */
    public static final String SESSION_USER = "sessionUser";
    /** 菜单在session中的key */
    public static final String THYMELEAF_MENU = "thymeleafMenu";
    /** 菜单中的模块的父ID */
    public static final String MENU_MODULE_PARENT_ID = "0";
    /** 保存在servletContext中的请求具体类的key */
    public static final String REQUEST_CLASS = "class";
    /** 保存在servletContext中的请求具体方法的key */
    public static final String REQUEST_METHOD = "method";
}
