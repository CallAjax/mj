package cn.codesign.config.security;

import cn.codesign.common.util.SysConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/4/24
 * Time: 16:32
 * Description:
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String getJwtToken(UserInfo userInfo) {
        return  Jwts.builder()
                .claim("authorities", "ROLE_ADMIN,AUTH_WRITE")
                .setSubject(userInfo.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 432_000_000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public Claims getClaims(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(SysConstant.JWT_TOKEN_AUTHORIZATION);
        Claims claims = null;
        if(token != null) {
            try {
                claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
            } catch (Exception e) {
                return null;
            }
        }
        return claims;
    }
}
