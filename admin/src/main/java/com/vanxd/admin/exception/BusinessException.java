package com.vanxd.admin.exception;

/**
 * 业务层异常抛出.
 */
public class BusinessException extends BaseException {

	public BusinessException(final Throwable t, final Object[] args) {
		super("BusinessException", t, args);
	}

	public BusinessException(final String message){
		super("BusinessException", message);
	}

}
