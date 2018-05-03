package cn.codesign.sys.service.impl;

import cn.codesign.common.util.SysConstant;
import cn.codesign.sys.data.mapper.BuIgnoreServiceMapper;
import cn.codesign.sys.data.mapper.SecurityMapper;
import cn.codesign.sys.data.mapper.SysDictMapper;
import cn.codesign.sys.data.mapper.SysErrorInfoMapper;
import cn.codesign.sys.data.model.SysDict;
import cn.codesign.sys.data.model.SysErrorInfo;
import cn.codesign.sys.service.SysCacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created with codesign.
 * User: Sam
 * Date: 2017/5/27
 * Time: 15:32
 * Description:
 */
@Service
public class SysCacheServiceImpl implements SysCacheService {

    @Resource
    private SysErrorInfoMapper sysErrorInfoMapper;
    @Resource
    private SysDictMapper sysDictMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SecurityMapper securityMapper;
    @Resource
    private BuIgnoreServiceMapper buIgnoreServiceMapper;


    /**
     * @param
     * @return
     * @User Sam
     * @Date 2017/6/20
     * @Time 09:22
     * @Description 获取所有错误码
     */
    @Override
    @Cacheable(value = "errorInfo")
    public Map<String, SysErrorInfo> getErrorAll() {
        Map<String, SysErrorInfo> map = new HashMap<String, SysErrorInfo>();
        List<SysErrorInfo> list = this.sysErrorInfoMapper.getAll();
        if (list != null) {
            for (SysErrorInfo info : list) {
                map.put(info.getErrorCode(), info);
            }
        }
        return map;
    }

    /**
     * @param
     * @return
     * @User Sam
     * @Date 2017/6/20
     * @Time 09:22
     * @Description 获取所有字典
     */
    @Override
    @Cacheable(value = "dict")
    public Map<String, Map<String, SysDict>> getDict() {
        List<SysDict> list = this.sysDictMapper.getDict();
        Map<String, Map<String, SysDict>> map = new HashMap<>();
        Map<String, SysDict> dictMap = null;
        String id = "";
        SysDict sysDict;
        for (int i = 0; i < list.size(); i++) {
            sysDict = list.get(i);
            if (sysDict.getDictLevel() == 1) {
                if (i != 0) {
                    map.put(id, dictMap);
                }
                id = sysDict.getId();
                dictMap = new HashMap<>();
            } else {
                dictMap.put(sysDict.getDictName(), sysDict);
            }
            if (i == list.size() - 1) {
                map.put(id, dictMap);
            }
        }
        return map;
    }


    /**
     * @param
     * @return
     * @User Sam
     * @Date 2018/4/30
     * @Time 01:49
     * @Description 获取所有权限
     */
    @Override
    @Cacheable(value = "Authority")
    public Map<String, Collection<ConfigAttribute>> getAllAuthority() {
        Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<>();
        List<Map<String, String>> resList = this.securityMapper.getAllAuthority();
        for (Map<String, String> map : resList) {
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
        return resourceMap;
    }

    /**
     * @param key:rediskey, value:redisvlaue, time:过期时间(分钟)
     * @return
     * @User Sam
     * @Date 2018/4/26
     * @Time 15:44
     * @Description setString类型到redis，过期时间为time分钟
     */
    @Override
    public void setStringValue(String key, String value, int time) {
        this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }


    /**
     * @param key:rediskey
     * @return
     * @User Sam
     * @Date 2018/4/26
     * @Time 15:53
     * @Description 根据key获取redis中String值
     */
    @Override
    public String getStringvalue(String key) {
        return String.valueOf(this.redisTemplate.opsForValue().get(key));
    }


    /**
     * 获取不受security和jwt保护的服务
     * @return
     */
    @Override
    @Cacheable(value = "IgnoreService")
    public List<String> getIgnoreService() {
        return this.buIgnoreServiceMapper.getIgnoreService();
    }


}
