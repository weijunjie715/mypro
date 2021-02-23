package com.wei.service.impl;

import com.wei.mapper.RolePowerMapper;
import com.wei.pojo.RolePower;
import com.wei.service.RolePowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * MyBatis Generator工具自动生成
 */
@Service
public class RolePowerServiceImpl extends BaseServiceImpl<RolePower, Integer> implements RolePowerService {
    @Autowired
    private RolePowerMapper rolePowerMapper;

    public RolePowerServiceImpl(RolePowerMapper mapper) {
        this.rolePowerMapper= mapper;
        init(rolePowerMapper);
    }

    public List<RolePower> selectRolePowerByUserCode(String userCode,String powerType){
        return rolePowerMapper.selectRolePowerByUserCode(userCode,powerType);
    }

    public List<HashMap<String,String>> selectRolePowerByRoleCode(String roleCode, String powerType){
        return rolePowerMapper.selectRolePowerByRoleCode(roleCode,powerType);
    }

    public int updateRolePower(List<RolePower> rolePowerList){
        //获取对应的列表数据
        if(rolePowerList.size()>0){
            String roleCode = rolePowerList.get(0).getRoleCode();
            String powerType = rolePowerList.get(0).getPowerType();
            //物理删除，清空该角色下原有的权限列表
            rolePowerMapper.clearRolePower(roleCode, powerType);
            //重新插入页面新指定的rolePowerList
            return rolePowerMapper.insertByBatch(rolePowerList);
        }else{
            return 0;
        }

    }
}