package com.wei.util;

import com.wei.enums.ResultEnum;
import com.wei.vo.ResultVo;

/**
 * @ClassName ResultVoUtil
 * 描述 :
 * @Author weijunjie
 * @Date 2020/7/30 17:42
 */
public class ResultVoUtil {

    /**
     * 成功返回有值
     * @param object
     * @return
     */
    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        resultVo.setMsg(ResultEnum.SUCCESS.getMessage());
        resultVo.setData(object);
        return resultVo;
    }

    /**
     * 无值成功返回
     * @return
     */
    public static ResultVo success(){
        return success(null);
    }
    /**
     * 错误返回
     * @param resultEnum
     * @return
     */
    public static ResultVo error(ResultEnum resultEnum){
        ResultVo resultVo =  new ResultVo();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMsg(resultEnum.getMessage());
        return resultVo;
    }

    /**
     * 错误返回
     * @param resultEnum
     * @return
     */
    public static ResultVo res(ResultEnum resultEnum){
        ResultVo resultVo =  new ResultVo();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMsg(resultEnum.getMessage());
        return resultVo;
    }

    /**
     * 带更新值的返回
     * @param resultEnum
     * @return
     */
    public static ResultVo res(ResultEnum resultEnum,Object object){
        ResultVo resultVo =  new ResultVo();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMsg(resultEnum.getMessage());
        resultVo.setData(object);
        return resultVo;
    }

    /**
     * 返回带传入参数的返回值
     * @Title: resMsg
     * @author: yindy 2020/8/28 17:56
     * @param: [resultEnum, msg, object]
     * @throws
     */
    public static ResultVo resMsg(ResultEnum resultEnum,String msg,Object object){
        ResultVo resultVo =  new ResultVo();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMsg(msg);
        resultVo.setData(object);
        return resultVo;
    }

}

