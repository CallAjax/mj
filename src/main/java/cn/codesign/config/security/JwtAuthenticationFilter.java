package cn.codesign.config.security;

import cn.codesign.common.util.SysConstant;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/4/24
 * Time: 22:23
 * Description:
 */
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    @Resource
    private JwtUtil jwtUtil;




    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        List<GrantedAuthority> auths = null;

        /**验证jwt**/
        Claims claims = this.jwtUtil.getClaims((HttpServletRequest)servletRequest);


        /**从token中拿权限**/
        if(claims != null) {
            auths = new ArrayList<>();
            List<Map<String,String>> list = (List<Map<String, String>>) claims.get(SysConstant.JWT_AUTH);
            for(Map<String,String> map : list) {
                auths.add(new SimpleGrantedAuthority(map.get(SysConstant.JWT_AUTHORITY)));
            }

            usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(claims.getSubject(), null, auths);
        }

        /**
         * usernamePasswordAuthenticationToken = null，等于没权限，直接返回登录页
         */
        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(servletRequest,servletResponse);
    }





}
