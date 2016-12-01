package com.vanxd.admin.controller;

import com.alibaba.fastjson.JSON;
import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.exception.ParameterException;
import com.vanxd.admin.service.BaseService;
import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.component.RespJSON;
import com.vanxd.data.component.jqgrid.JqFilter;
import com.vanxd.data.entity.BaseEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 抽象Controller的公共接口和权限
 * 接口名                权限名            备注
 * /page                :page             打开列表页面
 * /list.json           :page             列表页ajax获取数据的接口
 * /edit.json           :edit             编辑接口：新增或修改
 * /getById.json        :detail           详情接口
 * /delete.json         :delete           删除接口
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
    @RequiresPermissions(":page")
    @RequestMapping("/list.json")
    @ResponseBody
    public PageResult<T> list(T condition, String filters, Pagination pagination) {
        JqFilter jqFilter = JSON.parseObject(filters, JqFilter.class);
        setPoJoClazz(condition, jqFilter);
        PageResult<T> pageResult = getService().page(condition, jqFilter, pagination);
        return pageResult;
    }

    /**
     * 如果传了filter参数，则设置POJO的CLASS到Filter中，以便于设置表别名进行条件查询
     * @param condition POJO的对象
     * @param jqFilter    jqGrid过滤器
     */
    private void setPoJoClazz(T condition, JqFilter jqFilter) {
        if(jqFilter != null) {
            jqFilter.setPojoClazz(condition.getClass());
        }
    }

    /**
     * 打开列表页
     * @param mv
     * @param condition
     * @param pagination
     * @return
     */
    @RequiresPermissions(":page")
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
    @RequiresPermissions(":edit")
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
    @RequiresPermissions(":detail")
    @RequestMapping(value = "/getById.json", method = RequestMethod.GET)
    @ResponseBody
    public RespJSON getById(String id) {
        if(StringUtils.isEmpty(id)) {
            throw new ParameterException("参数不正确！");
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
    @RequiresPermissions(":delete")
    @RequestMapping(value = "/delete.json", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON delete(String id) {
        if(StringUtils.isEmpty(id)) {
            throw new ParameterException("参数不正确！");
        }
        if(getService().deleteSoftlyByPrimaryKey(id) > 0) {
            return new RespJSON(RespJSON.RespCode.SUCCESS);
        } else {
            return new RespJSON(RespJSON.RespCode.FAIL);
        }
    }
}
