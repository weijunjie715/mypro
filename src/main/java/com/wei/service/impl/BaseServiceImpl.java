package com.wei.service.impl;

import com.wei.exception.HandleDataException;
import com.wei.mapper.BaseMapper;
import com.wei.pojo.BaseBean;
import com.wei.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName BaseServiceImpl
 * 描述 : service通用请求方法 （mybatis-generator插件已封装）
 * @Author weijunjie
 * @Date 2020/6/28 13:32
 */
@SuppressWarnings("all")
public class BaseServiceImpl<T extends BaseBean,PK> implements BaseService<T,PK> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private BaseMapper baseMapper;

    //在子类构造函数中调用，指明具体的dao层
    public void init(BaseMapper mapper){
        baseMapper=mapper;
    }

    public int deleteByPrimaryKey(PK id){
        if(null != id){
            return baseMapper.deleteByPrimaryKey(id);
        }else{
            logger.info("传入数据为null，删除数据失败");
            return 0;
        }
    }

    public int insert(T record){
        try {
            if(null != record){
                return baseMapper.insert(record);
            }else{
                logger.info("传入数据为null，插入数据失败");
                return 0;
            }
        }catch (Exception e){
            throw new HandleDataException("插入数据异常");
        }

    }

    public int insertSelective(T record){
        if(null != record){
            return baseMapper.insertSelective(record);
        }else{
            logger.info("传入数据为null，插入数据失败");
            return 0;
        }
    }

    public T selectByPrimaryKey(PK id){
        BaseBean baseBean = baseMapper.selectByPrimaryKey(id);
        if(baseBean == null){
            logger.info("数据库查询数据为null");
            return null;
        }else{
            return (T)baseBean;
        }
    }

    public int updateByPrimaryKeySelective(T record){
        if(null != record){
            return baseMapper.updateByPrimaryKeySelective(record);
        }else{
            logger.info("传入数据为null，插入数据失败");
            return 0;
        }
    }

    public int updateByPrimaryKey(T record){
        if(null != record){
            return baseMapper.updateByPrimaryKey(record);
        }else{
            logger.info("传入数据为null，插入数据失败");
            return 0;
        }
    }
}
