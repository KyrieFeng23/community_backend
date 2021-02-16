package com.community.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/17 1:00 上午
 */
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    public static final long EXPIRATION_TIME = 3600_000_000L; // 1000 hour
    public static final String SECRET = "ThisIsASecret";//please change to your own encryption secret.
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_NAME = "userName";

    public static String generateToken(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put(USER_NAME, userId);
        String jwt = Jwts.builder()
                .setClaims(map)         //对map进行加密
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))      //到期时间，这里是1000小时
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return jwt;
    }
}
