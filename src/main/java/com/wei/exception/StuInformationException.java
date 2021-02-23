package com.wei.exception;


import com.wei.enums.ResultEnum;

/**
 * @ClassName StuInformationException
 * 描述 :
 * @Author weijunjie
 * @Date 2020/7/30 17:45
 */
public class StuInformationException extends RuntimeException{

    private Integer code;

    public StuInformationException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
