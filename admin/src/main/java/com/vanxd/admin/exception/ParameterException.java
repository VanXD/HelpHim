package com.vanxd.admin.exception;

/**
 * 参数异常.
 */
public class ParameterException extends BaseException {
	
	public ParameterException(final Throwable t, final Object[] args) {
		super("ParameterException", t, args);
	}

	public ParameterException(final String message){
		super("ParameterException", message);
	}
}
