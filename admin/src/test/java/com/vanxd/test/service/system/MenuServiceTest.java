package com.vanxd.test.service.system;

import com.vanxd.admin.service.system.MenuService;
import com.vanxd.data.vo.system.MenuTreeVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spring.SpringTestParent;

import java.util.List;

/**
 * @author wyd on 2016/9/9.
 */
public class MenuServiceTest extends SpringTestParent{
    @Autowired
    private MenuService menuServiceImpl;

    @Test
    public void test() {
        List<MenuTreeVO> menu = menuServiceImpl.getMenu();
        int a = 1;
    }
}
