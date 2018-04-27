package cn.codesign.config.security;

import cn.codesign.common.util.JacksonUtil;
import cn.codesign.common.util.SysConstant;
import cn.codesign.data.vo.ResInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with codesign.
 * User: Sam
 * Date: 2017/3/27
 * Time: 12:12
 * Description:
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(AjaxAuthenticationSuccessHandler.class);

    @Resource
    private JwtUtil jwtUtil;




    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResInfo resInfo = new ResInfo();
        UserInfo userInfo = ((UserInfo) authentication.getPrincipal());
        resInfo.setResCode(SysConstant.AJAX_RESULT_SUCCESS);
        resInfo.setResInfo(authentication);
        response.setContentType(SysConstant.JSON_CONTENTTYPE);

        try {
            /**jwt生产token**/
            String token = jwtUtil.getJwtToken(userInfo);
            response.addHeader(SysConstant.JWT_AUTH_TOKEN, token);

            response.getWriter().write(JacksonUtil.toJson(resInfo));
            response.getWriter().flush();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            response.getWriter().close();
        }
    }
}
