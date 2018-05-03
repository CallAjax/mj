package cn.codesign.config.security;

import cn.codesign.common.util.SysConstant;
import cn.codesign.sys.data.mapper.SecurityMapper;
import cn.codesign.sys.service.SysCacheService;
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
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private SysCacheService sysCacheServiceImpl;


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

        /**
         * 提供第三方无需token，同时url不受security拦截，允许访问，单独表存放url
         * 被忽略url与受security保护的url互斥，不可同时存在
         */
        List<String> list = this.sysCacheServiceImpl.getIgnoreService();
        if(list.size() > 0) {
            String url = ((HttpServletRequest)servletRequest).getRequestURI();
            int firstQuestionMarkIndex = url.indexOf("?");
            if(firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }
            final String u = url;
            if(list.stream().filter(v -> u.startsWith(v)).collect(Collectors.toList()).size() > 0){
                usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(null, null, null);
            }
        }


        /**验证jwt**/
        Claims claims = null;
        String token = ((HttpServletRequest) servletRequest).getHeader(SysConstant.JWT_AUTHORIZATION_TOKEN);
        if(token != null) {
            claims = this.jwtUtil.getClaims(token);
        }

        /**从token中拿权限**/
        if(claims != null) {
            /**判断是否更新token**/
            try {
                //auths = this.sysServiceImpl.validateAndUpdate(claims, (HttpServletResponse) servletResponse);
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
