package com.huang.experiment_4.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author F1shBone
 * @version 1.0
 * @date 2024/7/22 10:40
 */
@Component
public class JwtUtils {
    private static final String signKey  = "huang";
    private static final Long expire = 43200000L;

    public static String generateJWT(Map<String, Object> claims)
    {
        /*
        *   @define 生成JWT令牌
        *   @param [claims]
        *   @return java.lang.String
        */
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,signKey)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
    }

    public static Claims parseJWT(String jwt){
        /*
        *   @define 解析JWT令牌
        *   @param [jwt]
        *   @return io.jsonwebtoken.Claims
        */
        return Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
