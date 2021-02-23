package com.wei.service;

import com.wei.pojo.RolePower;

import java.util.HashMap;
import java.util.List;

/**
 * MyBatis Generator工具自动生成
 */
public interface RolePowerService extends BaseService<RolePower, Integer> {

    List<RolePower> selectRolePowerByUserCode(String userCode, String powerType);

    List<HashMap<String,String>> selectRolePowerByRoleCode(String roleCode, String powerType);

    int updateRolePower(List<RolePower> rolePowerList);
}