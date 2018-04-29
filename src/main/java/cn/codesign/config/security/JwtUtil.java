package cn.codesign.config.security;

import cn.codesign.common.util.SysConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/4/24
 * Time: 16:32
 * Description: jwt工具类
 */
@Component
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.time}")
    private long time;

    /**
     * 生成jwttoken
     * @param userInfo
     * @return
     */
    public String getJwtToken(UserInfo userInfo) {
        return Jwts.builder()
                .setHeader(SysConstant.JWT_MAP)
                .claim(SysConstant.JWT_AUTH, userInfo.getAuthorities())
                .setSubject(userInfo.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    /**
     * 验证token
     * @param token
     * @return
     */
    public Claims getClaims(String token) {
        Claims claims = null;
        if(token != null && token.startsWith(SysConstant.JWT_BEARER)) {
            try {
                claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token.substring(7))
                        .getBody();
            } catch (Exception e) {
                LOGGER.warn(e.getMessage(), e);
                return null;
            }
        }
        return claims;
    }
}
