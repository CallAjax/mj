package cn.codesign.web.controller;

import cn.codesign.base.BaseController;
import cn.codesign.data.vo.ResInfo;
import cn.codesign.sys.service.SysCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/4/18
 * Time: 08:22
 * Description:
 */
@RestController
public class TestController extends BaseController {

    @Resource
    private SysCacheService sysCacheServiceImpl;

    @Resource
    private RedisTemplate redisTemplate;


    @PostMapping("/test3")
    public String test3() {
        return "test3";
    }

    @PostMapping("/test2")
    public ResInfo test2() {
        ResInfo resInfo = new ResInfo();
        resInfo.setResCode("SUCCESS");
        return resInfo;
    }
}
