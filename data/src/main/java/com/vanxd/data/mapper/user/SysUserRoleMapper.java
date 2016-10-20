package com.vanxd.data.mapper.user;

import com.vanxd.data.entity.user.SysUserRole;
import com.vanxd.data.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 删除{userId}和{roleId}关联的数据，状态标记为-9
     * @param userId
     * @param roleId
     * @return
     */
    int deleteByUserIdAndRoleId(@Param(value = "userId") String userId, @Param(value = "roleId") String roleId);
}