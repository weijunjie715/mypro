package com.wei.controller;

import com.wei.annotation.ModuleActionAuth;
import com.wei.biz.UserService;
import com.wei.dto.UserInfoDto;
import com.wei.dto.UserInfoPasswordDto;
import com.wei.enums.ModuleActionEnum;
import com.wei.enums.ResultEnum;
import com.wei.util.PageInfo;
import com.wei.pojo.RoleInfo;
import com.wei.pojo.UserInfo;
import com.wei.util.ParamsUtil;
import com.wei.util.ResultVoUtil;
import com.wei.vo.ResultVo;
import com.wei.vo.RolePowerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserController
 * 描述 :
 * @Author weijunjie
 * @Date 2020/7/27 14:51
 */
@RequestMapping("/user/")
@RestController
@CrossOrigin
@Api(value = "user",description = "用户操作接口")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * @Description 用户登录  获取token
     * @Author: weijunjie
     * @Date: 2020/8/14 14:43
     * @return:
     **/
    @ApiOperation(value = "用户登录接口",notes = "accountNo,password必填")
    @ApiImplicitParam(name = "userInfo",value = "角色实体类",required = true,dataType = "UserInfo")
    @PostMapping("login")
    public ResultVo userLogin(HttpServletRequest request, @RequestBody UserInfo userInfo){
        //执行用户登录操作
        ResultVo<Object> resultVo = new ResultVo<>();
        String token = userService.userLogin(userInfo.getAccountNo(), userInfo.getPassword());
        if(StringUtils.isBlank(token)){
            resultVo.setMsg(ResultEnum.LOGIN_ERROR.getMessage());
            resultVo.setCode(ResultEnum.LOGIN_ERROR.getCode());
        }else{
            request.getSession().setAttribute("userInfo",userService.getUserInfo(userInfo.getAccountNo()));
            request.getSession().setAttribute("Authorization",token);
            resultVo.setData(token);
            resultVo.setMsg(ResultEnum.LOGIN_SUCCESS.getMessage());
            resultVo.setCode(ResultEnum.LOGIN_SUCCESS.getCode());
        }

        return resultVo;
    }

    /**
     * @Description 用户注销接口
     * @Author: weijunjie
     * @Date: 2020/8/14 14:43
     * @return:
     **/
    @ApiOperation(value = "用户退出接口",notes = "无请求参数，退出当前登录用户")
    @GetMapping("logout")
    public ResultVo userLogout(HttpServletRequest request){
        //执行用户注销操作 1、设置session失效
        String token = (String) request.getSession().getAttribute("Authorization");
        //执行清除缓存操作
        userService.userLogout(token);
        request.getSession().setAttribute("Authorization",null);
        return ResultVoUtil.res(ResultEnum.LOGOUT_SUCCESS);
    }
    /**---------------------------------角色管理相关接口start----------------------------------**/

    /**
     * @Description 添加角色信息
     * @Author: weijunjie
     * @Date: 2020/8/17 10:03
     **/
    @ApiOperation(value = "添加角色接口",notes = "添加角色信息数据")
    @ApiImplicitParam(name = "roleInfo",value = "角色实体类",required = true,dataType = "RoleInfo")
    @PostMapping("role/add")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.ROLE_MODULE_HANDLE)
    public ResultVo addRole(@RequestBody RoleInfo roleInfo,HttpServletRequest request){
        int i = userService.addRole(roleInfo,getLoginAccountNo(request));
        if(i>0){
            return ResultVoUtil.res(ResultEnum.ADD_SUCCESS);
        }else{
            return ResultVoUtil.res(ResultEnum.ADD_FAIL);
        }

    }

    /**
     * @Description 停用角色信息
     * @Author: weijunjie
     * @Date: 2020/8/17 10:03
     **/
    @ApiOperation(value = "启停角色接口",notes = "roleCode、roleStatus必填")
    @ApiImplicitParam(name = "roleInfo",value = "角色实体类",required = true,dataType = "RoleInfo")
    @PostMapping("role/upStatus")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.ROLE_MODULE_HANDLE)
    public ResultVo stopRole(HttpServletRequest request, @RequestBody RoleInfo roleInfo){

        int i = userService.stopRole(roleInfo.getRoleCode(), roleInfo.getRoleStatus(), getLoginAccountNo(request));
        if(i>0){
            return ResultVoUtil.res(ResultEnum.UPDATE_SUCCESS);
        }else{
            return ResultVoUtil.res(ResultEnum.UPDATE_SUCCESS);
        }
    }

    /**
     * @Description 删除角色信息
     * @Author: weijunjie
     * @Date: 2020/8/17 10:03
     **/
    @ApiOperation(value = "删除角色接口",notes = "roleCode必填")
    @ApiImplicitParam(name = "roleInfo",value = "角色实体类",required = true,dataType = "RoleInfo")
    @PostMapping("role/delete")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.ROLE_MODULE_HANDLE)
    public ResultVo delRole(HttpServletRequest request, @RequestBody RoleInfo roleInfo){
        int i = userService.delRole(roleInfo.getRoleCode(), getLoginAccountNo(request));
        if(i>0){
            return ResultVoUtil.res(ResultEnum.DEL_SUCCESS);
        }else{
            return ResultVoUtil.res(ResultEnum.DEL_FAIL);
        }
    }

    /**
     * @Description 获取角色列表数据 （角色数据量小，不需要分页）
     * @Author: weijunjie
     * @Date: 2020/8/19 10:01
     **/
    @ApiOperation(value = "获取角色列表",notes = "不需要参数")
    @GetMapping("role/query")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.ROLE_MODULE_VIEW)
    public ResultVo getRoleList(HttpServletRequest request){
        return ResultVoUtil.res(ResultEnum.DEL_FAIL);
    }

    /**---------------------------------角色管理相关接口stop----------------------------------**/

    /**---------------------------------接口权限管理相关接口start----------------------------------**/

    /**
     * @Description 获取访问权限列表
     * @Author: weijunjie
     * @Date: 2020/8/17 14:22
     **/
    @ApiOperation(value = "获取角色下模块权限列表",notes = "roleCode参数，结果一页展示")
    @ApiImplicitParam(name = "roleInfo",value = "角色实体类",required = true,dataType = "RoleInfo")
    @GetMapping("auth/query")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.AUTH_MODULE_VIEW)
    public ResultVo getAuth(HttpServletRequest request){
        return ResultVoUtil.res(ResultEnum.DEL_FAIL);
    }

    /**
     * @Description 更新访问权限
     * @Author: weijunjie
     * @Date: 2020/8/17 15:22
     **/
    @ApiOperation(value = "更新角色下模块权限列表",notes = "更新角色下模块权限列表")
    @PostMapping("auth/update")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.AUTH_MODULE_HANDLE)
    public ResultVo updateAuth(@RequestBody Map<String, Object> params,HttpServletRequest request){
//        Map<String, Object> params = ParamsUtil.getParams(request);
        String roleCode = MapUtils.getString(params,"roleCode","");
        String json = MapUtils.getString(params,"values","");
        int i = userService.updateRolePowerList(roleCode, json, getLoginAccountNo(request));
        if(i>0){
            return ResultVoUtil.res(ResultEnum.UP_SUCCESS);
        }else{
            return ResultVoUtil.res(ResultEnum.UP_FAIL);
        }

    }

    /**
     * @Description 添加按钮列表权限
     * @Author: weijunjie
     * @Date: 2020/8/17 15:22
     **/
    @ModuleActionAuth(moduleAction = ModuleActionEnum.AUTH_MODULE_HANDLE)
    public ResultVo addButtonAuth(){
        return ResultVoUtil.res(ResultEnum.ADD_SUCCESS);
    }

    /**
     * @Description 删除按钮列表权限
     * @Author: weijunjie
     * @Date: 2020/8/17 15:23
     **/
    @ModuleActionAuth(moduleAction = ModuleActionEnum.AUTH_MODULE_HANDLE)
    public ResultVo delButtonAuth(){
        return ResultVoUtil.res(ResultEnum.ADD_SUCCESS);
    }

    /**---------------------------------接口权限管理相关接口stop----------------------------------**/

    /**---------------------------------用户管理相关接口start----------------------------------**/

    /**
     * @Description 添加用户操作
     * @Author: weijunjie
     * @Date: 2020/8/14 14:43
     * @return:
     **/
    @ApiOperation(value = "添加用户",notes = "添加用户数据信息")
    @ApiImplicitParam(name = "userInfo",value = "用户实体类",required = true,dataType = "UserInfo")
    @PostMapping("add")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.USER_MODULE_HANDLE)
    public ResultVo addUser(@RequestBody UserInfoDto userInfoDto, HttpServletRequest request){
        return userService.addUser(userInfoDto,getLoginAccountNo(request));
    }

    /**
     * @Description 删除用户信息数据
     * @Author: weijunjie
     * @Date: 2020/8/17 9:36
     **/
    @ApiOperation(value = "删除用户接口",notes = "userCode")
    @ApiImplicitParam(name = "userInfo",value = "用户实体类",required = true,dataType = "UserInfo")
    @PostMapping("delete")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.USER_MODULE_HANDLE)
    public ResultVo delUser(@RequestBody UserInfo userInfo,HttpServletRequest request){
        int i = userService.deleteUserInfo(userInfo.getUserCode(),getLoginAccountNo(request));
        if(i == 1){
            return ResultVoUtil.res(ResultEnum.DEL_SUCCESS);
        }else{
            return ResultVoUtil.res(ResultEnum.DEL_FAIL);
        }
    }

    /**
     * @Description: 更新用户信息
     * @author: chenzhm 2020/8/28 15:31
     * @param: [userInfo]
     * @return: ResultVo
     */
    @ApiOperation(value = "更新用户信息接口",notes = "根据用户编号更新用户信息")
    @ApiImplicitParam(name = "userInfo",value = "用户实体类",required = true,dataType = "UserInfo")
    @PostMapping("update")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.USER_MODULE_HANDLE)
    public ResultVo updateUser(@RequestBody UserInfoDto userInfoDto, HttpServletRequest request){
        userService.updateUserInfo(userInfoDto, getLoginAccountNo(request));

        return ResultVoUtil.res(ResultEnum.UP_SUCCESS);
    }

    /**
     * @Description 启停用户信息数据
     * @Author: weijunjie
     * @Date: 2020/8/17 9:36
     **/
    @ApiOperation(value = "启停用户信息接口",notes = "userCode userStatus 必填")
    @ApiImplicitParam(name = "userInfo",value = "用户实体类",required = true,dataType = "UserInfo")
    @PostMapping("upStatus")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.USER_MODULE_HANDLE)
    public ResultVo stopUser(@RequestBody UserInfo userInfo, HttpServletRequest request){
        int i = userService.stopUser(userInfo.getUserCode(),userInfo.getUserStatus(),getLoginAccountNo(request));
        if(i == 1){
            return ResultVoUtil.res(ResultEnum.UP_SUCCESS);
        }else{
            return ResultVoUtil.res(ResultEnum.UP_FAIL);
        }
    }

    /**
     * @Description 更新用户密码
     * @Author: weijunjie
     * @Date: 2020/8/17 9:36
     **/
    @ApiOperation(value = "更新用户密码接口",notes = "选择用户信息数据，更新用户密码")
    @ApiImplicitParam(name = "UserInfoPasswordDto",value = "用户密码DTO类",required = true,dataType = "UserInfoPasswordDto")
    @PostMapping("upPassword")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.USER_MODULE_HANDLE)
    public ResultVo updatePassword(HttpServletRequest request, @RequestBody UserInfoPasswordDto userInfoPasswordDto){
        String accountNo = getLoginAccountNo(request);
        int i = userService.updateUserPassword(accountNo,userInfoPasswordDto);
        if(i == 1){
            return ResultVoUtil.res(ResultEnum.UP_SUCCESS);
        }else{
            return ResultVoUtil.res(ResultEnum.UP_FAIL);
        }
    }

    /**
     * @Description 获取用户列表操作
     * @Author: weijunjie
     * @Date: 2020/8/14 14:44
     * @return:
     **/
    @ApiOperation(value = "用户信息查询接口",notes = "PageInfo信息、userName、roleCode可选")
    @GetMapping("query")
    @ModuleActionAuth(moduleAction = ModuleActionEnum.USER_MODULE_VIEW)
    public ResultVo getAllUser(HttpServletRequest request){
        return ResultVoUtil.res(ResultEnum.UP_FAIL);
    }

    /**---------------------------------用户管理相关接口stop----------------------------------**/

}
