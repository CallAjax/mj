package cn.codesign.sys.service.impl;

import cn.codesign.common.util.JacksonUtil;
import cn.codesign.common.util.SysConstant;
import cn.codesign.config.security.JwtUtil;
import cn.codesign.config.security.UserInfo;
import cn.codesign.sys.data.model.SysAuthority;
import cn.codesign.sys.service.SysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * @param userInfo
     * @throws Exception
     */
    @Override
    public void resToken(HttpServletResponse httpServletResponse, UserInfo userInfo) throws Exception {
        List<SysAuthority> list = userInfo.getSysAuthority();
        List<SysAuthority> l1 = list.stream().filter(v -> v.getAuthorityLevel() == 1).collect(Collectors.toList());
        List<SysAuthority> l2 = list.stream().filter(v -> v.getAuthorityLevel() == 2).collect(Collectors.toList());
        List<SysAuthority> l3 = list.stream().filter(v -> v.getAuthorityLevel() == 3).collect(Collectors.toList());
        Map<String,List<SysAuthority>> map1 = new HashMap<>();
        Map<String,List<SysAuthority>> map2 = new HashMap<>();
        l1.stream().forEach(v1 -> {
            map1.put(v1.getUrl(), l2.stream().filter(m1 -> v1.getId().equals(m1.getAuthorityParentId()))
                    .collect(Collectors.toList()));
            map1.get(v1.getUrl()).forEach(v2 -> {
                map2.put(v2.getUrl(),l3.stream().filter(m2 -> v2.getId().equals(m2.getAuthorityParentId()))
                        .collect(Collectors.toList()));
            });
        });

        httpServletResponse.addHeader(
                SysConstant.JWT_ACCESS_TOKEN,
                this.jwtUtil.getJwtToken(userInfo.getUsername(),userInfo.getAuthorities())
        );
        /**路由页面以及对应子路由**/
        httpServletResponse.addHeader(
                SysConstant.JWT_ROUTES,
                //返回之前encode，前端js使用decodeURIComponent
                URLEncoder.encode(JacksonUtil.toJson(map1),
                        SysConstant.CHARSET_UTF8)
        );
        /**所有子路由下具体按钮**/
        httpServletResponse.addHeader(
                SysConstant.JWT_MENU,
                //返回之前encode，前端js使用decodeURIComponent
                URLEncoder.encode(JacksonUtil.toJson(map2),
                        SysConstant.CHARSET_UTF8)
        );
    }
}
