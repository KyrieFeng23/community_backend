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


    //生成token
    public static String generateToken(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put(USER_NAME, userId);
        String jwt = Jwts.builder()
                .setClaims(map)         //对map进行加密
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))      //到期时间，这里是1000小时
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return jwt; //jwt前面一般都会加Bearer
    }

    //校验token
    public static HttpServletRequest validateTokenAndAddUserIdToHeader(HttpServletRequest request) {
        //拿到token
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            try {
                Map<String, Object> body = Jwts.parser()   //parser解析
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))        //去掉刚才token最前面的"Bearer "
                        .getBody();             //还原出来刚才生成token的map对象
                //返回一个定制的http请求对象
                return new CustomHttpServletRequest(request, body);
            } catch (Exception e) {
                logger.info(e.getMessage());
                throw new TokenValidationException(e.getMessage());
            }
        } else {
            throw new TokenValidationException("Missing token");
        }
    }


    public static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private Map<String, String> claims;

        public CustomHttpServletRequest(HttpServletRequest request, Map<String, ?> claims) {
            super(request);
            this.claims = new HashMap<>();
            claims.forEach((k, v) -> this.claims.put(k, String.valueOf(v)));
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (claims != null && claims.containsKey(name)) {
                return Collections.enumeration(Arrays.asList(claims.get(name)));
            }
            return super.getHeaders(name);
        }

    }

    static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }
}
