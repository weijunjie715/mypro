package com.wei.config.shiro;

import com.wei.biz.UserService;
import com.wei.exception.UserLoginException;
import com.wei.pojo.UserInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * @Description
 **/
@Service
public class MyRealm extends AuthorizingRealm {

    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String token = principals.toString();
        Set<String> permission = userService.getUserPowerList(token);
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        //解析token  获取用户对象
        UserInfo userInfoByToken = userService.getUserInfoByToken(token);
        if(null == userInfoByToken){
//            throw new UserLoginException("登陆超时，重新登陆");
            throw new UserLoginException("用户异常，重新登录"); //AuthenticationException
        }
        //执行鉴权刷新
//        boolean b = userService.refreshUserPower(token);
//        if(!b){
//            throw new UserLoginException("登陆超时，重新登陆");
//        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
