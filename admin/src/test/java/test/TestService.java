package test;

import com.vanxd.data.entity.SysRole;
import com.vanxd.data.entity.SysUser;
import com.vanxd.data.repository.SysRoleRepository;
import com.vanxd.data.repository.SysUserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import spring.SpringTest;

/**
 * Created by wejoy-a on 2016/6/30.
 */
public class TestService extends SpringTest {
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
