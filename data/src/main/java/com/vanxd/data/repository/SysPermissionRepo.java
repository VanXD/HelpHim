package com.vanxd.data.repository;

import com.vanxd.data.entity.user.SysPermission;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by wyd on 2016/8/25.
 */
public interface SysPermissionRepo extends PagingAndSortingRepository<SysPermission, String> {
}
