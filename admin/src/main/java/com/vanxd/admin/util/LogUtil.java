package com.vanxd.admin.util;

import org.apache.log4j.Logger;

/**
 * @author wyd on 2017/2/21.
 */
public class LogUtil {
    public static void errorLog(Class clazz, String msg) {
        Logger.getLogger(clazz).error(msg);
    }

    public static void debug(Class clazz, String msg) {
        Logger.getLogger(clazz).debug(msg);
    }
}
