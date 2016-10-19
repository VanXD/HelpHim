package com.vanxd.data.annotation;

import java.lang.annotation.*;

/**
 * 用来注明表的别名
 * 放在字段和类上，优先使用字段上的别名
 * @author wyd on 2016/10/11.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableAlias {
    /** 表别名 */
    String alias() default "";
    /** 是否必须 */
    boolean isRequire() default true;
}
