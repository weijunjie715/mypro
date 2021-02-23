package com.wei.enums;

/**
 * @ClassName ModuleActionEnum
 * @Description :
 * @Author weijunjie
 * @Date 2020/8/24 10:01
 */
public enum ModuleActionEnum {

    USER_MODULE_HANDLE(ModuleEnum.USER_MODULE.getModule(),"handle"),
    ROLE_MODULE_HANDLE(ModuleEnum.ROLE_MODULE.getModule(),"handle"),
    AUTH_MODULE_HANDLE(ModuleEnum.AUTH_MODULE.getModule(),"handle"),
    DICT_MODULE_HANDLE(ModuleEnum.DICT_MODULE.getModule(),"handle"),


    USER_MODULE_VIEW(ModuleEnum.USER_MODULE.getModule(),"view"),
    ROLE_MODULE_VIEW(ModuleEnum.ROLE_MODULE.getModule(),"view"),
    AUTH_MODULE_VIEW(ModuleEnum.AUTH_MODULE.getModule(),"view"),
    DICT_MODULE_VIEW(ModuleEnum.DICT_MODULE.getModule(),"view"),

    MY_DEFAULT("",""),
    ;

    private String module;

    private String action;

    public String getModule() {
        return module;
    }

    public String getAction() {
        return action;
    }

    ModuleActionEnum(String module, String action) {
        this.module = module;
        this.action = action;
    }
}
