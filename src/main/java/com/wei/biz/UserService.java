package com.wei.biz;

import com.wei.dto.UserInfoDto;
import com.wei.dto.UserInfoPasswordDto;
import com.wei.util.PageInfo;
import com.wei.pojo.RoleInfo;
import com.wei.pojo.UserInfo;
import com.wei.vo.ResultVo;
import com.wei.vo.RolePowerVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName UserService
 * @Description : 用户操作逻辑业务层
 * @Author weijunjie
 * @Date 2020/8/6 16:28
 */
public interface UserService {

    UserInfo getUserInfo(String accountNo);

    String userLogin(String accountNo, String pwd);

    Set<String> getUserPowerList(String jwt);

    Map<String,Object> getToken(String token);

    String createJwt(UserInfo user);

    UserInfo getUserInfoByToken(String jwt);

    boolean refreshUserPower(String jwt);

    List<UserInfo> getUserList(Map<String, String> queryMap, PageInfo pageInfo);

    ResultVo addUser(UserInfoDto userInfo, String accountNo);

    int deleteUserInfo(String userCode, String accountNo);

    int updateUserInfo(UserInfoDto userInfoDto, String accountNo);

    int stopUser(String userCode, String status, String accountNo);

    int updateUserPassword(String accountNo, UserInfoPasswordDto userInfoPasswordDto);

    int delRole(String roleCode, String accountNo);

    int addRole(RoleInfo roleInfo, String accountNo);

    int stopRole(String roleCode, String status, String accountNo);

    List<RoleInfo> getRoleList(RoleInfo roleInfo);

    int updateRolePowerList(String roleCode, String moduleHandle, String accountNo);

    ResultVo updateRolePower(String params, String accountNo);

    ArrayList<RolePowerVo> getRolePowerList(String roleCode);

    void userLogout(String jwt);
}
