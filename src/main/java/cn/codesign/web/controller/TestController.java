package cn.codesign.web.controller;

import cn.codesign.sys.service.SysCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/4/18
 * Time: 08:22
 * Description:
 */
@RestController
@RequestMapping("/service")
public class TestController {

    @Resource
    private SysCacheService sysCacheServiceImpl;

    @Resource
    private RedisTemplate redisTemplate;

//    @Resource
//    private RedisClusterUtil redisClusterUtil;

    @RequestMapping("/test3")
    public String test3() {
        return "test3";
    }

    @RequestMapping("/test2")
    public String test2() {
        this.redisTemplate.opsForValue().set("111","222",1,TimeUnit.MINUTES);
        return "test2";
    }
}
