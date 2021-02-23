package com.wei.annotation;

import com.wei.enums.ModuleActionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于认证的接口访问类型校验
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleActionAuth {

    /**
     * 业务模块
     * @return
     */
    String module() default "";
    String action() default "";

    ModuleActionEnum moduleAction() default ModuleActionEnum.MY_DEFAULT;

}

