package com.vanxd.admin.shiro.handler;

import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;

/**
 * 权限验证处理器
 * Created by wyd on 2016/4/22.
 */
public class CustomPermissionAnnotationHandler extends AuthorizingAnnotationHandler {
    public CustomPermissionAnnotationHandler() {
        super(RequiresPermissions.class);
    }

    protected String[] getAnnotationValue(Annotation a) {
        RequiresPermissions rpAnnotation = (RequiresPermissions)a;
        return rpAnnotation.value();
    }

    public void assertAuthorized(Annotation a) throws AuthorizationException {
        if(a instanceof RequiresPermissions) {
            RequiresPermissions rpAnnotation = (RequiresPermissions)a;
            String[] perms = this.getAnnotationValue(a);
            Subject subject = this.getSubject();
            if(perms.length == 1) {
                subject.checkPermission(perms[0]);
            } else if(Logical.AND.equals(rpAnnotation.logical())) {
                this.getSubject().checkPermissions(perms);
            } else {
                if(Logical.OR.equals(rpAnnotation.logical())) {
                    boolean hasAtLeastOnePermission = false;
                    String[] arr$ = perms;
                    int len$ = perms.length;

                    for(int i$ = 0; i$ < len$; ++i$) {
                        String permission = arr$[i$];
                        if(this.getSubject().isPermitted(permission)) {
                            hasAtLeastOnePermission = true;
                        }
                    }

                    if(!hasAtLeastOnePermission) {
                        this.getSubject().checkPermission(perms[0]);
                    }
                }

            }
        }
    }

    /**
     * 权限验证
     * 通过方法 获得 方法与该方法所属类上的@RequiresPermissions注解，将其注解value拼到一起验证
     * @param mi 执行的方法
     * @throws AuthorizationException
     */
    public void assertAuthorized(MethodInvocation mi) throws AuthorizationException {
        RequiresPermissions methodAnnotaion = mi.getMethod().getAnnotation(RequiresPermissions.class);
        String[] methodPerms = this.getAnnotationValue(methodAnnotaion);
        Subject subject = this.getSubject();
        if(methodPerms.length == 1) {
            RequiresPermissions classAnnotation = null;
            String name = mi.getThis().getClass().getName();
            int proxySymbolPos = name.indexOf("$");
            if(proxySymbolPos > -1) {
                name = name.substring(0, proxySymbolPos);
            }
            try {
                Class<?> aClass = Class.forName(name);
                classAnnotation = aClass.getAnnotation(RequiresPermissions.class);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(null != classAnnotation) {
                String[] classPerms = this.getAnnotationValue(classAnnotation);
                subject.isPermitted(classPerms[0] + methodPerms [0]);
            } else {
                subject.isPermitted(methodPerms[0]);
            }
        } else if(Logical.AND.equals(methodAnnotaion.logical())) {
            this.getSubject().checkPermissions(methodPerms);
        } else {
            if(Logical.OR.equals(methodAnnotaion.logical())) {
                boolean hasAtLeastOnePermission = false;
                String[] arr$ = methodPerms;
                int len$ = methodPerms.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String permission = arr$[i$];
                    if(this.getSubject().isPermitted(permission)) {
                        hasAtLeastOnePermission = true;
                    }
                }
                if(!hasAtLeastOnePermission) {
                    this.getSubject().checkPermission(methodPerms[0]);
                }
            }
        }
    }
}