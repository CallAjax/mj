package cn.codesign.config.security;

import cn.codesign.sys.data.model.SysAuthority;

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
    private Map<String,Map<String,SysAuthority>> routes;
    private Map<String,Map<String,SysAuthority>> menu;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Map<String, SysAuthority>> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, Map<String, SysAuthority>> routes) {
        this.routes = routes;
    }

    public Map<String, Map<String, SysAuthority>> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, Map<String, SysAuthority>> menu) {
        this.menu = menu;
    }
}
