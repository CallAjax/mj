package cn.codesign.config.security;

import cn.codesign.sys.data.model.SysAuthority;

import java.util.List;
import java.util.Map;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/5/3
 * Time: 08:29
 * Description:
 */
public class TokenInfo {

    private String token;
    private Map<String,List<SysAuthority>> routes;
    private Map<String,List<SysAuthority>> menu;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, List<SysAuthority>> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, List<SysAuthority>> routes) {
        this.routes = routes;
    }

    public Map<String, List<SysAuthority>> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, List<SysAuthority>> menu) {
        this.menu = menu;
    }
}
