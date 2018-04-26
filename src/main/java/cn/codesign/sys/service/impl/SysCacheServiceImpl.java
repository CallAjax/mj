package cn.codesign.sys.service.impl;

import cn.codesign.sys.data.mapper.SysDictMapper;
import cn.codesign.sys.data.mapper.SysErrorInfoMapper;
import cn.codesign.sys.data.mapper.SysMenuMapper;
import cn.codesign.sys.data.model.SysDict;
import cn.codesign.sys.data.model.SysErrorInfo;
import cn.codesign.sys.data.model.SysMenu;
import cn.codesign.sys.service.SysCacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysDictMapper sysDictMapper;
    @Resource
    private RedisTemplate redisTemplate;




    /**
     * @User Sam
     * @Date 2017/6/20
     * @Time 09:22
     * @param
     * @return
     * @Description 获取所有错误码
     */
    @Override
    @Cacheable(value = "errorInfo")
    public Map<String,SysErrorInfo> getErrorAll() {
        Map<String,SysErrorInfo> map = new HashMap<String, SysErrorInfo>();
        List<SysErrorInfo> list = this.sysErrorInfoMapper.getAll();
        if(list != null){
            for(SysErrorInfo info : list){
                map.put(info.getErrorCode(), info);
            }
        }
        return map;
    }

    /**
     * @User Sam
     * @Date 2017/6/20
     * @Time 09:22
     * @param
     * @return
     * @Description 获取菜单
     */
    @Override
    @Cacheable(value = "sysMenu")
    public List<SysMenu> getMenu() {
        return this.sysMenuMapper.getMenu();
    }


    /**
     * @User Sam
     * @Date 2017/6/20
     * @Time 09:22
     * @param
     * @return
     * @Description 获取所有字典
     */
    @Override
    @Cacheable(value = "dict")
    public Map<String, Map<String, SysDict>> getDict() {
        List<SysDict> list = this.sysDictMapper.getDict();
        Map<String,Map<String,SysDict>> map = new HashMap<>();
        Map<String,SysDict> dictMap = null;
        String id = "";
        SysDict sysDict;
        for(int i = 0;i < list.size();i++){
            sysDict = list.get(i);
            if(sysDict.getDictLevel() == 1){
                if( i != 0){
                    map.put(id,dictMap);
                }
                id = sysDict.getId();
                dictMap = new HashMap<>();
            }else{
                dictMap.put(sysDict.getDictName(),sysDict);
            }
            if(i == list.size() -1){
                map.put(id,dictMap);
            }
        }
        return map;
    }

    /**
     * @User Sam
     * @Date 2018/4/26
     * @Time 15:44
     * @param key:rediskey, value:redisvlaue, time:过期时间(分钟)
     * @return
     * @Description setString类型到redis，过期时间为time分钟
     */
    @Override
    public void setStringValue(String key, String value, int time) {
        this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }


}
