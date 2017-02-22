package com.vanxd.admin.config.datasource;

/**
 * 数据源切换类
 *
 * @author wyd on 2017/2/22.
 */
public class DataSourceSwitcher {
    private static final ThreadLocal<DataSourceTypeEnum> contextHolder = new ThreadLocal<>();

    public static DataSourceTypeEnum getDatabaseType(){
        return contextHolder.get();
    }

    public static void setWriteType() {
        contextHolder.set(DataSourceTypeEnum.WRITE);
    }

    public static void setReadType() {
        contextHolder.set(DataSourceTypeEnum.READ);
    }
}
