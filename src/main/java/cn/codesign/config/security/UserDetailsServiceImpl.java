package cn.codesign.config.security;

import cn.codesign.data.mapper.BuLoginMapper;
import cn.codesign.sys.data.mapper.SecurityMapper;
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
		List<String> list = this.securityMapper.getAuthority(userName);
		if(list.size() == 0){
            return null;
        }

        SimpleGrantedAuthority authority = null;
        for(String str : list){
            authority  = new SimpleGrantedAuthority(str);
            auths.add(authority);
        }

		SysUser sysUser = this.securityMapper.getUser(userName);
		return new UserInfo(userName,sysUser.getUserPwd(),auths,null);
	}


	/**
	 * <p><strong>title</strong> getMenu</p>
	 * <p><strong>description</strong> 获取用户菜单</p>
	 * @author Jiaju Ling
	 * @date 2014年5月31日
	 * @param mobilePhone
	 */
//	private LinkedHashMap<String,LinkedHashMap<String, List<String>>> getMenu(String mobilePhone){
//		//{SEQUENCE=1, ID=1000010100, REMARK=角色管理, URL=/page/authority/index.jsp, MENU_LEVEL=2, PARENT_ID=1000010000}
//		List<Map<String,String>> result = this.securityMapper.getMenu(mobilePhone);
//		//{1000010000|权限管理={1000010100|角色管理|/page/authority/index.jsp=[1000010101]}}
//		LinkedHashMap<String,LinkedHashMap<String, List<String>>> menuMap =
//				new LinkedHashMap<String,LinkedHashMap<String, List<String>>>();
//		LinkedHashMap<String, List<String>> menuLv2 = null;
//		List<String> menuLv3 = null;
//		StringBuffer s1 = new StringBuffer();
//		StringBuffer s2 = new StringBuffer();
//		for(Map<String,String> m1 : result){
//			if(SysConstant.LEVEL_1.equals(m1.get(SysConstant.MENU_LEVEL))){
//				s1.append(m1.get(SysConstant.ID)).append(SysConstant.SPLIT).append(m1.get(SysConstant.REMARK));
//				menuLv2 = new LinkedHashMap<String, List<String>>();
//				for(Map<String,String> m2 : result){
//					if(SysConstant.LEVEL_2.equals(m2.get(SysConstant.MENU_LEVEL)) && m1.get(SysConstant.ID).equals(m2.get(SysConstant.PARENT_ID))){
//						s2.append(m2.get(SysConstant.ID)).append(SysConstant.SPLIT).append(m2.get(SysConstant.REMARK)).append(SysConstant.SPLIT).append(m2.get(SysConstant.URL));
//						menuLv3 = new ArrayList<String>();
//						for(Map<String,String> m3 : result){
//							if(SysConstant.LEVEL_3.equals(m3.get(SysConstant.MENU_LEVEL)) && m2.get(SysConstant.ID).equals(m3.get(SysConstant.PARENT_ID))){
//								menuLv3.add(m3.get(SysConstant.ID));
//							}
//						}
//						menuLv2.put(s2.toString(), menuLv3);
//						s2.delete(0, s2.length());
//					}
//				}
//				menuMap.put(s1.toString(), menuLv2);
//				s1.delete(0, s1.length());
//			}
//		}
//		return menuMap;
//	}
}
