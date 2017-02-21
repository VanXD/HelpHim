package com.vanxd.admin.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.vanxd.admin.service.BaseServiceImpl;
import com.vanxd.admin.service.user.SysRequestLogService;
import com.vanxd.admin.util.GlobalKey;
import com.vanxd.data.dict.SysRequestLogTypeEnum;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRequestLog;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.mapper.user.SysRequestLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class SysRequestLogServiceImpl extends BaseServiceImpl<SysRequestLog, SysRequestLogMapper> implements SysRequestLogService {
    @Autowired
    private SysRequestLogMapper sysRequestLogMapper;

    @Override
    public SysRequestLogMapper getMapper() {
        return sysRequestLogMapper;
    }

    @Override
    public int save(HttpServletRequest request, ResponseEntity<Map<String, Object>> error, SysPermission sysPermission) {
        ServletContext servletContext = request.getServletContext();
        SysRequestLog sysRequestLog = new SysRequestLog();
        sysRequestLog.setCreatorUserId(((SysUser)servletContext.getAttribute(GlobalKey.REQUEST_EXCEPTION_USER)).getId());
        sysRequestLog.setType(SysRequestLogTypeEnum.EXCEPTION.getCode().byteValue());
        sysRequestLog.setTitle(sysPermission.getName());
        sysRequestLog.setRemoteIp(request.getRemoteAddr());
        sysRequestLog.setRequestParam(JSON.toJSONString(request.getParameterMap()));
        sysRequestLog.setException(JSON.toJSONString(error));
        sysRequestLog.setUserAgent(request.getHeader("user-agent"));
        return save(sysRequestLog);
    }
}
