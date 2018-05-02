package cn.codesign.config.security;

import cn.codesign.common.util.BusConstant;
import cn.codesign.common.util.SysConstant;
import cn.codesign.data.mapper.BuLoginMapper;
import cn.codesign.data.model.BuLogin;
import cn.codesign.sys.data.model.SysDict;
import cn.codesign.sys.service.SysCacheService;
import cn.codesign.sys.service.SysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with codesign.
 * User: Sam
 * Date: 2017/3/27
 * Time: 10:22
 * Description: 自定义检测用户登录合法性
 */
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

    @Resource
    private  UserDetailsService userDetailsService;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private HttpServletResponse httpServletResponse;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private BuLoginMapper buLoginMapper;

    @Resource
    private SysCacheService sysCacheServiceImpl;

    @Resource
    private SysService sysServiceImpl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        Object password = token.getCredentials();

        if(null == username || "".equals(username.trim())){
            throw new UsernameNotFoundException(SysConstant.LOGIN_USERNAME_NULL);
        }

        if(null == password || "".equals(password.toString().trim())){
            throw new UsernameNotFoundException(SysConstant.LOGIN_PASSWORD_NULL);
        }

        boolean isVerify = false;

        //用户提交验证码
        String verifyCode = this.httpServletRequest.getParameter(SysConstant.JWT_PARAM_CODE);
        String codeId = this.httpServletRequest.getParameter(SysConstant.JWT_PARAM_CODE_ID);

        //redis中的验证码
        String code = null;
        if(codeId != null && !"".equals(codeId)) {
            code = this.sysCacheServiceImpl.getStringvalue(codeId);
        }


        Map<String,Map<String,SysDict>> map = sysCacheServiceImpl.getDict();
        int max = Integer.parseInt(map.get(BusConstant.DICT_LOGIN).get(BusConstant.DICT_LOGIN_MAX).getDictValue());

        //是否验证验证码
        BuLogin buLogin = this.buLoginMapper.getLogin(username);
        if(buLogin != null){//当天曾经登陆过
            if(buLogin.getLoginStatus() == 0){//上一次登陆失败
                if(buLogin.getLoginCount() >= max){//超出登陆次数上限
                    throw new UsernameNotFoundException(SysConstant.LOGIN_MAX_ERROR);
                }
                isVerify = true;
            }
        }

        //验证验证码
        if(isVerify){
            if(code == null){
                throw new UsernameNotFoundException(SysConstant.LOGIN_VERIFY_ERROR);
            }
            if(!code.equals(verifyCode)){
                throw new UsernameNotFoundException(SysConstant.LOGIN_VERIFY_ERROR);
            }
        }

        //从数据库找到的用户
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);



        //比对数据库用户的密码
        if(userDetails == null ||
                !bCryptPasswordEncoder.matches(password.toString(),userDetails.getPassword())){
            //记录失败用户名
            this.buLoginMapper.insertLoginInfo(username);
            throw new UsernameNotFoundException(SysConstant.SECURITY_NAME_OR_PWD_ERROR);
        }

        //用户被禁止登陆
        if(((UserInfo)userDetails).getSysUser().getUserStatus() == SysConstant.USER_STATUS_PROHIBITED) {
            throw new UsernameNotFoundException(SysConstant.USER_PROHIBITED);
        }

        //更新登陆信息表
        this.buLoginMapper.updateLoginInfo(username);

        //写token
        try {
            this.sysServiceImpl.resToken(this.httpServletResponse,(UserInfo)userDetails);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        /**
         * 不用session，所有参数为null
         */
        return new UsernamePasswordAuthenticationToken(null, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
