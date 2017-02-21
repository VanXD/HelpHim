package com.vanxd.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.vanxd.admin.service.user.SysPermissionService;
import com.vanxd.admin.service.user.SysRequestLogService;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.admin.util.LogUtil;
import com.vanxd.data.component.RespJSON;
import com.vanxd.data.entity.user.SysPermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * 当找不到页面时，spring会去请求/error这个路径
 *
 * @author wyd on 2016/11/17.
 */
public class AppErrorController extends BasicErrorController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String myExceptionPackage = "com.vanxd.admin.exception.";
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysRequestLogService sysRequestLogService;

    // todo 线程池配置需要写在Profile中各个环境不一样
    private static ExecutorService executorService = new ThreadPoolExecutor(2, 2, 1L, TimeUnit.MILLISECONDS,
                                                                            new LinkedBlockingQueue<Runnable>(1000),
                                                                            new ThreadPoolExecutor.CallerRunsPolicy());

    public AppErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        return super.errorHtml(request, response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        ResponseEntity<Map<String, Object>> error = super.error(request);
        saveExceptionLog(request, error);
        logger.error(JSONObject.toJSONString(error));
        RespJSON exceptionRespJson = RespJSON.exception(error);
        handleErrorByStatusCode(error, exceptionRespJson);
        return new ResponseEntity(exceptionRespJson, HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * 做出异步的保存错误日志
     * @param request
     * @param error
     */
    private void saveExceptionLog(HttpServletRequest request, ResponseEntity<Map<String, Object>> error) {
        ServletContext servletContext = request.getServletContext();
        Class controllerClazz = (Class) servletContext.getAttribute(GlobalKey.REQUEST_CLASS);
        HandlerMethod controllerHandler = (HandlerMethod) servletContext.getAttribute(GlobalKey.REQUEST_METHOD);
        Method method = controllerHandler.getMethod();
        RequiresPermissions  clazzAnotation = (RequiresPermissions) controllerClazz.getAnnotation(RequiresPermissions.class);
        if ( null == clazzAnotation ) {
            return;
        }
        RequiresPermissions methodAnnotation = method.getAnnotation(RequiresPermissions.class);
        String permission = clazzAnotation.value()[0] + methodAnnotation.value()[0];
        SysPermission sysPermission = sysPermissionService.findByPermission(permission);
        if (null == sysPermission) {
            LogUtil.errorLog(AppErrorController.class, String.format("没有找到权限：%s的菜单", sysPermission));
            return;
        }
        executorService.execute(() -> sysRequestLogService.save(request, error, sysPermission));
    }

    /**
     * 根据状态值处理异常
     * @param error
     * @param exceptionRespJson
     */
    private void handleErrorByStatusCode(ResponseEntity<Map<String, Object>> error, RespJSON exceptionRespJson) {
        switch (error.getStatusCode()) {
            case INTERNAL_SERVER_ERROR :
                setExceptionMsg(error, exceptionRespJson);
        }
    }

    /**
     * 如果是我们自己的异常，就把异常消息设我们想显示的消息
     * @param error                 系统发生的错误
     * @param exceptionRespJson     返回的异常JSON对象
     */
    private void setExceptionMsg(ResponseEntity<Map<String, Object>> error, RespJSON exceptionRespJson) {
        Map<String, Object> errorBody = error.getBody();
        String exceptionMessage = errorBody.get("exception").toString();
        if ( exceptionMessage.contains(myExceptionPackage) ) {
            exceptionRespJson.setMessage(errorBody.get("message").toString());
        }
    }
}
