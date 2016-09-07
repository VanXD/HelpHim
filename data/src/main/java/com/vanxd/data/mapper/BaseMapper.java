package com.vanxd.data.mapper;

import com.vanxd.data.component.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    List<T> page(@Param("queryCondition")T t, @Param("Pagination")Pagination pagination);

    Long count(@Param("queryCondition")T t);
}
