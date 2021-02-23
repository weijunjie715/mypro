package com.wei.service.impl;

import com.wei.mapper.SysErrorLogMapper;
import com.wei.pojo.SysErrorLog;
import com.wei.service.SysErrorLogService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * MyBatis Generator工具自动生成
 */
@Service
public class SysErrorLogServiceImpl extends BaseServiceImpl<SysErrorLog, Integer> implements SysErrorLogService {
    @Autowired
    private SysErrorLogMapper sysErrorLogMapper;

    public SysErrorLogServiceImpl(SysErrorLogMapper mapper) {
        this.sysErrorLogMapper= mapper;
        init(sysErrorLogMapper);
    }

    /**
     * @Description 记录错误日志数据
     * @Author weijunjie
     * @Date 2020/7/28 9:15
     **/
    public void saveErrorLog(Map<String,Object> map){
        SysErrorLog sysErrorLog = new SysErrorLog();
        sysErrorLog.setCreateTime(new Date());
        sysErrorLog.setErrorException(MapUtils.getString(map,"exception"));
        sysErrorLog.setErrorMsg(MapUtils.getString(map,"message"));
        sysErrorLog.setErrorCode(MapUtils.getString(map,"status"));
        sysErrorLog.setErrorInterface(MapUtils.getString(map,"path"));
        sysErrorLog.setErrorType(MapUtils.getString(map,"error"));
        insert(sysErrorLog);
    }

    /**
     * @Description 记录错误日志数据
     * @Author weijunjie
     * @Date 2020/7/28 9:15
     **/
    public void saveErrorLog(String name,String errorMsg,String type){
        SysErrorLog sysErrorLog = new SysErrorLog();
        sysErrorLog.setErrorTime(new Date());
        sysErrorLog.setErrorMsg(errorMsg);
        sysErrorLog.setErrorInterface(name);
        sysErrorLog.setErrorType(type);
        insert(sysErrorLog);
    }
}