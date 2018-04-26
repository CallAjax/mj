package cn.codesign.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class UserInfo extends User {

	private static final long serialVersionUID = 6577957544860688000L;

	private LinkedHashMap<String,LinkedHashMap<String, List<String>>> menuMap;
	
	
	
	public UserInfo(String username, String password,
                    Collection<? extends GrantedAuthority> authorities, LinkedHashMap<String,LinkedHashMap<String, List<String>>> menuMap) {
		super(username, password, authorities);
		this.menuMap = menuMap;
	}

	public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getMenuMap() {
		return menuMap;
	}
	public void setMenuMap(
			LinkedHashMap<String, LinkedHashMap<String, List<String>>> menuMap) {
		this.menuMap = menuMap;
	}

}
