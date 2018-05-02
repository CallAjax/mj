package cn.codesign.config.security;

import cn.codesign.common.util.SysConstant;
import cn.codesign.sys.data.mapper.SecurityMapper;
import cn.codesign.sys.service.SysService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private static final String FILTER_APPLIED = "FILTER_APPLIED";

    @Value("${jwt.update}")
    private long time;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private SecurityMapper securityMapper;

    @Resource
    private SysService sysServiceImpl;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 解决security过滤器一个请求执行两次的问题
         */
        if(servletRequest.getAttribute(FILTER_APPLIED) != null) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }


        /**验证jwt**/
        Claims claims = null;
        String token = ((HttpServletRequest) servletRequest).getHeader(SysConstant.JWT_AUTHORIZATION_TOKEN);
        if(token != null) {
            claims = this.jwtUtil.getClaims(token);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        List<GrantedAuthority> auths = null;



        /**从token中拿权限**/
        if(claims != null) {
            /**判断是否更新token**/
            try {
                auths = this.sysServiceImpl.validateAndUpdate(claims, (HttpServletResponse) servletResponse);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
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
