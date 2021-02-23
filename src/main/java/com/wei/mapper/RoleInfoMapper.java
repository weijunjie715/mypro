package com.wei.mapper;

import com.wei.pojo.RoleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis Generator工具自动生成
 */
@Repository
public interface RoleInfoMapper extends BaseMapper<RoleInfo, Integer> {

    RoleInfo selectByRoleInfo(RoleInfo roleInfo);

    List<RoleInfo> getRoleInfoList(RoleInfo roleInfo);

    int updateByRoleInfo(RoleInfo roleInfo);
}