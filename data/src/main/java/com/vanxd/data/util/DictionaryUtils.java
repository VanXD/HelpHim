package com.vanxd.data.util;

import com.vanxd.data.dict.Dictionary;

/**
 * @author wyd on 2016/9/23.
 */
public class DictionaryUtils {
    /**
     * 获得所有枚举类型
     * @param clazz
     * @return
     */
    public static Dictionary[] getDictionaries(Class<?> clazz) {
        Dictionary[] dictionaries = null;
        if(null != clazz) {
            dictionaries = (Dictionary[]) clazz.getEnumConstants();
        }
        return dictionaries;
    }

    /**
     * 根据枚举类名获得所有该类下的所有枚举类型
     * @param className 枚举类名
     * @return
     */
    public static Dictionary[] getDictionaries(String className) {
        Dictionary[] dictionaries = null;
        Class<?> clazz = getDictClass(className);
        if(null != clazz) {
            dictionaries = (Dictionary[]) clazz.getEnumConstants();
        }
        return dictionaries;
    }

    /**
     * 获得枚举类的全路径
     * @param className 类名
     * @return
     */
    public static String getDictCompleteClassName(String className) {
        return "com.vanxd.data.dict." + className;
    }

    /**
     * 使用枚举类的类名实例化该类
     * @param className 枚举类名
     * @return
     */
    public static Class<?> getDictClass(String className) {
        try {
            return Class.forName(getDictCompleteClassName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
