package com.wei.service;

import com.wei.pojo.BaseBean;

/**
 * @ClassName BaseService
 * 描述 :
 * @Author weijunjie
 * @Date 2020/6/28 13:31
 *
 */
@SuppressWarnings("all")
public interface BaseService<T extends BaseBean,PK> {
    int deleteByPrimaryKey(PK id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(PK id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

}
