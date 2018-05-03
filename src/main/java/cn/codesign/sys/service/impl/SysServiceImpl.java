package cn.codesign.sys.service.impl;

import cn.codesign.common.util.SysConstant;
import cn.codesign.config.security.JwtUtil;
import cn.codesign.config.security.TokenInfo;
import cn.codesign.sys.data.mapper.SecurityMapper;
import cn.codesign.sys.data.model.SysAuthority;
import cn.codesign.sys.data.model.SysUser;
import cn.codesign.sys.data.model.SysUserAuthority;
import cn.codesign.sys.service.SysService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(SysServiceImpl.class);

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private SecurityMapper securityMapper;

    @Value("${jwt.update}")
    private long time;



    /**
     * @User Sam
     * @Date 2018/5/3
     * @Time 08:38
     * @param
     * @return
     * @Description 生成token信息
     */
    @Override
    public TokenInfo resToken(SysUser sysUser) {
        SysUserAuthority sysUserAuthority = getSysUserAuthority(sysUser.getUserName());
        List<SysAuthority> list = sysUserAuthority.getSysAuthority();
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

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(this.jwtUtil.getJwtToken(sysUser.getUserName(),sysUserAuthority.getAuthorities()));
        tokenInfo.setRoutes(map1);
        tokenInfo.setMenu(map2);
        return tokenInfo;
    }

    /**
     * 获取用户权限
     * @param name
     * @return
     */
    @Override
    public SysUserAuthority getSysUserAuthority(String name) {
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        List<SysAuthority> list = this.securityMapper.getAuthority(name);
        if(list.size() == 0){
            return null;
        }

        /**authority名称会影响jwt获取对象里面的key**/
        SimpleGrantedAuthority authority = null;
        for(SysAuthority sysAuthority : list){
            authority  = new SimpleGrantedAuthority(sysAuthority.getId());
            auths.add(authority);
        }
        SysUserAuthority sysUserAuthority = new SysUserAuthority();
        sysUserAuthority.setSysAuthority(list);
        sysUserAuthority.setAuthorities(auths);
        return sysUserAuthority;
    }


    /**
     * 验证并更新token
     * @param claims
     * @return
     */
    @Override
    public List<GrantedAuthority> validateAndUpdate(Claims claims,HttpServletResponse httpServletResponse) {
        List<GrantedAuthority> auths = null;
        long t = claims.getExpiration().getTime() - System.currentTimeMillis();

        SysUser sysUser = null;
        if (t < this.time) {//需要更新token
            sysUser = this.securityMapper.getUser(claims.getSubject());
            //检查用户状态
            if(sysUser.getUserStatus() == SysConstant.USER_STATUS_PROHIBITED) {
                LOGGER.warn(claims.getSubject() + ":" + SysConstant.USER_PROHIBITED);
                return null;
            }

            SysUserAuthority sysUserAuthority = getSysUserAuthority(sysUser.getUserName());

            try {
                //resToken(httpServletResponse, sysUserAuthority, sysUser);
                auths = (List<GrantedAuthority>) sysUserAuthority.getAuthorities();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return null;
            }
        } else {
            auths = new ArrayList<>();
            List<Map<String,String>> list = (List<Map<String, String>>) claims.get(SysConstant.JWT_AUTH);
            for(Map<String,String> map : list) {
                auths.add(new SimpleGrantedAuthority(map.get(SysConstant.JWT_AUTHORITY)));
            }
        }
        return auths;
    }
}
