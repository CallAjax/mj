package cn.codesign.sys.service.impl;

import cn.codesign.common.util.JacksonUtil;
import cn.codesign.common.util.SysConstant;
import cn.codesign.config.security.JwtUtil;
import cn.codesign.config.security.UserInfo;
import cn.codesign.sys.service.SysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/4/30
 * Time: 17:04
 * Description:
 */
@Service
public class SysServiceImpl implements SysService {

    @Resource
    private JwtUtil jwtUtil;


    /**
     * 生成token
     * @param httpServletResponse
     * @param name
     * @param userInfo
     * @throws Exception
     */
    @Override
    public void resToken(HttpServletResponse httpServletResponse,String name, UserInfo userInfo) throws Exception {
        httpServletResponse.addHeader(
                SysConstant.JWT_ACCESS_TOKEN,
                this.jwtUtil.getJwtToken(name,userInfo.getAuthorities())
        );
        httpServletResponse.addHeader(
                SysConstant.JWT_ROUTES,
                //返回之前encode，前端js使用decodeURIComponent
                URLEncoder.encode(JacksonUtil.toJson(userInfo.getSysAuthority()),
                        SysConstant.CHARSET_UTF8)
        );
    }
}
