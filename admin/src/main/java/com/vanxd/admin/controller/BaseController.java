package com.vanxd.admin.controller;

import com.vanxd.admin.service.BaseService;
import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.entity.BaseEntity;
import com.vanxd.data.entity.user.SysPermission;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wyd on 2016/9/9.
 */
public abstract class BaseController<T extends BaseEntity, Service extends BaseService> extends HandlerController {
    /**
     * 获得业务对象
     * @return
     */
    protected abstract Service getService();

    @RequestMapping("/list.json")
    @ResponseBody
    public PageResult<T> list(T condition, Pagination pagination) {
        PageResult<T> pageResult = getService().page(condition, pagination);
        return pageResult;
    }

    @RequestMapping("/page")
    public ModelAndView page(ModelAndView mv, T condition, Pagination pagination) {
//        PageResult<T> pageResult = getService().page(condition, pagination);
//        mv.addObject("pageResult", pageResult);
//        mv.addObject("condition", condition);
        pageView(mv, condition, pagination);
        return mv;
    }

    /**
     * 分页列表展示页
     * @param mv
     * @param condition
     * @param pagination
     * @return
     */
    protected void pageView(ModelAndView mv, T condition, Pagination pagination) {
        returnRequestUriPage(mv);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView mv, T condition) {
        T entity = null;
        if(!StringUtils.isEmpty(condition.getId())) {
            entity = (T) getService().findByPrimaryKey(condition.getId());
            mv.addObject("entity", entity);
        }
        editView(mv, entity);
        return mv;

    }

    protected void editView(ModelAndView mv, T entity) {
        returnRequestUriPage(mv);
    }

    private void returnRequestUriPage(ModelAndView mv) {
        String requestURI = getRequest().getRequestURI();
        mv.setViewName(requestURI);
    }
}
