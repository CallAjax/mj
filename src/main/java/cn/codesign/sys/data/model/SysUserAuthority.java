package cn.codesign.sys.data.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/5/2
 * Time: 19:05
 * Description:
 */
public class SysUserAuthority {

    private Collection<? extends GrantedAuthority> authorities;
    private List<SysAuthority> sysAuthority;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public List<SysAuthority> getSysAuthority() {
        return sysAuthority;
    }

    public void setSysAuthority(List<SysAuthority> sysAuthority) {
        this.sysAuthority = sysAuthority;
    }
}
