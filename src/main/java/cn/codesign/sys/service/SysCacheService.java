package cn.codesign.sys.service;

import cn.codesign.sys.data.model.SysDict;
import cn.codesign.sys.data.model.SysErrorInfo;
import cn.codesign.sys.data.model.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * Created with codesign.
 * User: Sam
 * Date: 2017/5/27
 * Time: 15:30
 * Description:
 */
public interface SysCacheService {

    /**
     * @User Sam
     * @Date 2017/6/20
     * @Time 09:21
     * @param
     * @return
     * @Description 获取所有错误码
     */
    Map<String,SysErrorInfo> getErrorAll();


    /**
     * @User Sam
     * @Date 2017/6/20
     * @Time 09:21
     * @param
     * @return
     * @Description 获取菜单
     */
    List<SysMenu> getMenu();


    /**
     * @User Sam
     * @Date 2017/6/20
     * @Time 09:21
     * @param
     * @return
     * @Description 获取所有字典
     */
    Map<String,Map<String,SysDict>> getDict();


    /**
     * @User Sam
     * @Date 2018/4/26
     * @Time 15:44
     * @param key:rediskey, value:redisvlaue, time:过期时间(分钟)
     * @return
     * @Description setString类型到redis，过期时间为time分钟
     */
    void setStringValue(String key, String value, int time);
}
