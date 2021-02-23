package com.wei.config;

import com.alibaba.fastjson.JSONObject;

import com.wei.biz.UserService;
import com.wei.common.RedisConstant;
import com.wei.util.ParamsUtil;
import com.wei.util.RedisUtils;
import com.wei.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PrintWriter out = null ;
        String authorization = request.getHeader("Authorization");
        //检验请求地址   做重复请求校验
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        //获取用户请求头携带的用户信息
        try {
            if(StringUtils.isNotBlank(authorization)){
                String[] split = authorization.split("\\.");
                if(split.length >1){
                    //查询当前reids中是否存在请求保存
                    Long expire = redisUtils.getExpire(RedisConstant.USER_REPEAT_REQUEST + split[1] + requestURI);
                    if(!expire.equals(RedisConstant.REDIS_NUMBER_2) &&
                            !expire.equals(RedisConstant.REDIS_NUMBER_1)){
                        ResultVo resultVo = new ResultVo();
                        resultVo.setCode(401);
                        resultVo.setMsg("登录异常，重新登录");
                        out = response.getWriter();
                        out.append(JSONObject.toJSONString(resultVo));
                        return false;
                    }else{
                        redisUtils.set(RedisConstant.USER_REPEAT_REQUEST+split[1]+requestURI,
                                "true",RedisConstant.REDIS_TIME_1,TimeUnit.SECONDS);
                    }
                }
            }
            return true;
        }catch (Exception e){
            logger.warn("LoginInterceptor preHandle:" + ParamsUtil.getErrorMsg(e));
            response.sendError(500);
            return false;
        }

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}