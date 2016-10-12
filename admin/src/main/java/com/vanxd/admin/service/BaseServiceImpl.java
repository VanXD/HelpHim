package com.vanxd.admin.service;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.data.component.PageResult;
import com.vanxd.data.component.Pagination;
import com.vanxd.data.component.jqgrid.JqFilter;
import com.vanxd.data.dict.StatusEnum;
import com.vanxd.data.entity.BaseEntity;
import com.vanxd.data.mapper.BaseMapper;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 基础service实现类
 *
 * @author wyd on 2016/9/8.
 */
public abstract class BaseServiceImpl<T extends BaseEntity, Mapper extends BaseMapper<T>> implements BaseService<T, Mapper> {

    @Override
    public PageResult<T> page(T conditions, JqFilter jqFilter, Pagination pagination) {
        PageResult<T> pageResult = new PageResult<T>(pagination.getPage(), pagination.getPageSize());
        pageResult.setRecords(count(conditions, jqFilter));
        if(0 > pageResult.getRecords()) {
            return pageResult;
        } else {
            pageResult.setRows(list(conditions, jqFilter, pagination));
            return pageResult;
        }
    }

    @Override
    public boolean edit(T entity) {
        if(StringUtils.isEmpty(entity.getId())) {
            return save(entity) > 0;
        } else {
            return updateByPrimaryKeySelective(entity) > 0;
        }
    }

    @Override
    public Long count(T conditions, JqFilter jqFilter) {
        return getMapper().count(conditions, jqFilter);
    }

    @Override
    public List<T> list(T conditions, JqFilter jqFilter, Pagination pagination) {
        return getMapper().page(conditions, jqFilter, pagination);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int deleteSoftlyByPrimaryKey(String id) {
        T entity = findByPrimaryKey(id);
        if(null == entity) {
            throw new BusinessException("未获取到数据！");
        }
        entity.setStatus(StatusEnum.DELETED.getCode());
        return updateByPrimaryKeySelective(entity);
    }

    @Override
    public int save(T entity) {
        return getMapper().insert(entity);
    }

    @Override
    public int saveSelective(T entity) {
        return getMapper().insertSelective(entity);
    }

    @Override
    public T findByPrimaryKey(String id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        return getMapper().updateByPrimaryKey(entity);
    }
}
