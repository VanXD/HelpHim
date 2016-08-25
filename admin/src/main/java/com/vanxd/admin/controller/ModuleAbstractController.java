package com.vanxd.admin.controller;

import com.vanxd.admin.service.ModuleAbstractService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 针对管理功能的Controller，审核、发布 CRUD的都可以继承这个类
 *
 * 提供常用的请求地址：list, load,审核，发布等
 * *********************************************************************************************************************************
 * 需要注意save方法。因为不知道是没图、单图或多图保存
 * 大部分保存都有图，所以save是单图上传保存
 * 针对load方法有多参数情况：因为大部分Input页面的action都是save所以我们这里需要在页面的action部分写成这样，
 * *********************************************************************************************************************************
 * 修改：
 * input页面的查询对象都是entity
 * *********************************************************************************************************************************
 * Created by wyd on 2016/4/5.
 */
public abstract class ModuleAbstractController<T> extends BaseController{
    protected ModuleAbstractService<T> moduleAbstractService;

    /**
     * 实现类注入自己的主要业务对象
     * @param moduleAbstractService
     */
    public abstract void setModuleAbstractService(ModuleAbstractService<T> moduleAbstractService);

    /**
     * 分页获取数据对象
     * @param mv
     * @param queryCondition
     * @param pageable
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(":list")
    public ModelAndView list (ModelAndView mv, T queryCondition, Pageable pageable) {
        PageBean<T> pageBean = moduleAbstractService.getPageBean(queryCondition, pageable);
        mv.addObject("page", pageBean);
        mv.addObject("queryCondition", queryCondition);
        return listView(mv, queryCondition, pageable);
    }

    /**
     * 列表页跳转地址
     * @return
     */
    protected abstract ModelAndView listView(ModelAndView mv, T queryCondition, Pageable pageable);

    /**
     * 内容显示模板
     * @param mv 为了方便IDE点击跳转，使用这个
     * @param id
     * @return
     */
    @RequiresPermissions(":add")
    @RequestMapping("/load")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView load(ModelAndView mv, String id) {
        T t = null;
        if (!StringUtils.isEmpty(id)) {
            t = moduleAbstractService.getById(id);
            mv.addObject("entity", t);
        }
        return loadView(mv, t);
    }

    /**
     * input页加载地址
     * @return
     */
    protected abstract ModelAndView loadView(ModelAndView mv, T t);

    /**
     * 单图上传，保存或修改项目
     * @param imgFile 项目大图
     * @return
     */
    @RequiresPermissions(":add")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public String save(T t,
                       @RequestParam(value = "imgFile", required = true) MultipartFile imgFile) {
        try {
            if (moduleAbstractService.add(t, imgFile)) {
                return saveView(t);
            } else {
                throw new BusinessException("添加失败！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 保存后跳转地址
     * @return
     */
    protected String saveView(T t) {
        return "redirect:list";
    }

    /**
     * 保存或修改项目
     * @return
     */
    @RequiresPermissions(":add")
    @RequestMapping(value = "/saveNoPic", method = RequestMethod.POST)
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public String saveNoPic(T t) {
        try {
            if (moduleAbstractService.add(t)) {
                return saveView(t);
            } else {
                throw new BusinessException("添加失败！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
    /**
     * 单图上传，保存或修改项目
     * @param imgFiles 多张图片
     * @return
     */
    @RequiresPermissions(":add")
    @RequestMapping(value = "/saveMultiPic", method = RequestMethod.POST)
    @AvoidDuplicateSubmission(needRemoveToken = true)
    public String saveMulPic(T t,
                             @RequestParam(value = "imgFiles", required = true) MultipartFile[] imgFiles) {
        try {
            if (moduleAbstractService.add(t, imgFiles)) {
                return saveView(t);
            } else {
                throw new BusinessException("添加失败！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 审核
     * @param id
     * @param pageable
     * @return
     */
    @RequiresPermissions(":audit")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON audit(String id, Pageable pageable) {
        try {
            if(StringUtils.isNotEmpty(id)) {
                if(moduleAbstractService.audit(id) != null){
                    return new RespJSON(RespJSON.RespCode.SUCCESS);
                } else {
                    return new RespJSON(RespJSON.RespCode.FAIL);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RespJSON(e);
        }
        return new RespJSON(RespJSON.RespCode.FAIL);
    }

    /**
     * 取消审核
     * @param id
     * @param pageable
     * @return
     */
    @RequiresPermissions(":unaudit")
    @RequestMapping(value = "/unaudit", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON unaudit(String id, Pageable pageable) {
        try {
            if(StringUtils.isNotEmpty(id)) {
                if(moduleAbstractService.unaudit(id) != null){
                    return new RespJSON(RespJSON.RespCode.SUCCESS);
                } else {
                    return new RespJSON(RespJSON.RespCode.FAIL);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RespJSON(e);
        }
        return new RespJSON(RespJSON.RespCode.FAIL);
    }

    /**
     * 发布
     * @param id
     * @param pageable
     * @return
     */
    @RequiresPermissions(":publish")
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON publish(String id, Pageable pageable) {
        try {
            if(StringUtils.isNotEmpty(id)) {
                if(moduleAbstractService.publish(id) != null){
                    return new RespJSON(RespJSON.RespCode.SUCCESS);
                } else {
                    return new RespJSON(RespJSON.RespCode.FAIL);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RespJSON(e);
        }
        return new RespJSON(RespJSON.RespCode.FAIL);
    }

    /**
     * 取消发布
     * @param id
     * @param pageable
     * @return
     */
    @RequiresPermissions(":unpublish")
    @RequestMapping(value = "/unpublish", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON unpublish(String id, Pageable pageable) {
        try {
            if(StringUtils.isNotEmpty(id)) {
                if(moduleAbstractService.unpublish(id) != null){
                    return new RespJSON(RespJSON.RespCode.SUCCESS);
                } else {
                    return new RespJSON(RespJSON.RespCode.FAIL);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RespJSON(e);
        }
        return new RespJSON(RespJSON.RespCode.FAIL);
    }

    /**
     * 删除
     * @param id
     * @param pageable
     * @return
     */
    @RequiresPermissions(":delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public RespJSON delete(String id, Pageable pageable) {
        try {
            if(StringUtils.isNotEmpty(id)) {
                boolean result = moduleAbstractService.delete(id);
                if(result){
                    return new RespJSON(RespJSON.RespCode.SUCCESS);
                }else{
                    return new RespJSON(RespJSON.RespCode.FAIL);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RespJSON(e);
        }
        return new RespJSON(RespJSON.RespCode.FAIL);
    }

    /**
     * 详情
     *
     * @param mv
     * @param id 主键
     * @return
     */
    @RequiresPermissions(":detail")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(ModelAndView mv, String id) {
        T t = null;
        if (!StringUtils.isEmpty(id)) {
            t = moduleAbstractService.getById(id);
            mv.addObject("entity", t);
        }
        return detailView(mv, t);
    }

    /**
     * 详情页加载地址
     * @return
     */
    protected abstract ModelAndView detailView(ModelAndView mv, T t);

}
