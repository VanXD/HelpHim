package com.vanxd.admin.service;

import com.base.core.domain.Pageable;
import com.wjzp.v2gogo.admin.dict.Status;
import com.wjzp.v2gogo.data.repository.BaseMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 针对具有CRUD且有审核流程的模块
 *
 * 提供简单的删、改、分页查、审核、发布流程的默认实现
 * 提供简单的数据审核、发布实现
 * 保存到数据库(add)方法请重写
 * Created by wyd on 2016/4/1.
 */
public abstract class ModuleAbstractService<T> extends BaseService<T>{
    /**
     * 数据库持久层操作对象
     */
    protected BaseMapper baseMapper;

    /**
     * 实现类注入自己的持久层对象(mapper)
     * @param baseMapper 基础mapper
     */
    protected abstract void setBaseMapper(BaseMapper baseMapper);

    /**
     * 获取总数.
     *
     * @param queryCondition 查询条件对象
     * @return the total
     */
    @Override
    public Long getTotal(T queryCondition) {
        Map<String, Object> map = new HashMap<>();
        map.put(conditionsName, queryCondition);
        return baseMapper.getCount(map);
    }

    /**
     * 通过条件分页查找数据集合
     *
     * @param queryCondition 查询条件对象
     * @param pageable       分页对象
     * @return
     */
    @Override
    public List<T> findList(T queryCondition, Pageable pageable) {
        Map<String, Object> map = new HashMap<>();
        map.put(conditionsName, queryCondition);
        map.put(pageableName, pageable);
        return baseMapper.getByPage(map);
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @Override
    public boolean update(T entity) {
        return baseMapper.updateByPrimaryKeySelective(entity) > 0;
    }

    /**
     * 删除，默认真删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        return baseMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    @Override
    public T getById(String id) {
        return (T) baseMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新数据状态
     * @param id
     * @param status
     * @return
     */
    public abstract T updateStatus(String id, Integer status);


    /**
     * 审核
     * @param id
     * @return
     */
    public T audit(String id) {
        return updateStatus(id, Status.AUDIT.getCode());
    }

    /**
     * 取消审核
     * @param id
     * @return
     */
    public T unaudit(String id) {
        return updateStatus(id, Status.NEW.getCode());
    }

    /**
     * 发布
     * @param id
     * @return
     */
    public T publish(String id) {
        return updateStatus(id, Status.RELEASE.getCode());
    }

    /**
     * 取消发布
     * @param id
     * @return
     */
    public T unpublish(String id) {
        return updateStatus(id, Status.AUDIT.getCode());
    }

    /**
     * 单图上传 保存/修改
     * @param t
     * @param imgFile
     * @return
     */
    public abstract boolean add(T t, MultipartFile imgFile);

    /**
     * 多图上传 保存/修改
     * @param t
     * @param imgFiles
     * @return
     */
    public abstract boolean add(T t, MultipartFile[] imgFiles);
}
