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
        return Jwts.builder()
                .claim(SysConstant.JWT_AUTH, userInfo.getAuthorities())
                .setSubject(userInfo.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 14400000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public Claims getClaims(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(SysConstant.JWT_AUTH_TOKEN);
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
