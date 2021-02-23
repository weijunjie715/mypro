package com.wei.exception;

import com.wei.enums.ResultEnum;
import com.wei.util.ResultVoUtil;
import com.wei.vo.ResultVo;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常捕捉
 **/
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResultVo handle401(ShiroException e) {
        return ResultVoUtil.error(ResultEnum.RET_CODE_401);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UserLoginException.class)
    public ResultVo loginException(UserLoginException e) {
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.RET_CODE_401.getCode());
        objectResultVo.setMsg(e.getMessage());
        return objectResultVo;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(QueryParamException.class)
    public ResultVo queryParamException(QueryParamException e) {
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.RET_CODE_400.getCode());
        objectResultVo.setMsg(e.getMessage());
        return objectResultVo;
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResultVo authErrorHandle(AuthorizationException e) {
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.RET_CODE_403.getCode());
        objectResultVo.setMsg(e.getMessage());
        return objectResultVo;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResultVo handle401() {
        return ResultVoUtil.error(ResultEnum.RET_CODE_401);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVo globalException(HttpServletRequest request, Throwable ex) {
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.RET_CODE_999.getCode());
        objectResultVo.setMsg(ex.getMessage());
        return objectResultVo;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultVo runtimeException(HttpServletRequest request, Throwable ex) {
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.RET_CODE_900.getCode());
        objectResultVo.setMsg(ex.getMessage());
        return objectResultVo;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

