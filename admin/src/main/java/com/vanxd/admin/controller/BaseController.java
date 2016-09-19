package com.vanxd.admin.controller;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.service.BaseService;
import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.component.RespJSON;
import com.vanxd.data.entity.BaseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping("/list.json")
    @ResponseBody
    public PageResult<T> list(T condition, Pagination pagination) {
        PageResult<T> pageResult = getService().page(condition, pagination);
        return pageResult;
    }

    @RequestMapping("/page")
    public ModelAndView page(ModelAndView mv, T condition, Pagination pagination) {
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


    @RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON<RespJSON> edit(T entity) {
        if(getService().edit(entity)) {
            return new RespJSON<RespJSON>(RespJSON.RespCode.SUCCESS);
        } else {
            return new RespJSON<RespJSON>(RespJSON.RespCode.FAIL);
        }
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public RespJSON getById(String id) {
        if(StringUtils.isEmpty(id)) {
            throw new BusinessException("参数不正确！");
        }
        T entity = (T) getService().findByPrimaryKey(id);
        if(null == entity) {
            return new RespJSON(RespJSON.RespCode.DATA_EMPTY);
        } else {
            return new RespJSON(entity);
        }
    }


    protected void editView(ModelAndView mv, T entity) {
        returnRequestUriPage(mv);
    }

    private void returnRequestUriPage(ModelAndView mv) {
        String requestURI = getRequest().getRequestURI();
        mv.setViewName(requestURI);
    }
}
