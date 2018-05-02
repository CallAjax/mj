package cn.codesign.sys.service;

import cn.codesign.config.security.UserInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/4/30
 * Time: 17:04
 * Description:
 */
public interface SysService {

    /**
     * 生成token
     * @param httpServletResponse
     * @param name
     * @param userInfo
     */
    void resToken(HttpServletResponse httpServletResponse, String name, UserInfo userInfo) throws Exception;
}
