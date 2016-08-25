package com.vanxd.admin.exception;

/**
 * 登陆权限异常.
 */
public class AuthException extends BaseException {

	private static final long serialVersionUID = -8105404293888960392L;

	public AuthException(final Throwable t, final Object[] args) {
		super("AuthException", t, args);
	}

	public AuthException(final String message){
		super("AuthException", message);
	}

	public AuthException(final String message, final Throwable t){
		super(message, t);
	}

}
