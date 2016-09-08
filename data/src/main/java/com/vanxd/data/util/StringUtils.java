package com.vanxd.data.util;

import java.util.UUID;

/**
 * @author wyd on 2016/9/8.
 */
public class StringUtils {
    /**
     * 获取UUID
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
