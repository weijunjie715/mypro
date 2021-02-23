package com.wei.service;

import com.wei.pojo.UserInfo;
import com.wei.util.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * MyBatis Generator工具自动生成
 */
public interface UserInfoService extends BaseService<UserInfo, Integer> {

    UserInfo selectUserInfoByAccount(String accountNo);

    List<UserInfo> selectByUserInfo(UserInfo userInfo);

    List<UserInfo> getUserList(Map<String, String> queryMap, PageInfo pageInfo);

    int updateUserInfo(UserInfo userInfo);

    int updateByRoleCode(Integer isDel, String roleCode, String accountNo, String userStatus);

}