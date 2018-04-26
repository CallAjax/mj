//package cn.codesign.config.redis;
//
//import cn.codesign.common.util.JacksonUtil;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.JedisCluster;
//
//import javax.annotation.Resource;
//
///**
// * Created with mj.
// * User: Sam
// * Date: 2018/4/20
// * Time: 15:57
// * Description:
// */
//@Component
//public class RedisClusterUtil {
//
//    @Resource
//    private JedisCluster jedisCluster;
//
//
//    /**
//     * 设置set格式缓存
//     * @param key key
//     * @param value value
//     */
//    public void set(String key, String value) {
//        jedisCluster.set(key, value);
//    }
//
//
//    /**
//     * 设置对象缓存
//     * @param key key
//     * @param obj 对象
//     * @param expireTime
//     * @param <T>
//     * @throws Exception
//     */
//    public <T> void setObject(String key, T obj , int expireTime) throws Exception {
//        jedisCluster.setex(key, expireTime, JacksonUtil.toJson(obj));
//    }
//
//
//    /**
//     * 获取对象
//     * @param key key
//     * @return
//     */
//    public String getObject(String key) {
//        return jedisCluster.get(key);
//    }
//
//
//    /**
//     * 判断key是否存在
//     * @param key key
//     * @return
//     */
//    public boolean hasKey(String key) {
//        return jedisCluster.exists(key);
//    }
//
//
//    /**
//     * 设置缓存并制定过期时间
//     * @param key key
//     * @param value value
//     * @param expireTime
//     */
//    public void setWithExpireTime( String key, String value, int expireTime) {
//        jedisCluster.setex(key, expireTime, value);
//    }
//
//
//    /**
//     * 获取缓存
//     * @param key key
//     * @return
//     */
//    public String get(String key) {
//        String value = jedisCluster.get(key);
//        return value;
//    }
//
//    /**
//     * 删除缓存
//     * @param key
//     */
//    public void delete(String key) {
//        jedisCluster.del(key);
//    }
//}
