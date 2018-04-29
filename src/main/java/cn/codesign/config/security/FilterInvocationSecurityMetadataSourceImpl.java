package cn.codesign.config.security;

import cn.codesign.common.util.SysConstant;
import cn.codesign.sys.service.SysCacheService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with codesign.
 * User: Sam
 * Date: 2017/3/23
 * Time: 16:17
 * Description:
 */
@Component
public class FilterInvocationSecurityMetadataSourceImpl implements
        FilterInvocationSecurityMetadataSource {

    @Resource
    private SysCacheService SysCacheServiceImpl;

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    private void loadResourceDefine(){
        resourceMap = new HashMap<>();
        List<Map<String,String>> resList = this.SysCacheServiceImpl.getAllAuthority();
        for(Map<String,String> map : resList){
            ConfigAttribute ca = new SecurityConfig(map.get(SysConstant.SECURITY_AUTHORITY_ID));
            if (resourceMap.containsKey(map.get(SysConstant.SECURITY_URL))) {
                Collection<ConfigAttribute> value = resourceMap.get(map.get(SysConstant.SECURITY_URL));
                value.add(ca);
                resourceMap.put(map.get(SysConstant.SECURITY_URL), value);
            } else {
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                atts.add(ca);
                resourceMap.put(map.get(SysConstant.SECURITY_URL), atts);
            }
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        if(resourceMap == null) {
            loadResourceDefine();
        }
        String url = ((FilterInvocation)object).getRequestUrl();

        int firstQuestionMarkIndex = url.indexOf("?");

        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        if(resourceMap.containsKey(url)){
            return resourceMap.get(url);
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}
