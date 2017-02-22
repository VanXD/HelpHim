package com.vanxd.admin.config.datasource;

import com.vanxd.admin.util.LogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 使Service方法进行数据源切换的AOP
 * @author wyd on 2017/2/22.
 */
@Aspect
@Component
public class DataSourceAdvice {
    private ThreadLocal<Long> startTime = new ThreadLocal<>();


    // service方法执行之前被调用
    @Before("execution(* com.vanxd.admin.service.*.*(..))")
    public void before(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        String methodName = joinPoint.getSignature().getName();
        LogUtil.debug(DataSourceAdvice.class, String.format("切入点: %s#%s.", joinPoint.getTarget().getClass(), methodName));
        if(methodName.startsWith("add") ||
           methodName.startsWith("create") ||
           methodName.startsWith("save")   ||
           methodName.startsWith("edit")   ||
           methodName.startsWith("update") ||
           methodName.startsWith("delete") ||
           methodName.startsWith("remove")){
            LogUtil.debug(DataSourceAdvice.class, "^^^^^^^^^^切换到: 写库^^^^^^^^^^");
            DataSourceSwitcher.setWriteType();
        } else {
            LogUtil.debug(DataSourceAdvice.class, "^^^^^^^^^^切换到: 读库^^^^^^^^^^");
            DataSourceSwitcher.setReadType();
        }
    }


    @After("execution(* com.vanxd.admin.service.*.*(..))")
    public void after() {
        LogUtil.debug(DataSourceAdvice.class, String.format("__________方法耗时%d毫秒__________", System.currentTimeMillis() - startTime.get()));
    }
}
