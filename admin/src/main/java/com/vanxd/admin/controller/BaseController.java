package com.vanxd.admin.controller;

import com.alibaba.fastjson.JSON;
import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.service.BaseService;
import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.component.RespJSON;
import com.vanxd.data.component.jqgrid.Filter;
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

    /**
     * 根据条件进行数据列表查询，
     * condition和filters都有的情况下，默认使用filters(取决于mapper.xml如何写)
     * @param condition     可为空 数据实体作为参数
     * @param filters       可为空 符合jqGrid多条件查询的json字符串作为参数
     * @param pagination    可为空 分页对象，为空查询全部
     * @return
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public PageResult<T> list(T condition, String filters, Pagination pagination) {
        Filter filter = JSON.parseObject(filters, Filter.class);
        PageResult<T> pageResult = getService().page(condition, filter, pagination);
        return pageResult;
    }

    /**
     * 打开列表页
     * @param mv
     * @param condition
     * @param pagination
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView page(ModelAndView mv, T condition, Pagination pagination) {
        pageView(mv, condition, pagination);
        return mv;
    }

    /**
     * 分页列表展示页,
     * 默认返回页面为 page+控制器名称去掉Controller 例如 pageSystemPermission
     * @param mv
     * @param condition
     * @param pagination
     * @return
     */
    protected void pageView(ModelAndView mv, T condition, Pagination pagination) {
        String simpleName = this.getClass().getSimpleName();
        // 移除类名后面的Controller
        String controllerName = simpleName.substring(0, simpleName.length() - 10);
        String requestURI = getRequest().getRequestURI();
        mv.setViewName(requestURI + controllerName);
    }


    /**
     * 编辑数据
     * @param entity    必须  实体数据
     * @return
     */
    @RequestMapping(value = "/edit.json", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON<RespJSON> edit(T entity) {
        if(getService().edit(entity)) {
            return new RespJSON<RespJSON>(RespJSON.RespCode.SUCCESS);
        } else {
            return new RespJSON<RespJSON>(RespJSON.RespCode.FAIL);
        }
    }

    /**
     * 通过ID获得对象数据
     * @param id    必须 对象ID
     * @return
     */
    @RequestMapping(value = "/getById.json", method = RequestMethod.GET)
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

    /**
     * 通过ID删除对象
     * @param id    必须 对象ID
     * @return
     */
    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON delete(String id) {
        if(StringUtils.isEmpty(id)) {
            throw new BusinessException("参数不正确！");
        }
        if(getService().deleteSoftlyByPrimaryKey(id) > 0) {
            return new RespJSON(RespJSON.RespCode.SUCCESS);
        } else {
            return new RespJSON(RespJSON.RespCode.FAIL);
        }
    }
}
