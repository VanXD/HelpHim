package com.vanxd.admin.controller;

import com.google.common.base.Throwables;
import com.vanxd.admin.exception.AuthException;
import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.exception.ParameterException;
import com.vanxd.data.entity.user.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基类控制器.
 */
public abstract class BaseController {
	
	/** The Constant ERROR. */
	protected static final String ERROR = "error/error";
	
	/** The Constant SUCCESS. */
	protected static final String SUCCESS = "success";
	/** 查询条件变量名 */
	protected static final String queryConditionName = "queryCondition";
	/** 分页变量名 */
	protected static final String pageName = "page";

	/**
	 * 获取当前登陆人的信息
	 *
	 * @return
	 */
	public SysUser getSessionSysUser(){
		return (SysUser) getSession().getAttribute("sysUser");
	}


	/**
	 * 获取session
	 *
	 * @return
     */
	public Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
	
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
		LogUtils.saveSysLog(request,  null, ex, ex.getMessage());
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
	 * 将结果以JSON格式输出。.
	 *
	 * @param data
	 *            需要进行JSON转换的数据对象。
	 * @return 响应实体。
	 */
	protected ResponseEntity<String> renderJson(Object data) {
		return renderJsonp(data, null);
	}

	/**
	 * 跨站访问将结果以JSONP格式输出。.
	 *
	 * @param data
	 *            需要进行JSON转换的数据对象。
	 * @param callback
	 *            是否是跨站AJAX请求，有该参数则为跨站请求。
	 * @return 响应实体。
	 */
	protected ResponseEntity<String> renderJsonp(Object data, String callback) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String responseData = jsonUtils.toJsonByCallback(data, callback);
		return new ResponseEntity<String>(responseData, headers, HttpStatus.OK);
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

}