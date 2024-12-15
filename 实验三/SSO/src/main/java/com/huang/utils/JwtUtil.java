package com.huang.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map; /**
 * @author Huang_ruijie
 * @version 1.0
 */
public class JwtUtil {
    //密钥
    private static final String SECRET = "my_secret";
    //过期时间
    private static final long EXPIRATION = 1000;//单位为秒

    //生成用户token,设置token超时时间
    public static String createToken(String userName, String password) {
        //过期时间60s
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 120);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)// 添加头部
                //可以将基本信息放到claims中
                .withClaim("userName", userName)
                .withClaim("password", password)
                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
        return token;
    }

    //校验token
    public static boolean verifyToken(String token) {
        try{
            DecodedJWT jwt=JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}