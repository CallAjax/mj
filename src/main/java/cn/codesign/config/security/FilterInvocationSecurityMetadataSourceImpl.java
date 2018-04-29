package cn.codesign.config.security;

import cn.codesign.sys.service.SysCacheService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

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


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        Map<String, Collection<ConfigAttribute>> resourceMap = this.SysCacheServiceImpl.getAllAuthority();
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
