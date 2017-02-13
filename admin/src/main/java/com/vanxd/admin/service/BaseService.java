package com.vanxd.admin.service;

import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.component.jqgrid.JqFilter;
import com.vanxd.data.entity.BaseEntity;
import com.vanxd.data.mapper.BaseMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wyd on 2016/9/8.
 */
public interface BaseService<T extends BaseEntity, Mapper extends BaseMapper<T>> {
    /**
     * 将实体当作筛选条件，进行分页查询
     *
     * @param conditions 实体
     * @param pagination 必须 分页对象
     * @return 分页对象结果
     */
    PageResult<T> page(T conditions, JqFilter jqFilter, Pagination pagination);

    /**
     * 将实体当作筛选条件，获得总数
     * @param conditions 实体
     * @return 总数
     */
    Long count(T conditions, JqFilter jqFilter);

    /**
     * 将实体当作筛选条件，进行分页查询
     * 在这里分页对象可以为null
     * @param conditions 实体
     * @param pagination 可为空 分页对象
     * @return 分页后的数据列表
     */
    List<T> list(T conditions, JqFilter jqFilter, Pagination pagination);

    /**
     * 由具体类实现，获得相应实体的Mapper对象
     * @return
     */
    Mapper getMapper();

    @Transactional
    int deleteByPrimaryKey(String id);

    @Transactional
    int save(T entity);

    @Transactional
    int saveSelective(T entity);

    T findByPrimaryKey(String id);

    @Transactional
    int updateByPrimaryKeySelective(T entity);

    @Transactional
    int updateByPrimaryKey(T entity);

    @Transactional
    boolean edit(T entity);

    /**
     * 软删除，status改为 StatusEnum.DELETED
     * @param id    主键
     * @return
     */
    @Transactional
    int deleteSoftlyByPrimaryKey(String id);
}
