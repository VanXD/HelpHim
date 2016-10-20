package com.vanxd.admin.controller.system;

import com.vanxd.admin.controller.BaseController;
import com.vanxd.admin.service.user.SysUserRoleService;
import com.vanxd.data.component.RespJSON;
import com.vanxd.data.entity.user.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wyd on 2016/10/20.
 */
@Controller
@RequestMapping("/system/userRole")
public class SysUserRoleController extends BaseController<SysUserRole, SysUserRoleService> {
    @Autowired
    private SysUserRoleService sysUserRoleServiceImpl;

    @Override
    protected SysUserRoleService getService() {
        return sysUserRoleServiceImpl;
    }

    /**
     * 根据用户ID和角色ID，解除关联
     * @param userId 必须 用户ID
     * @param roleId 必须 角色ID
     * @return
     */
    @RequestMapping("/cancelRelation.json")
    @ResponseBody
    public RespJSON cancelRelation(String userId, String roleId) {
        if(StringUtils.isEmpty(userId)) {
            return RespJSON.generator(RespJSON.RespCode.PARAM_ILLEAGUE);
        }
        if(StringUtils.isEmpty(roleId)) {
            return RespJSON.generator(RespJSON.RespCode.PARAM_ILLEAGUE);
        }
        if(sysUserRoleServiceImpl.cancelRelation(userId, roleId)) {
            return RespJSON.generator(RespJSON.RespCode.SUCCESS);
        } else {
            return RespJSON.generator(RespJSON.RespCode.FAIL);
        }
    }

    @RequestMapping("/listAndChecked.json")
    @ResponseBody
    public RespJSON listAndChecked(String userId) {
        if(StringUtils.isEmpty(userId)) {
            return new RespJSON(RespJSON.RespCode.PARAM_ILLEAGUE);
        }
        return new RespJSON(sysUserRoleServiceImpl.findByUserIdAndChecked(userId));
    }
}
