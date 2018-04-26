package cn.codesign.config.security;

import cn.codesign.common.util.JacksonUtil;
import cn.codesign.common.util.SysConstant;
import cn.codesign.data.vo.ResInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResInfo resInfo = new ResInfo();
        resInfo.setResCode(SysConstant.AJAX_RESULT_ERROR);
        resInfo.setResMsg(exception.getMessage());
        response.setContentType(SysConstant.JSON_CONTENTTYPE);
        try {
            response.getWriter().write(JacksonUtil.toJson(resInfo));
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getWriter().close();
        }
    }
}
