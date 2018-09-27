package com.firstproject.service.impl;

import com.firstproject.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/9/27 20:22
 */
@Service("testService")
public class TestServiceImpl implements TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class) ;
    @Override
    public void test() {
        logger.warn("com.firstproject.service.impl.TestServiceImpl测试");
    }
}
