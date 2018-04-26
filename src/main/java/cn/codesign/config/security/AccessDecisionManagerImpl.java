package cn.codesign.config.security;

import cn.codesign.common.util.SysConstant;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class AccessDecisionManagerImpl  implements AccessDecisionManager {


    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttribute) throws AccessDeniedException,
            InsufficientAuthenticationException {

        if (configAttribute == null) {
            return;
        }

        for(ConfigAttribute ca : configAttribute){
            String needRole = ca.getAttribute();
            for(GrantedAuthority ga :authentication.getAuthorities()){
                if(needRole.trim().equals(ga.getAuthority().trim())){
                    return;
                }
            }
        }

        throw new AccessDeniedException(SysConstant.SECURITY_AUTHORITY_ERROR);
    }

    @Override
    public boolean supports(ConfigAttribute arg0) {
        return true;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}
