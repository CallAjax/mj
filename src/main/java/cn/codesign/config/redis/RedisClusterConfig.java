//package cn.codesign.config.redis;
//
//
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Created with mj.
// * User: Sam
// * Date: 2018/4/20
// * Time: 14:51
// * Description:
// */
//@Configuration
//public class RedisClusterConfig {
//
//
//    @Value("${spring.redis.cluster.nodes}")
//    private String clusterNodes;
//
//
//    @Bean
//    public JedisCluster jedisCluster() {
//
//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//        genericObjectPoolConfig.setMaxIdle(5);
//        genericObjectPoolConfig.setMaxTotal(20);
//        genericObjectPoolConfig.setMinIdle(2);
//        genericObjectPoolConfig.setMaxWaitMillis(5000);
//
//        Set<HostAndPort> nodes = new HashSet<>();
//        Arrays.stream(clusterNodes.split(",")).map(v -> v.split(":"))
//                .forEach(v -> nodes.add(new HostAndPort(v[0].trim(),Integer.parseInt(v[1].trim()))));
//        return new JedisCluster(nodes, 5000, 1000, 1, "test123", genericObjectPoolConfig);
//    }
//}
