package cn.codesign.config.exception;

import cn.codesign.base.BaseController;
import cn.codesign.common.util.BusinessException;
import cn.codesign.common.util.JacksonUtil;
import cn.codesign.common.util.SysConstant;
import cn.codesign.data.vo.ResInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/5/3
 * Time: 13:39
 * Description:
 */
@ControllerAdvice
public class AllException {


    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler({Exception.class})
    public String handlerException(HttpServletRequest request, HttpServletResponse response, Exception e){
        ResInfo resJson = new ResInfo();
        try {
            if(e instanceof BusinessException){
                LOGGER.warn("业务异常：" + ((BusinessException) e).getSysErrorInfo().getErrorMsg(),e);
                resJson.setResCode(((BusinessException) e).getSysErrorInfo().getErrorCode());
                resJson.setResMsg(((BusinessException) e).getSysErrorInfo().getErrorMsg());
                resJson.setResInfo(((BusinessException) e).getSysErrorInfo());
            }else{
                LOGGER.error("系统异常：" + e.getMessage(),e);
                resJson.setResCode(SysConstant.E99999);
                resJson.setResMsg(e.getMessage());
            }
            if(isAjax(request)){
                response.setContentType(SysConstant.JSON_CONTENTTYPE);
                response.getWriter().write(JacksonUtil.toJson(resJson));
                response.getWriter().flush();
                response.getWriter().close();
                return null;
            }
            request.setAttribute(SysConstant.ERROR_INFO_NAME, resJson);
        } catch (Exception ee) {
            LOGGER.error("处理异常：" + ee.getMessage(),ee);
        }
        return "/error";
    }



    /**
     * @User Sam
     * @Date 2017/5/27
     * @Time 16:00
     * @param
     * @return
     * @Description 判断是否ajax请求
     */
    private boolean isAjax(HttpServletRequest request){
        if(SysConstant.REQUEST_AJAX_TYPE.equals(request.getHeader(SysConstant.REQUEST_AJAX_HEADR))){
            return true;
        }
        return false;
    }

}
