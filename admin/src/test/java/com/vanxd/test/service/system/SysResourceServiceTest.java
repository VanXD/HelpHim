package com.vanxd.test.service.system;

import com.vanxd.admin.service.system.SysResourceService;
import com.vanxd.data.dict.system.SRtypeEnum;
import com.vanxd.data.entity.system.SysResource;
import com.vanxd.data.util.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spring.SpringTestParent;

import java.util.Date;

/**
 * @author <a href="mailto:wuyaodong@lansent.com">wuyaodong</a> on 2016/9/9.
 */
public class SysResourceServiceTest extends SpringTestParent{
    @Autowired
    private SysResourceService sysResourceServiceImpl;

    @Test
    public void test() {
        SysResource sysResource = new SysResource();
        sysResource.setId(StringUtils.uuid());
        sysResource.setCreateTime(new Date());
        sysResource.setCreatorUserId("123");
        sysResource.setIcon("123");
        sysResource.setIsShow(true);
        sysResource.setName("系统管理");
        sysResource.setParentId("");
        sysResource.setPermissionIdentity("resource");
        sysResource.setStatus(1);
        sysResource.setType(SRtypeEnum.MODULE.getCode());
        sysResource.setWeight(0);
        sysResource.setUrl("");
        sysResourceServiceImpl.save(sysResource);

        

    }
}
