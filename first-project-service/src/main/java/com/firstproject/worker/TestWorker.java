package com.firstproject.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Description：
 * 任务worker
 * @author zhouzhongyi1
 * DATE： 2018/9/28 11:30
 */
@Service("testWorker")
public class TestWorker {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestWorker.class) ;
    //必须public
    public void doWork1() {
        LOGGER.warn("测试doWork1：时间戳={}ms", System.currentTimeMillis());
    }

    //必须public
    public void doWork2() {
        LOGGER.warn("测试doWork2：时间戳={}ms", System.currentTimeMillis());
    }

    public void doWork3() {
        LOGGER.warn("task-schedule测试doWork3：时间戳={}ms", System.currentTimeMillis());
    }
}
