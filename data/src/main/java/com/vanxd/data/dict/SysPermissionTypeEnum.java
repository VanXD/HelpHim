package com.vanxd.data.dict;

import com.vanxd.data.dict.Dictionary;

/**
 * @author wyd on 2016/9/9.
 */
public enum SysPermissionTypeEnum implements Dictionary{
    MODULE(1, "模块"),

    MENU(2, "菜单"),

    FUNCTION(3, "功能");

    /**
     * 带参数构建函数.
     *
     * @param code
     *            the code
     * @param text
     *            the 文字描述
     */
    private SysPermissionTypeEnum(Integer code, String text) {
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
