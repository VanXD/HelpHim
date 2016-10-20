package com.vanxd.admin.controller.system;

import com.vanxd.admin.service.system.MenuService;
import com.vanxd.data.vo.system.MenuTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author wyd on 2016/9/9.
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuServiceImpl;

    @RequestMapping("list")
    public String menu(Map map) {
        List<MenuTreeVO> menus = menuServiceImpl.getMenu();
        map.put("menus", menus);
        return "/system/menu/list";
    }
}
