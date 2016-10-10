package com.vanxd.data.mapper;

import com.vanxd.data.component.Pagination;
import com.vanxd.data.component.jqgrid.Filter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyd on 2016/9/7.
 */
public interface BaseMapper<T> {
    int deleteByPrimaryKey(String id);

    int insert(T entity);

    int insertSelective(T entity);

    T selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(T entity);

    int updateByPrimaryKey(T entity);

    List<T> page(@Param("conditions")T t, @Param("filter") Filter filter, @Param("pagination")Pagination pagination);

    Long count(@Param("conditions")T t, @Param("filter") Filter filter);
}
