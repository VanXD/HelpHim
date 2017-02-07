package com.vanxd.admin.shiro.handler;

import com.vanxd.admin.exception.BusinessException;
import org.apache.shiro.aop.AnnotationHandler;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

/**
 * 权限验证拦截器，让权限验证注解处理器执行新的方法
 * Created by wyd on 2016/4/22.
 */
public class CustomPermissionAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {
    public CustomPermissionAnnotationMethodInterceptor() {
        super(new CustomPermissionAnnotationHandler());
    }

    public CustomPermissionAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new CustomPermissionAnnotationHandler(), resolver);
    }

    public void assertAuthorized(MethodInvocation mi) throws AuthorizationException {
        try {
            AnnotationHandler handler = this.getHandler();
            if(handler instanceof CustomPermissionAnnotationHandler) {
                ((CustomPermissionAnnotationHandler)handler).assertAuthorized(mi);
            } else {
                ((AuthorizingAnnotationHandler) handler).assertAuthorized(this.getAnnotation(mi));
            }
        } catch (AuthorizationException e) {
            throw new BusinessException("当前用户无该操作权限！");
            /*if(e.getCause() == null) {
                e.initCause(new AuthorizationException("Not authorized to invoke method: " + mi.getMethod()));
            }
            throw e;*/
        }
    }

}
