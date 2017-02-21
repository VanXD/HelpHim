package com.vanxd.data.entity.user;

import com.vanxd.data.annotation.TableAlias;
import com.vanxd.data.entity.BaseEntity;

/**
 * 系统日志
 */
@TableAlias(alias = "srl")
public class SysRequestLog extends BaseEntity{
    /** 类型：1：请求日志 2：异常日志 */
    private Byte type;
    /** 日志标题 */
    private String title;
    /** 请求IP */
    private String remoteIp;
    /** 请求参数 */
    private String requestParam;
    /** 请求浏览器user-agent */
    private String userAgent;
    /** 创建人 */
    private String creatorUserId;
    /** 异常信息 */
    private String exception;

    /** [VO]创建人昵称  */
    @TableAlias(isRequire = false)
    private String creatorNickname;

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}