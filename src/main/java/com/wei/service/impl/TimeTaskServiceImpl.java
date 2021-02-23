package com.wei.service.impl;

import com.wei.service.TimeTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName TimeTaskServiceImpl
 * @Description :
 * @Author weijunjie
 * @Date 2021/2/23 13:46
 */
@Service
public class TimeTaskServiceImpl implements TimeTaskService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async
    public void makeTimeTask(){
        logger.info("执行了一次定时操作");
    }
}
