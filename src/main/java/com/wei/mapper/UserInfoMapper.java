package com.wei.mapper;

import com.wei.pojo.UserInfo;
import com.wei.util.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * MyBatis Generator工具自动生成
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo, Integer> {

    UserInfo selectUserInfoByAccount(String accountNo);

    List<UserInfo> getUserList(@Param("queryMap") Map<String, String> queryMap,
                               @Param("page") PageInfo pageInfo);

    List<UserInfo> selectByUserInfo(UserInfo userInfo);

    int updateUserInfo(UserInfo userInfo);

    int updateByRoleCode(@Param("isDel") Integer isDel,
                         @Param("roleCode") String roleCode,
                         @Param("updateUser") String updateUser,
                         @Param("userStatus") String userStatus);

}