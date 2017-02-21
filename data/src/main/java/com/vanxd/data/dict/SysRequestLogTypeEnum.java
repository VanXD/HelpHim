package com.vanxd.data.dict;

/**
 * 日志类型
 * @author wyd on 2017/2/21.
 */
public enum SysRequestLogTypeEnum implements Dictionary {
    REQUEST(1, "请求日志"),

    EXCEPTION(2, "异常日志");

    /**
     * 带参数构建函数.
     *
     * @param code
     *            the code
     * @param text
     *            the 文字描述
     */
    private SysRequestLogTypeEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    /** 状态码. */
    private Integer code;

    /** 文字描述. */
    private String text;


    /**
     * Gets the 文字描述.
     *
     * @return the 文字描述
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * Gets the 状态码.
     *
     * @return the 状态码
     */
    @Override
    public Integer getCode(){
        return code;
    }
}
