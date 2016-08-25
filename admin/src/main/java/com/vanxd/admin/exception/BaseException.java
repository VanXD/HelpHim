package com.vanxd.admin.exception;

/**
 * 基础异常类
 */
public class BaseException extends RuntimeException {

    //所属模块
    private String module;

    private Throwable t;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String message;


    public BaseException(String module, Throwable t, Object[] args, String message) {
        this.module = module;
        this.t = t;
        this.args = args;
        this.message = message;
    }

    public BaseException(String module, Throwable t, Object[] args) {
        this(module, t, args, null);
    }

    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BaseException(String message, Throwable t){
        this(null, t, null, message);
    }

    public BaseException(Throwable t, Object[] args) {
        this(null, t, args, null);
    }

    public BaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }


    public String getModule() {
        return module;
    }

    public Throwable getT() {
        return t;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.getClass() + "{" +
                "module='" + module + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
