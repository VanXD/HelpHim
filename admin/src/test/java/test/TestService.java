package test;

import com.vanxd.data.entity.user.SysRole;
import com.vanxd.data.entity.user.SysUser;
import com.vanxd.data.repository.SysRoleRepository;
import com.vanxd.data.repository.SysUserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import spring.SpringTestParent;

/**
 * Created by wejoy-a on 2016/6/30.
 */
public class TestService extends SpringTestParent {
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired
    private SysUserRepository sysUserRepository;


    @Test
    @Rollback(true)
    public void test() {
        SysUser sysUser = sysUserRepository.findOne("1");
        SysRole sysRole = sysRoleRepository.findOne("1");
        sysUser.removeRole(sysRole);
    }
}
