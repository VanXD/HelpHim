package com.vanxd.admin.service.system;

import com.vanxd.data.vo.MenuTreeVO;

import java.util.List;

/**
 * @author wyd on 2016/9/9.
 */
public interface MenuService {
    List<MenuTreeVO> getMenu();
}
