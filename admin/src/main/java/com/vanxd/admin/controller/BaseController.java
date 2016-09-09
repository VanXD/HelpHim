package com.vanxd.admin.controller;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.entity.BaseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wyd on 2016/9/9.
 */
public abstract class BaseController<T extends BaseEntity, Service extends BaseService> extends HandlerController {
    /**
     * 获得业务对象
     * @return
     */
    protected abstract Service getService();

    @RequestMapping("/page")
    public ModelAndView page(ModelAndView mv, T condition, Pagination pagination) {
        PageResult<T> pageResult = getService().page(condition, pagination);
        mv.addObject("pageResult", pageResult);
        mv.addObject("condition", condition);
        return pageView(mv, condition, pagination);
    }

    /**
     * 分页列表展示页
     * @param mv
     * @param condition
     * @param pagination
     * @return
     */
    protected abstract ModelAndView pageView(ModelAndView mv, T condition, Pagination pagination);


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView mv, T condition) {
        T entity = null;
        if(!StringUtils.isEmpty(condition.getId())) {
            entity = (T) getService().findByPrimaryKey(condition.getId());
            mv.addObject("entity", entity);
        }
        return editView(mv, entity);
    }

    protected abstract ModelAndView editView(ModelAndView mv, T entity);
}
