package cn.codesign.config.security;

import cn.codesign.data.vo.ResInfo;
import cn.codesign.sys.service.SysService;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/5/3
 * Time: 09:16
 * Description:
 */
@Aspect
@Component
public class TokenAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAop.class);

    @Value("${jwt.update}")
    private long time;


    @Resource
    private SysService sysServiceImpl;

    @Resource
    private HttpServletResponse httpServletResponse;


    @Pointcut("execution(* cn.codesign.web.controller.*.*(..))")
    public void controllerToken() {}


    @AfterReturning(pointcut = "controllerToken()", returning = "resInfo" )
    public void updateToken(ResInfo resInfo){
        /**claims由JwtAuthenticationFilter写入，没有则不处理**/
        Claims claims = (Claims) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.sysServiceImpl.updateToken(this.httpServletResponse,claims,resInfo);
    }
}
