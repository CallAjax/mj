package cn.codesign.config.security;

import cn.codesign.common.util.JacksonUtil;
import cn.codesign.common.util.SysConstant;
import cn.codesign.data.vo.ResInfo;
import cn.codesign.sys.data.model.SysUser;
import cn.codesign.sys.service.SysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    private SysService sysServiceImpl;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        ResInfo resInfo = new ResInfo();
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        TokenInfo tokenInfo = this.sysServiceImpl.resToken(sysUser);

        resInfo.setTokenInfo(tokenInfo);
        resInfo.setResCode(SysConstant.AJAX_RESULT_SUCCESS);
        response.setContentType(SysConstant.JSON_CONTENTTYPE);
        response.addHeader(SysConstant.JWT_ACCESS_TOKEN,tokenInfo.getToken());
        try {
            response.getWriter().write(JacksonUtil.toJson(resInfo));
            response.getWriter().flush();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            response.getWriter().close();
        }
    }
}
