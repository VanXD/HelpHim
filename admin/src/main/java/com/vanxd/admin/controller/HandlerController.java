package com.vanxd.admin.controller;

import com.google.common.base.Throwables;
import com.vanxd.admin.exception.AuthException;
import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.exception.ParameterException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基类控制器.
 */
public abstract class HandlerController {
	@Autowired
	private HttpServletRequest request;

	/** The Constant ERROR. */
	protected static final String ERROR = "error/error";
	
	/** The Constant SUCCESS. */
	protected static final String SUCCESS = "success";
	/** 查询条件变量名 */
	protected static final String queryConditionName = "queryCondition";
	/** 分页变量名 */
	protected static final String pageName = "page";

	protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    /**
	 * 全局异常处理类，错误页面。.
	 *
	 * @param request
	 *            用户请求。
	 * @param ex
	 *            异常。
	 * @return 异常显示视图。
	 */
    @ExceptionHandler
    protected String handleException(HttpServletRequest request, Exception ex) {
    	String message = Throwables.getStackTraceAsString(ex);
    	String description = ex.getMessage();
    	logger.error(message);
		request.setAttribute("description", description);
		request.setAttribute("simpleName", Throwables.getRootCause(ex).getClass().getName());
		request.setAttribute("message", message);
//		todo 保存到数据库
//		LogUtils.saveSysLog(request,  null, ex, ex.getMessage());
		if(ex instanceof AuthException){
			return "redirect:/login";
		} else if (ex instanceof BusinessException) {
			return "error/error-business";
		} else if (ex instanceof ParameterException) {
			return "error/error-parameter";
		} else {
			request.setAttribute("description", description);
			request.setAttribute("simpleName", Throwables.getRootCause(ex).getClass().getName());
			request.setAttribute("message", "异常！");
			return "error/error";
		}
    }

	/**
	 * 自动转换日期类型的字段格式
	 * 
	 * @param binder binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
	}

	public HttpServletRequest getRequest() {
		return request;
	}
}