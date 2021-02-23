package com.wei.mapper;

import com.wei.pojo.RolePower;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * MyBatis Generator工具自动生成
 */
@Repository
public interface RolePowerMapper extends BaseMapper<RolePower, Integer> {

    List<RolePower> selectRolePowerByUserCode(@Param("userCode") String userCode,
                                              @Param("powerType") String powerType);

    List<HashMap<String,String>> selectRolePowerByRoleCode(@Param("roleCode") String roleCode,
                                                           @Param("powerType") String powerType);

    int clearRolePower(@Param("roleCode") String roleCode,
                       @Param("powerType") String powerType);

    int insertByBatch(@Param("rolePowers") List<RolePower> rolePowers);
}