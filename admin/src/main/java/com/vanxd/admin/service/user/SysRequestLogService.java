package com.vanxd.admin.service.user;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.entity.user.SysPermission;
import com.vanxd.data.entity.user.SysRequestLog;
import com.vanxd.data.mapper.user.SysRequestLogMapper;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface SysRequestLogService extends BaseService<SysRequestLog, SysRequestLogMapper> {
    /**
     * 从{request}, {error}中提取相关的异常信息和请求信息，
     * 保存到数据库日志表中
     * @param request
     * @param error         异常信息
     * @param sysPermission
     * @return
     */
    int save(HttpServletRequest request, ResponseEntity<Map<String, Object>> error, SysPermission sysPermission);
}