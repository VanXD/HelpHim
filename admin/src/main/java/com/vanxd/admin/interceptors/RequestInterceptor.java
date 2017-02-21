package com.vanxd.admin.interceptors;

import com.vanxd.admin.controller.AppErrorController;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.admin.util.ShiroUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * URL请求拦截器，拦截所有的请求
 * 将请求方法设置到servletContext中的method属性
 * @author wyd on 2016/12/19.
 */
public class RequestInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class<?> beanType = handlerMethod.getBeanType();
        if ( handlerMethod.getBeanType() != AppErrorController.class) {
            request.getServletContext().setAttribute(GlobalKey.REQUEST_CLASS, beanType);
            request.getServletContext().setAttribute(GlobalKey.REQUEST_METHOD, handlerMethod);
            request.getServletContext().setAttribute(GlobalKey.REQUEST_EXCEPTION_USER, ShiroUtil.getSessionSysUser());
        }
        return super.preHandle(request, response, handler);
    }
}
