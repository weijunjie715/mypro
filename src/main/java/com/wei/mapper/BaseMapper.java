
package com.wei.mapper;

import com.wei.pojo.BaseBean;

/**
 * @ClassName BaseMapper
 * 描述 :
 * @Author weijunjie
 * @Date 2020/6/24 16:14
 */
@SuppressWarnings("all")
public interface BaseMapper<T extends BaseBean,PK> {
    int deleteByPrimaryKey(PK id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(PK id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
