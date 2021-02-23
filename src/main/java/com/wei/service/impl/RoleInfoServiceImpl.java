package com.wei.service.impl;

import com.wei.mapper.RoleInfoMapper;
import com.wei.pojo.RoleInfo;
import com.wei.service.RoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MyBatis Generator工具自动生成
 */
@Service
public class RoleInfoServiceImpl extends BaseServiceImpl<RoleInfo, Integer> implements RoleInfoService {
    @Autowired
    private RoleInfoMapper roleInfoMapper;

    public RoleInfoServiceImpl(RoleInfoMapper mapper) {
        this.roleInfoMapper= mapper;
        init(roleInfoMapper);
    }

    public RoleInfo selectByRoleInfo(RoleInfo roleInfo){
        return roleInfoMapper.selectByRoleInfo(roleInfo);
    }

    public int updateByRoleInfo(RoleInfo roleInfo){
        return roleInfoMapper.updateByRoleInfo(roleInfo);
    }

    public List<RoleInfo> getRoleInfoList(RoleInfo roleInfo){
        return roleInfoMapper.getRoleInfoList(roleInfo);
    }

    public RoleInfo testSelect(String id){
        System.out.println("-=-=123-=-=");
        return selectByPrimaryKey(Integer.parseInt(id));
    }
}