package cn.codesign.config.security;

import cn.codesign.data.mapper.BuLoginMapper;
import cn.codesign.sys.data.mapper.SecurityMapper;
import cn.codesign.sys.data.model.SysAuthority;
import cn.codesign.sys.data.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <p><strong>description</strong> 用户详细信息</p>
 * @author Jiaju Ling
 * @version v1.0
 * @date 2014-3-1
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private SecurityMapper securityMapper;

	@Resource
    private BuLoginMapper buLoginMapper;

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {

        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		List<SysAuthority> list = this.securityMapper.getAuthority(userName);
		if(list.size() == 0){
            return null;
        }

        /**authority名称会影响jwt获取对象里面的key**/
        SimpleGrantedAuthority authority = null;
        for(SysAuthority sysAuthority : list){
            authority  = new SimpleGrantedAuthority(sysAuthority.getId());
            auths.add(authority);
        }

		SysUser sysUser = this.securityMapper.getUser(userName);
		return new UserInfo(userName,sysUser.getUserPwd(),auths,list,sysUser);
	}


}
