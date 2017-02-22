package com.vanxd.admin.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * 数据源配置
 * @author wyd on 2017/2/9.
 */
@ConfigurationProperties(prefix="datasource")
public class DataSourceProperties {
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 驱动类 */
    private String driverClass;
    /** 连接地址 */
    private String url;

    public DataSourceProperties() {
    }

    public DataSourceProperties(String username, String password, String driverClass, String url) {
        this.username = username;
        this.password = password;
        this.driverClass = driverClass;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
