package cn.codesign.config.security;

import cn.codesign.sys.data.model.SysAuthority;
import cn.codesign.sys.data.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class UserInfo extends User {

	private static final long serialVersionUID = 6577957544860688000L;
	private List<SysAuthority> sysAuthority;
	private SysUser sysUser;
	
	
	
	public UserInfo(String username, String password,
                    Collection<? extends GrantedAuthority> authorities, List<SysAuthority> sysAuthority, SysUser sysUser) {
		super(username, password, authorities);
		this.sysUser = sysUser;
		this.sysAuthority = sysAuthority;
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

    public List<SysAuthority> getSysAuthority() {
        return sysAuthority;
    }

    public void setSysAuthority(List<SysAuthority> sysAuthority) {
        this.sysAuthority = sysAuthority;
    }
}
