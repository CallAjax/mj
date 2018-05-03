package cn.codesign.base;

import cn.codesign.sys.data.model.SysErrorInfo;
import cn.codesign.sys.service.SysCacheService;
import org.apache.commons.beanutils.BeanUtils;

import javax.annotation.Resource;

/**
 * Created with codesign.
 * User: Sam
 * Date: 2017/5/27
 * Time: 15:25
 * Description:
 */
public abstract class BaseService {

    @Resource
    private SysCacheService sysCacheServiceImpl;
    
    /**
     * @User Sam
     * @Date 2017/5/27
     * @Time 15:27
     * @param 
     * @return 
     * @Description 获取所有错误码，并copy对象，防止地址传递
     */
    public SysErrorInfo getErrorInfo(String errorCode) throws Exception{
        return (SysErrorInfo) BeanUtils.cloneBean(this.sysCacheServiceImpl.getErrorAll().get(errorCode));
    }
}
