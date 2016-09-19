package com.vanxd.data.dict;

/**
 * 枚举类的字典接口
 * @author wyd on 2016/9/19.
 */
public interface Dictionary {
    /**
     * Gets the 文字描述.
     *
     * @return the 文字描述
     */
    String getText();

    /**
     * Gets the 状态码.
     *
     * @return the 状态码
     */
    Integer getCode();
}
