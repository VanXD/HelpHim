package com.vanxd.test.data.mapper;

import com.vanxd.data.mapper.BaseMapper;
import com.vanxd.test.data.entity.SysPermission;

public interface SysPermissionMapper extends BaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}