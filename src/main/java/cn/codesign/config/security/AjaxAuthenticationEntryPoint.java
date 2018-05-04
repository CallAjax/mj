package cn.codesign.config.security;

import cn.codesign.common.util.JacksonUtil;
import cn.codesign.common.util.SysConstant;
import cn.codesign.data.vo.ResInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/5/4
 * Time: 13:29
 * Description:
 */
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResInfo resInfo = new ResInfo();
        resInfo.setResCode(SysConstant.AJAX_RESULT_ERROR);
        resInfo.setResMsg(e.getMessage());
        httpServletResponse.setContentType(SysConstant.JSON_CONTENTTYPE);
        httpServletResponse.setStatus(httpServletResponse.SC_FORBIDDEN);

        try {
            httpServletResponse.getWriter().write(JacksonUtil.toJson(resInfo));
            httpServletResponse.getWriter().flush();
        } catch (Exception ee) {
            e.printStackTrace();
        } finally {
            httpServletResponse.getWriter().close();
        }
    }
}
