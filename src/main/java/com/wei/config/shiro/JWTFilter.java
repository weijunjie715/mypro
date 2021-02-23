package com.wei.config.shiro;

import com.wei.common.RedisConstant;
import com.wei.exception.RepeatRequestException;
import com.wei.exception.UserLoginException;
import com.wei.util.RedisUtils;
import com.wei.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@Component
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Lazy
    private RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);

    private static final String urls = "/401,/404,/login,/logout,/file/upload,file/checkName";
    private static final Set<String> ignoreUrls = new HashSet<>(Arrays.asList(urls.split(",")));

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String authorization = req.getHeader("Authorization");
            //检验请求地址   做重复请求校验
            String requestURI = ((HttpServletRequest) request).getRequestURI();
//            repeatRequest(authorization,requestURI);
            for(String url:ignoreUrls){
                if(requestURI.equals(url)){
                    return false;
                }
            }
            return authorization != null;
        }catch (Exception e){
            if(e instanceof RepeatRequestException){
                response403(request, response,e.getMessage());
            }else if(e instanceof ShiroException){
                response403(request, response,e.getMessage());
            }
            return false;
        }

    }

    /**
     * @Description 重复请求校验
     * @Author: weijunjie
     * @Date: 2020/8/24 17:10
     * @return: boolean
     **/
    private void repeatRequest(String token,String url){
        if(StringUtils.isNotBlank(token)){
            String[] split = token.split("\\.");
            if(split.length >1){
                //查询当前reids中是否存在请求保存
                Long expire = redisUtils.getExpire(RedisConstant.USER_REPEAT_REQUEST + split[1] + url);
                if(!expire.equals(RedisConstant.REDIS_NUMBER_2) &&
                        !expire.equals(RedisConstant.REDIS_NUMBER_1)){
                    //当前有一次请求处理中
                    throw new RepeatRequestException("请求频繁，稍后重试");
                }else{
                    redisUtils.set(RedisConstant.USER_REPEAT_REQUEST+split[1]+url,
                            "true",RedisConstant.REDIS_TIME_1,TimeUnit.SECONDS);
                }
            }
        }
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        //获取session中数据
        String authorization1 = (String)((HttpServletRequest) request).getSession().getAttribute("Authorization");
        if (StringUtils.isBlank(authorization1)){
            throw new UserLoginException("登录超时，重新登录");
        }
        if(!authorization.equals(authorization1)){
            throw new UserLoginException("登录环境异常，重新登录");
        }
        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                if(e instanceof UserLoginException){
                    response401(request, response,e.getMessage());
                    return false;
                }else{
                    response403(request, response,e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /403
     */
    private void response403(ServletRequest req, ServletResponse resp,String msg) {
        try {
//            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            HttpServletRequest httpServletRequest = (HttpServletRequest) req;
//            httpServletResponse.sendRedirect("/401");
            httpServletRequest.setAttribute("errorMessage", msg);
            // 请求转发
            httpServletRequest.getRequestDispatcher("/403").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp,String msg) {
        try {
//            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            HttpServletRequest httpServletRequest = (HttpServletRequest) req;
//            httpServletResponse.sendRedirect("/401");
            httpServletRequest.setAttribute("errorMessage", msg);
            // 请求转发
            httpServletRequest.getRequestDispatcher("/401").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
