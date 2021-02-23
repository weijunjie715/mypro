package com.wei.service;


import com.wei.pojo.RoleInfo;

import java.util.List;

/**
 * MyBatis Generator工具自动生成
 */
public interface RoleInfoService extends BaseService<RoleInfo, Integer> {

    RoleInfo selectByRoleInfo(RoleInfo roleInfo);

    int updateByRoleInfo(RoleInfo roleInfo);

    List<RoleInfo> getRoleInfoList(RoleInfo roleInfo);

    RoleInfo testSelect(String id);
}