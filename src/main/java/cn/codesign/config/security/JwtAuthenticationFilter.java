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

    private static final String FILTER_APPLIED = "APPLIED";

    @Resource
    private JwtUtil jwtUtil;




    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 解决security过滤器一个请求执行两次的问题
         */
        if(servletRequest.getAttribute(FILTER_APPLIED) != null) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        List<GrantedAuthority> auths = null;

        /**验证jwt**/
        Claims claims = this.jwtUtil.getClaims((HttpServletRequest)servletRequest);

        System.out.println(((HttpServletRequest) servletRequest).getRequestURI());

        /**从token中拿权限**/
        if(claims != null) {
            System.out.println(claims.getExpiration());
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
        servletRequest.setAttribute(FILTER_APPLIED,true);
        filterChain.doFilter(servletRequest,servletResponse);
    }





}
