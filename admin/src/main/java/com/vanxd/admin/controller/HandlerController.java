package com.vanxd.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基类控制器.
 */
public abstract class HandlerController {
	@Autowired(required = false)
	private HttpServletRequest request;

	/** The Constant ERROR. */
	protected static final String ERROR = "error/error";
	
	/** The Constant SUCCESS. */
	protected static final String SUCCESS = "success";

	/**
	 * 自动转换日期类型的字段格式
	 * 
	 * @param binder binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

	public HttpServletRequest getRequest() {
		return request;
	}
}