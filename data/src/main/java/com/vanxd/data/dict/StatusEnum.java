package com.vanxd.data.dict;

/**
 * @author wyd on 2016/9/8.
 */
public enum StatusEnum implements Dictionary{
    NEW(1, "新增"),

    AUDIT(2, "审核"),

    REALEASE(3, "发布"),

    DELETED(-9, "删除");


    /**
     * 带参数构建函数.
     *
     * @param code
     *            the code
     * @param text
     *            the 文字描述
     */
    private StatusEnum(Integer code, String text) {
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
