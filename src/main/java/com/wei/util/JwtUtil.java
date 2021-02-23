package com.wei.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * JWT的token，区分大小写
 * jwt默认过期时间为1天，redis缓存中维护一对关系数据，缓存时间设置为30min
 */
@SuppressWarnings("all")
public class JwtUtil {
    private String secret = "mypro001";

    private long expire = 60*60*24;

    private String header = "AuthToken";

    /**
     * 生成token
     * @param subject
     * @return
     */
    public String createToken (String subject){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);//过期时间

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    /**
     * 获取token中注册信息
     * @param token
     * @return
     */
    public Claims getTokenClaim (String token) {
        Claims body = null;
        try {
            body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            body = null;
        }
        return body;
    }
    /**
     * 验证token是否过期失效
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * @Description 校验token有效性
     * @Author: weijunjie
     * @Date: 2020/8/13 13:31
     * @return:
     **/
    public boolean checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return false;
        }
        boolean flag = false;
        Claims body = null;
        try {
            body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            if(body == null){
                flag = false;
            }else{
                flag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 获取token失效时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }
    /**
     * 获取用户名从token中
     */
    public String getUsernameFromToken(String token) {
        return getTokenClaim(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }

    // --------------------- getter & setter ---------------------

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public long getExpire() {
        return expire;
    }
    public void setExpire(long expire) {
        this.expire = expire;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
}