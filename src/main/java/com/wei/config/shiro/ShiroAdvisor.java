package com.wei.config.shiro;

import com.wei.annotation.ModuleActionAuth;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * 启动自定义注解
 */

public class ShiroAdvisor extends AuthorizationAttributeSourceAdvisor {

    public ShiroAdvisor() {
        // 这里可以添加多个
        setAdvice(new AuthAopInterceptor());
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean matches(Method method, Class targetClass) {
        Method m = method;
        if (targetClass != null) {
            try {
                m = targetClass.getMethod(m.getName(), m.getParameterTypes());
                return this.isFrameAnnotation(m);
            } catch (NoSuchMethodException ignored) {

            }
        }
        return super.matches(method, targetClass);
    }

    private boolean isFrameAnnotation(Method method) {
        return null != AnnotationUtils.findAnnotation(method, ModuleActionAuth.class);
    }
}
