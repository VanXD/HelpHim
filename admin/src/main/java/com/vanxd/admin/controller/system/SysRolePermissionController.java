package com.vanxd.admin.controller.system;

import com.vanxd.admin.controller.BaseController;
import com.vanxd.admin.service.user.SysRolePermissionService;
import com.vanxd.data.component.RespJSON;
import com.vanxd.data.entity.user.SysRolePermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wyd on 2016/10/27.
 */
@Controller
@RequestMapping("/system/rolePermission")
@RequiresPermissions("system:role")
public class SysRolePermissionController extends BaseController<SysRolePermission, SysRolePermissionService> {
    @Autowired
    private SysRolePermissionService sysRolePermissionServiceImpl;
    @Override
    protected SysRolePermissionService getService() {
        return sysRolePermissionServiceImpl;
    }

    /**
     * 根据用户ID和角色ID，解除关联
     * @param roleId 必须 角色ID
     * @param permissionId 必须 用户ID
     * @return
     */
    @RequestMapping("/cancelRelation.json")
    @ResponseBody
    public RespJSON cancelRelation(String roleId, String permissionId) {
        if(StringUtils.isEmpty(roleId)) {
            return RespJSON.respCode(RespJSON.RespCode.PARAM_ILLEAGUE);
        }
        if(StringUtils.isEmpty(permissionId)) {
            return RespJSON.respCode(RespJSON.RespCode.PARAM_ILLEAGUE);
        }
        if(sysRolePermissionServiceImpl.cancelRelation(roleId, permissionId)) {
            return RespJSON.respCode(RespJSON.RespCode.SUCCESS);
        } else {
            return RespJSON.respCode(RespJSON.RespCode.FAIL);
        }
    }

    @RequestMapping("/listChecked.json")
    @ResponseBody
    public RespJSON listChecked(String roleId) {
        if(StringUtils.isEmpty(roleId)) {
            return new RespJSON(RespJSON.RespCode.PARAM_ILLEAGUE);
        }
        return RespJSON.successData(sysRolePermissionServiceImpl.findByRoleIdAndChecked(roleId));
    }

}
