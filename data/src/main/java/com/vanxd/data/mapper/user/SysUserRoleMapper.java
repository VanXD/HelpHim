package com.vanxd.data.mapper.user;

import com.vanxd.data.entity.user.SysUserRole;
import com.vanxd.data.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 删除{userId}和{roleId}关联的数据，状态标记为-9
     * @param userId
     * @param roleId
     * @return
     */
    int deleteByUserIdAndRoleId(@Param(value = "userId") String userId, @Param(value = "roleId") String roleId);

    /**
     * 根据用户ID，获得用户的所有权限标识
     * @param userId    用户ID
     * @return
     */
    Set<String> selectPermissionsByUserId(String userId);
}