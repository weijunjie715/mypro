package com.wei.service.impl;

import com.wei.mapper.UserInfoMapper;
import com.wei.pojo.UserInfo;
import com.wei.service.UserInfoService;
import com.wei.util.PageHelperUtils;
import com.wei.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * MyBatis Generator工具自动生成
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Integer> implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoMapper mapper) {
        this.userInfoMapper= mapper;
        init(userInfoMapper);
    }

    public UserInfo selectUserInfoByAccount(String accountNo){
        return userInfoMapper.selectUserInfoByAccount(accountNo);
    }

    public List<UserInfo> getUserList(Map<String,String> queryMap,PageInfo pageInfo){
        PageHelperUtils.startPage(pageInfo);
        return userInfoMapper.getUserList(queryMap,pageInfo);
    }

    public List<UserInfo> selectByUserInfo(UserInfo userInfo){
        return userInfoMapper.selectByUserInfo(userInfo);
    }

    public int updateUserInfo(UserInfo userInfo){
        return userInfoMapper.updateUserInfo(userInfo);
    }

    public int updateByRoleCode(Integer isDel, String roleCode, String accountNo,String userStatus){
        return userInfoMapper.updateByRoleCode(isDel,roleCode,accountNo,userStatus);
    }

}