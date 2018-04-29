package cn.codesign.config.security;

import cn.codesign.sys.data.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class UserInfo extends User {

	private static final long serialVersionUID = 6577957544860688000L;

	private LinkedHashMap<String,LinkedHashMap<String, List<String>>> menuMap;

	private SysUser sysUser;
	
	
	
	public UserInfo(String username, String password,
                    Collection<? extends GrantedAuthority> authorities, LinkedHashMap<String,LinkedHashMap<String, List<String>>> menuMap, SysUser sysUser) {
		super(username, password, authorities);
		this.menuMap = menuMap;
		this.sysUser = sysUser;
	}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getMenuMap() {
		return menuMap;
	}
	public void setMenuMap(
			LinkedHashMap<String, LinkedHashMap<String, List<String>>> menuMap) {
		this.menuMap = menuMap;
	}

}
