package cn.codesign.sys.task;

import cn.codesign.data.mapper.BuLoginMapper;
import cn.codesign.data.model.BuLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * Created with codesign.
 * User: Sam
 * Date: 2017/6/20
 * Time: 23:39
 * Description:
 */
@Component
public class SysTask {

    private static final Logger logger = LoggerFactory.getLogger(SysTask.class);

    @Resource
    private BuLoginMapper buLoginMapper;

    @PostConstruct
    public void start(){

    }

    @PreDestroy
    public void stop(){

    }

    @Scheduled(cron = "1 0 0 * * *")
    public void clearLoginInfo(){
        this.buLoginMapper.clearLoginInfo();
    }
}
