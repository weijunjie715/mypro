package com.wei.service;

import com.wei.pojo.SysErrorLog;

import java.util.Map;

/**
 * MyBatis Generator工具自动生成
 */
public interface SysErrorLogService extends BaseService<SysErrorLog, Integer> {

    void saveErrorLog(Map<String, Object> map);

    void saveErrorLog(String name, String errorMsg, String type);
}