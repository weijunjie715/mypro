package com.wei.config;

import com.wei.service.TimeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName TimeTask
 * @Description : 定时器类
 * @Author weijunjie
 * @Date 2020/9/9 15:03
 */
@Component
@Configuration
@EnableScheduling
public class TimeTask {

    @Autowired
    @Lazy
    private TimeTaskService timeTaskService;

    /**
     * @Description 每天凌晨10点，触发一次文件整体清理操作
     * @Author: weijunjie
     * @Date: 2020/9/9 15:31
     **/
    @Scheduled(cron="0 10 0 * * ?")
    public void taskEndFissionWanted(){
        timeTaskService.makeTimeTask();
    }
}
