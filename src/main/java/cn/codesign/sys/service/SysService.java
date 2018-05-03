package cn.codesign.sys.service;

import cn.codesign.config.security.TokenInfo;
import cn.codesign.data.vo.ResInfo;
import cn.codesign.sys.data.model.SysUser;
import cn.codesign.sys.data.model.SysUserAuthority;
import io.jsonwebtoken.Claims;

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
     */
    TokenInfo resToken(SysUser sysUser);

    /**
     * 获取用户权限
     * @param name
     * @return
     */
    SysUserAuthority getSysUserAuthority(String name);


    void updateToken(HttpServletResponse httpServletResponse, Claims claims,  ResInfo resInfo);


}
