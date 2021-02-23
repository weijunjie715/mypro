package com.wei.biz.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wei.biz.UserService;
import com.wei.common.DbCommon;
import com.wei.common.RedisConstant;
import com.wei.dto.UserInfoDto;
import com.wei.dto.UserInfoPasswordDto;
import com.wei.enums.ModuleEnum;
import com.wei.enums.ResultEnum;
import com.wei.util.*;
import com.wei.pojo.RoleInfo;
import com.wei.pojo.RolePower;
import com.wei.pojo.UserInfo;
import com.wei.service.*;
import com.wei.vo.ResultVo;
import com.wei.vo.RolePowerVo;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName UserServiceImpl
 * @Description :
 * @Author weijunjie
 * @Date 2020/8/6 16:29
 */
@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String DEFAULT_PASSWORD = "123123";

    @Autowired
    private RedisUtils redisUtils;

    private JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    private RolePowerService rolePowerService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    private SysErrorLogService sysErrorLogService;

    @Override
    public UserInfo getUserInfo(String accountNo){
        return userInfoService.selectUserInfoByAccount(accountNo);
    }

    /**
     * @Description 用户登录操作
     * @Author: weijunjie
     * @Date: 2020/8/12 13:53
     * @return:
     **/
    @Override
    public String userLogin(String accountNo, String pwd){
        String s = "";
        UserInfo user = userInfoService.selectUserInfoByAccount(accountNo);
        if(null != user){
            boolean verify = MD5Util.verify(pwd,user.getPassword());
            if(verify){
                try {
                    s = redisUtils.get(RedisConstant.USER_JWT_TOKEN+user.getUserCode());
                    //获取redis缓存数据之后执行一次jwt有效性校验
                    if(!jwtUtil.checkToken(s)){
                        logger.info("token失效，重新签发token");
                        s = "";
                    }
                    if(StringUtils.isNotBlank(s)){
                        //校验token有效时间
                        Date tokenDate = jwtUtil.getExpirationDateFromToken(s);
                        long[] distanceTime = DateUtil.getDistanceTime(tokenDate, new Date());
                        if(distanceTime[1]<RedisConstant.REDIS_NUMBER_6){
                            logger.info("token有效时间不足，重新签发token");
                            s = "";
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(StringUtils.isNotBlank(s)){
                    //jwt生成成功，redis存储jwt数据  用户对应的权限数据（登录超时判断）
                    keepUserPowerList(user.getUserCode());
                }else{
                    //操作生成用户jwt返回前端
                    s = createJwt(user);
                    //jwt生成成功，redis存储jwt数据  用户对应的权限数据（登录超时判断）
                    keepUserPowerList(user.getUserCode());
                    keepUserJwt(user.getUserCode(),s);
                }
            }
        }
        return s;
    }

    /**
     * @Description 获取用户数据列表
     * @Author: weijunjie
     * @Date: 2020/8/14 15:41
     * @return:
     **/
    @Override
    public List<UserInfo> getUserList(Map<String,String> queryMap,PageInfo pageInfo){
        return userInfoService.getUserList(queryMap,pageInfo);
    }

    /**
     * @Description 删除用户数据
     * @Author: weijunjie
     * @Date: 2020/8/17 9:14
     **/
    @Override
    public int deleteUserInfo(String userCode,String accountNo){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserCode(userCode);
        userInfo.setIsDel(DbCommon.IS_DEL_1);
        userInfo.setUpdateUser(accountNo);
        redisUtils.delete(RedisConstant.USER_POWER_TOKEN + userCode);
        return userInfoService.updateUserInfo(userInfo);
    }

    /**
     * @author: chenzhm
     * @date: 2020/8/28 15:33
     * @Description: 更新用户数据
     */
    @Override
    public int updateUserInfo(UserInfoDto userInfoDto, String accountNo) {
        userInfoDto.setUpdateUser(accountNo);
        //添加用户对应机构信息
        int affectUserInfoRows = userInfoService.updateUserInfo(userInfoDto);
        return affectUserInfoRows;
    }

    /**
     * @Description 启停用户数据
     * @Author: weijunjie
     * @Date: 2020/8/17 9:14
     **/
    @Override
    public int stopUser(String userCode,String status,String accountNo){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserCode(userCode);
        userInfo.setUserStatus(status);
        userInfo.setUpdateUser(accountNo);
        if(status.equals(UserInfo.USER_STATUS_1)){
            keepUserPowerList(userCode);
        }else{
            redisUtils.delete(RedisConstant.USER_POWER_TOKEN + userCode);
        }
        return userInfoService.updateUserInfo(userInfo);
    }

    /**
     * @Description 修改用户密码
     * @Author: weijunjie
     * @Date: 2020/8/17 9:14
     **/
    @Override
    public int updateUserPassword(String accountNo,UserInfoPasswordDto userInfoPasswordDto){
        String oldPassword = userInfoPasswordDto.getOldPassword();
        String newPassword = userInfoPasswordDto.getNewPassword();
        String newPasswordConfirm = userInfoPasswordDto.getNewPasswordConfirm();

        if(StringUtils.isBlank(newPassword)) {
            throw new RuntimeException("新密码不能为空");
        }
        if(!newPassword.equals(newPasswordConfirm)) {
            throw new RuntimeException("两次新密码输入不一致.");
        }
        if(newPassword.equals(oldPassword)) {
            throw new RuntimeException("密码没改变不需要修改.");
        }
        UserInfo user = userInfoService.selectUserInfoByAccount(accountNo);
        if(user == null){
            throw new RuntimeException("当前用户状态无效,不可以操作密码修改.");
        }
        boolean verify = MD5Util.verify(oldPassword,user.getPassword());
        if(!verify){
            throw new RuntimeException("原密码不正确.");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserCode(user.getUserCode());
        //加密新密码
        String md5Pow = MD5Util.generate(newPassword);
        userInfo.setPassword(md5Pow);
        return userInfoService.updateUserInfo(userInfo);
    }

    /**
     * @Description 添加用户操作
     * @Author: weijunjie
     * @Date: 2020/8/17 9:00
     **/
    @Override
    public ResultVo addUser(UserInfoDto userInfoDto, String accountNo){
        //查询当前用户信息是否已存在
        List<UserInfo> userInfoList = userInfoService.selectByUserInfo(userInfoDto);
        UserInfo user = null;
        if(userInfoList.size()>0){
            user = userInfoList.get(0);
        }
        if (null != user){
            return new ResultVo(ResultEnum.RET_CODE_999.getCode(),"用户已存在，添加失败");
        }else{
            //生成用户code
            String code = CodeUtil.createCode(CodeUtil.TYPE_USER, CodeUtil.RANDOM_LENGTH_2);
            //加密密码
            String ciphertext = MD5Util.generate(userInfoDto.getPassword());
            userInfoDto.setPassword(ciphertext);
            userInfoDto.setCreateUser(accountNo);
            userInfoDto.setUserStatus(UserInfo.USER_STATUS_1);
            userInfoDto.setUserCode(code);
            int i = userInfoService.insert(userInfoDto);
            if (i>0){
                //添加用户对应机构信息
//                userOrganService.updateByUserCode(userInfoDto.getUserCode(), userInfoDto.getOrgCodeList(), accountNo);
                return ResultVoUtil.res(ResultEnum.ADD_SUCCESS);
            }else{
                return new ResultVo(ResultEnum.RET_CODE_999.getCode(),"用户已存在，添加失败");
            }
        }
    }

    /**---------------------------角色管理相关service---------------------------**/

    /**
     * @Description 添加角色数据
     * @Author: weijunjie
     * @Date: 2020/8/17 15:50
     * @return: void
     **/
    @Override
    public int addRole(RoleInfo roleInfo,String accountNo){
        int i = 0;
        RoleInfo role = roleInfoService.selectByRoleInfo(roleInfo);
        if(null == role){
            roleInfo.setCreateUser(accountNo);
            roleInfo.setRoleCode(CodeUtil.createCode(CodeUtil.TYPE_ROLE, CodeUtil.RANDOM_LENGTH_2));
            roleInfo.setRoleStatus(RoleInfo.ROLE_STATUS_1);
            i = roleInfoService.insert(roleInfo);
        }else{
            role.setUpdateUser(accountNo);
            role.setIsDel(DbCommon.IS_DEL_0);
            i = roleInfoService.updateByRoleInfo(role);
            //
            i+=userInfoService.updateByRoleCode(DbCommon.IS_DEL_0,role.getRoleCode(),accountNo,UserInfo.USER_STATUS_0);
        }
        return i;
    }

    /**
     * @Description 删除角色操作
     * @Author: weijunjie
     * @Date: 2020/8/17 16:17
     * @return: void
     **/
    @Override
    public int delRole(String roleCode,String accountNo){
        RoleInfo role = new RoleInfo();
        role.setRoleCode(roleCode);
        role.setUpdateUser(accountNo);
        role.setIsDel(DbCommon.IS_DEL_1);
        int i = roleInfoService.updateByRoleInfo(role);
        if(i>0){
            //删除角色对应的用户列表
            i+=userInfoService.updateByRoleCode(DbCommon.IS_DEL_1,roleCode,accountNo,null);
            //清除已经登录成功用户所有的登录标志
            UserInfo userInfo = new UserInfo();
            userInfo.setRoleCode(roleCode);
            List<UserInfo> userInfoList = userInfoService.selectByUserInfo(userInfo);
            clearUserPowerRedis(userInfoList);
        }
        return i;
    }

    /**
     * @Description 获取角色列表
     * @Author: weijunjie
     * @Date: 2020/8/17 16:15
     **/
    @Override
    public List<RoleInfo> getRoleList(RoleInfo roleInfo){
        return roleInfoService.getRoleInfoList(roleInfo);
    }

    /**
     * @Description 停用角色数据
     * @Author: weijunjie
     * @Date: 2020/8/17 15:50
     * @return: void
     **/
    @Override
    public int stopRole(String roleCode,String status,String accountNo){
        int i = 0;
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleCode(roleCode);
        roleInfo.setRoleStatus(status);
        roleInfo.setUpdateUser(accountNo);
        i = roleInfoService.updateByRoleInfo(roleInfo);
        if(i>0){
            //停用角色对应的用户列表
            i+=userInfoService.updateByRoleCode(null,roleCode,accountNo,status);
            //更新当前角色对应的所有的权限列表
            UserInfo userInfo = new UserInfo();
            userInfo.setRoleCode(roleCode);
            List<UserInfo> userInfoList = userInfoService.selectByUserInfo(userInfo);
            if(status.equals(UserInfo.USER_STATUS_1)){
                //启用
                refaceUserPowerRedis(userInfoList);
            }else{
                //停用
                clearUserPowerRedis(userInfoList);
            }
        }
        return i;
    }

    /**---------------------------角色权限相关service---------------------------**/

    /**
     * @Description 获取当前角色对应的权限列表数据
     * @Author: weijunjie
     * @Date: 2020/8/17 17:56
     **/
    @Override
    public ArrayList<RolePowerVo> getRolePowerList(String roleCode){
        //获取所有模块列表组装的对象
        JSONObject jsonObject = ModuleEnum.getResObj();
        //组装数据 前端页面直接解析
        List<HashMap<String,String>> hashMaps = rolePowerService.selectRolePowerByRoleCode(roleCode, RolePower.POWER_TYPE_TYPE_2);
        //遍历数据库存储结果，组装前端展示数据对象
        for(HashMap<String,String> hashMap:hashMaps){
            String key = MapUtils.getString(hashMap, "pModule", "");
            String value = MapUtils.getString(hashMap, "pActions", "");
            if(StringUtils.isBlank(value)){
                jsonObject.put(key,new JSONArray());
            }else{
                String[] sp = value.split(",");
                jsonObject.put(key,sp);
            }
        }
        //获取角色信息数据
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleCode(roleCode);
        RoleInfo role = roleInfoService.selectByRoleInfo(roleInfo);
        //组装返回数据对象
        ArrayList<RolePowerVo> rolePowerVos = new ArrayList<>();
        for(String key:jsonObject.keySet()){
            RolePowerVo rolePowerVo = new RolePowerVo();
            rolePowerVo.setRoleName(role.getRoleName());
            rolePowerVo.setActions(jsonObject.getJSONArray(key));
            rolePowerVo.setModule(key);
            rolePowerVo.setPowerName(ModuleEnum.getName(key));
            rolePowerVo.setPowerRemarks(ModuleEnum.getRemarks(key));
            rolePowerVos.add(rolePowerVo);
        }
        return rolePowerVos;
    }

    /**
     * @Description 更新角色权限表列表
     * @Author: weijunjie
     * @Date: 2020/8/17 18:06
     **/
    @Override
    public int updateRolePowerList(String roleCode,String moduleHandle,String accountNo){
        ArrayList<RolePower> rolePowerList = new ArrayList<>();
        //解析jsonArray
        JSONArray jsonArray = JSONObject.parseArray(moduleHandle);
        for(int i = 0;i<jsonArray.size();i++){
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(jsonArray.get(i)));
            if(jsonObject.getJSONArray("actions").size() == 0){
                //无需操作
                continue;
            }else{
                String module = jsonObject.getString("module");
                JSONArray actions = jsonObject.getJSONArray("actions");
                for(int j = 0;j<actions.size();j++){
                    rolePowerList.add(makeRolePower(roleCode,module,actions.get(j).toString(),accountNo));
                }
            }
        }
        if(rolePowerList.size() == 0){
            throw new RuntimeException("至少保留一个权限");
        }
        int i = rolePowerService.updateRolePower(rolePowerList);
        //刷新当前用户所拥有的权限列表缓存数据
        UserInfo userInfo = new UserInfo();
        userInfo.setRoleCode(roleCode);
        List<UserInfo> userInfoList = userInfoService.selectByUserInfo(userInfo);
        refaceUserPowerRedis(userInfoList);
        return i;
    }

    @Override
    public ResultVo updateRolePower( String params, String accountNo){

        return null;
    }

    private RolePower makeRolePower(String roleCode,String powerModule,String powerAction,
                                    String accountNo){
        RolePower rolePower = new RolePower();
        rolePower.setPowerModule(powerModule);
        rolePower.setPowerAction(powerAction);
        rolePower.setCreateUser(accountNo);
        //获取该module 对应的中文名 以及中文描述
        rolePower.setPowerName(ModuleEnum.getName(powerModule));
        rolePower.setRemarks(ModuleEnum.getRemarks(powerModule));
        rolePower.setPowerType(RolePower.POWER_TYPE_TYPE_2);
        rolePower.setRoleCode(roleCode);
        return rolePower;
    }

    /**
     * 方案  jwt签发有效期为24小时，redis中缓存记录该token有效期为24小时
     * 用户token对应权限缓存时间为30分钟，每次校验权限获取该缓存的剩余时间，
     * 剩余时间小于10min 刷新该权限列表缓存，超时返回重新登录，根据输入信息
     * 查询jwt缓存有效性，可用直接使用，不可用生成新的jwt鉴权，重复操作
     * @Author: weijunjie
     * @Date: 2020/8/13 10:47
     * @return:
     **/
    public void keepUserJwt(String userCode,String jwt){
        redisUtils.set(RedisConstant.USER_JWT_TOKEN+userCode,jwt,RedisConstant.REDIS_TIME_24,TimeUnit.HOURS);
    }

    /**
     * @Description 刷新userPower列表
     * @Author: weijunjie
     * @Date: 2020/8/13 11:24
     * @return:
     **/
    @Override
    public void userLogout(String jwt){
        UserInfo user = getUserInfoByToken(jwt);
        if(null == user){
            return;
        }
        //删除jwt缓存
        redisUtils.delete(RedisConstant.USER_JWT_TOKEN+user.getUserCode());
        if(StringUtils.isNotBlank(user.getUserCode())){
            //删除权限列表缓存
            redisUtils.delete(RedisConstant.USER_POWER_TOKEN + user.getUserCode());
        }
    }

    /**
     * @Description userPower列表
     * @Author: weijunjie
     * @Date: 2020/8/13 11:24
     * @return:
     **/
    @Override
    public boolean refreshUserPower(String userCode){
        if(StringUtils.isNotBlank(userCode)){
            Long expire = redisUtils.getExpire(RedisConstant.USER_POWER_TOKEN + userCode);
            if(expire.equals(RedisConstant.REDIS_NUMBER_2) ||
                    expire.equals(RedisConstant.REDIS_NUMBER_1)){
                keepUserPowerList(userCode);
                return true;
            }else{
                //判断剩余时间 小于10分钟 更新权限列表缓存
                if (expire<RedisConstant.USER_POWER_REFRESH){
                    keepUserPowerList(userCode);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @Description 保存用户对应的权限列表
     * @Author: weijunjie
     * @Date: 2020/8/12 15:10
     * @return:
     **/
    public void keepUserPowerList(String userCode){
        if(StringUtils.isNotBlank(userCode)){
                List<RolePower> rolePowers = rolePowerService.selectRolePowerByUserCode(
                        userCode,RolePower.POWER_TYPE_TYPE_2);
                redisUtils.set(RedisConstant.USER_POWER_TOKEN+userCode,makeRedisValue(rolePowers),
                        RedisConstant.REDIS_TIME_30,TimeUnit.MINUTES);
        }
    }

    /**
     * @Description 获取当前登录用户对应的访问权限列表
     * @Author: weijunjie
     * @Date: 2020/8/12 13:53
     * @return:
     **/
    @Override
    public Set<String> getUserPowerList(String jwt){
        Set<String> strings = new HashSet<>();
        //解析jwt数据，获取用户code
        String sub = MapUtils.getString(getToken(jwt),"sub");
        if(StringUtils.isNotBlank(sub)){
            //获取用户编号查询数据库，获取权限列表
            String userCode = JSONObject.parseObject(sub).getString("userCode");
            //执行数据库缓存查询
            String s = "";
            boolean redisStatus = true;
            try {
                s = redisUtils.get(RedisConstant.USER_POWER_TOKEN+userCode);
            }catch (Exception e){
                e.printStackTrace();
                logger.info("redis读取发生异常，查询数据库数据");
                sysErrorLogService.saveErrorLog("redis读取发生异常",e.getMessage(),"手动记录异常");
                redisStatus = false;
            }
            if(StringUtils.isNotBlank(s)){
                return makePowerList(s);
            }
            List<RolePower> rolePowers = rolePowerService.selectRolePowerByUserCode(
                    userCode,RolePower.POWER_TYPE_TYPE_2);
            strings = makePowerList(makeRedisValue(rolePowers));
            if(redisStatus){
                //当前redis没有发生异常，往redis存储一条数据
                redisUtils.set(RedisConstant.USER_POWER_TOKEN+userCode, makeRedisValue(rolePowers),
                        RedisConstant.REDIS_TIME_30, TimeUnit.MINUTES);
            }
        }
        return strings;
    }

    /**
     * @Description 根据jwt获取用户对象
     * @Author: weijunjie
     * @Date: 2020/8/12 17:09
     * @return:
     **/
    @Override
    public UserInfo getUserInfoByToken(String jwt){
        UserInfo userInfo = new UserInfo();
        if(StringUtils.isNotBlank(jwt)) {
//            String[] split = jwt.split("\\.");
//            String key = split[split.length - 1];
//            RedisUtils redisUtils = new RedisUtils();
            //解析jwt数据，获取用户code
            Map<String, Object> token = getToken(jwt);
            if(null == token){
                return userInfo;
            }
            String sub = MapUtils.getString(token,"sub");
            if(StringUtils.isNotBlank(sub)) {
                //获取用户编号查询数据库，获取权限列表
                String accountNo = JSONObject.parseObject(sub).getString("accountNo");
                String userCode = JSONObject.parseObject(sub).getString("userCode");
                String userName = JSONObject.parseObject(sub).getString("userName");
                String s = redisUtils.get(RedisConstant.USER_POWER_TOKEN + userCode);
                if(StringUtils.isNotBlank(s)){
                    userInfo.setUserCode(userCode);
                    userInfo.setUserName(userName);
                    userInfo.setAccountNo(accountNo);
                }else{
                    userInfo = userInfoService.selectUserInfoByAccount(accountNo);
                }

            }
        }
        return userInfo;
    }

    /**
     * @Description 根据权限列表信息，组装redis中存储数据的格式
     * @Author: weijunjie
     * @Date: 2020/8/12 14:49
     * @return:
     **/
    public String makeRedisValue(List<RolePower> rolePowers){
        StringBuilder sb = new StringBuilder();
        for(RolePower rolePower:rolePowers){
            sb.append(rolePower.getPowerModule())
                    .append(":")
                    .append(rolePower.getPowerAction())
                    .append(",");
        }
        return sb.toString();
    }

    /**
     * @Description 根据redis中获取到的信息，组装权限列表
     * @Author: weijunjie
     * @Date: 2020/8/12 14:49
     * @return:
     **/
    public Set<String> makePowerList(String redisValue){
        return new HashSet<>(Arrays.asList(redisValue.split(",")));
    }

    /**
     * @Description 解析token
     * @Author weijunjie
     * @Date 2020/7/23 14:31
     **/
    @Override
    public Map<String,Object> getToken(String token){
        Claims tokenClaim = jwtUtil.getTokenClaim(token);
        //校验token的有效时间
        if(null == tokenClaim){
            return null;
        }
        Map<String,Object> tokenObj = (Map<String, Object>)tokenClaim;
        return tokenObj;

    }

    /**
     * @Description 强制刷新
     * @Author: weijunjie
     * @Date: 2020/8/21 11:21
     * @return: void
     **/
    private void refaceUserPowerRedis(List<UserInfo> userInfoList){
        for(UserInfo userInfo:userInfoList){
            keepUserPowerList(userInfo.getUserCode());
        }
    }

    /**
     * @Description 强制删除
     * @Author: weijunjie
     * @Date: 2020/8/21 11:21
     * @return: void
     **/
    private void clearUserPowerRedis(List<UserInfo> userInfoList){
        for(UserInfo userInfo:userInfoList){
            redisUtils.delete(RedisConstant.USER_POWER_TOKEN + userInfo.getUserCode());
        }
    }

    /**
     * @Description 登录成功  组装返回前端token
     * @Author weijunjie
     * @Date 2020/7/22 16:58
     **/
    @Override
    public String createJwt(UserInfo user){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountNo",user.getAccountNo());
        jsonObject.put("userCode",user.getUserCode());
        jsonObject.put("userName",user.getUserName());
        String subject = jsonObject.toJSONString();
        String token = jwtUtil.createToken(subject);
        return token;
    }

}
