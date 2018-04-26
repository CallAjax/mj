package cn.codesign.config.security;

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
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

        Claims claims = this.jwtUtil.getClaims((HttpServletRequest)servletRequest);


        if(claims != null) {
            String url = ((HttpServletRequest) servletRequest).getRequestURI();

            int firstQuestionMarkIndex = url.indexOf("?");

            if (firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }
            SimpleGrantedAuthority authority  = new SimpleGrantedAuthority("a001");
            auths.add(authority);
            usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(claims.getSubject(), null, auths);
        }

        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(servletRequest,servletResponse);
    }





}
