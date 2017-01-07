package com.vanxd.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.data.component.RespJSON;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 *
 * 当找不到页面时，spring会去请求/error这个路径
 *
 * @author wyd on 2016/11/17.
 */
public class AppErrorController extends BasicErrorController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String myExceptionPackage = "com.vanxd.admin.exception.";

    public AppErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        return super.errorHtml(request, response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        saveExceptionLog(request);
        ResponseEntity<Map<String, Object>> error = super.error(request);
        logger.error(JSONObject.toJSONString(error));
        RespJSON exceptionRespJson = RespJSON.exception(error);
        handleErrorByStatusCode(error, exceptionRespJson);
        return new ResponseEntity(exceptionRespJson, HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * 做出异步的保存错误日志
     * @param request
     */
    private void saveExceptionLog(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        Class controllerClazz = (Class) servletContext.getAttribute(GlobalKey.REQUEST_CLASS);
        HandlerMethod controllerHandler = (HandlerMethod) servletContext.getAttribute(GlobalKey.REQUEST_METHOD);
        Method method = controllerHandler.getMethod();
        RequiresPermissions  clazzAnotation = (RequiresPermissions) controllerClazz.getAnnotation(RequiresPermissions.class);
        RequiresPermissions methodAnnotation = method.getAnnotation(RequiresPermissions.class);
        String permAnnotation = clazzAnotation.value()[0] + methodAnnotation.value()[0];
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
