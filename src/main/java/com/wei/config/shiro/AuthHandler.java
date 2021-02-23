package com.wei.config.shiro;

import com.wei.annotation.ModuleActionAuth;
import com.wei.enums.ModuleActionEnum;
import com.wei.exception.UserLoginException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;

/**
 * Auth注解的操作类
 */
public class AuthHandler extends AuthorizingAnnotationHandler {

    public AuthHandler() {
        //写入注解
        super(ModuleActionAuth.class);
    }

    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        if (a instanceof ModuleActionAuth) {
            ModuleActionAuth annotation = (ModuleActionAuth) a;
            String module = annotation.module();
            String action = annotation.action();
            ModuleActionEnum moduleAction = annotation.moduleAction();
            //module action 赋值
            module = StringUtils.isNotBlank(module)?module:moduleAction.getModule();
            action = StringUtils.isNotBlank(action)?action:moduleAction.getAction();
            //1.获取当前主题
            Subject subject = this.getSubject();
            if (!subject.isAuthenticated()){
                throw new UserLoginException("登录超时，重新登录");
            }
            //2.验证是否包含当前接口的权限有一个通过则通过
            try {
                subject.checkPermission(module+":"+action);
            }catch (Exception e){
                throw new AuthorizationException("接口访问权限校验失败");
            }

        }
    }
}