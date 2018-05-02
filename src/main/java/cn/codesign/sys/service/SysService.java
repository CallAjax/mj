package cn.codesign.sys.service;

import cn.codesign.sys.data.model.SysUser;
import cn.codesign.sys.data.model.SysUserAuthority;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     */
    void resToken(HttpServletResponse httpServletResponse, SysUserAuthority sysUserAuthority, SysUser sysUser) throws Exception;

    /**
     * 获取用户权限
     * @param name
     * @return
     */
    SysUserAuthority getSysUserAuthority(String name);

    /**
     * 验证并更新token
     * @param claims
     * @return
     */
    List<GrantedAuthority> validateAndUpdate(Claims claims, HttpServletResponse httpServletResponse);


}
