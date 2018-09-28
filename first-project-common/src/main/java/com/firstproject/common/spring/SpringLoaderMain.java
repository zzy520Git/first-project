package com.firstproject.common.spring;

import com.firstproject.common.bean.Dog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

/**
 * Description:
 * 启动spring服务,启动入口
 * @author zhouzhongyi
 * Date: 2018/8/25 13:41
 */
public class SpringLoaderMain {
    private static final Logger logger = LoggerFactory.getLogger(SpringLoaderMain.class) ;
    private static volatile boolean running = true ;
    private static final Semaphore semaphore = new Semaphore(1) ;

    public static void main(String[] args) {
        try {
            semaphore.acquire();
            try {
                final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-common.xml") ;
                Thread deamon = new Thread(()->{
                    try {
                        context.stop();
                        System.out.println("SpringLoaderMain.class中的spring服务stopped!");
                        logger.info("SpringLoaderMain.class中的spring服务stopped!");
                    } catch (Throwable t) {
                        System.out.println(t.toString());
                        logger.error(t.getMessage(), t);
                    }
                    synchronized (SpringLoaderMain.class) {
                        running = false ;
                        SpringLoaderMain.class.notify();
                    }
                }) ;
                deamon.setDaemon(true);
                Runtime.getRuntime().addShutdownHook(deamon) ;
                context.start();
                logger.info("SpringLoaderMain.class中的spring服务started!");
                System.out.println(LocalDateTime.now().toString() + " SpringLoaderMain.class中的spring服务started!");

                testSpring(context);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
                semaphore.release();
                System.exit(1);
            }
            synchronized (SpringLoaderMain.class) {
                while(running) {
                    try{
                        SpringLoaderMain.class.wait();
                    } catch (Throwable e) {
                    }
                }
            }
        } catch (Throwable e) {
        } finally {
            System.out.println("释放锁许可");
            semaphore.release();
        }
    }

    private static void testSpring(ApplicationContext ctx) {
        Dog dog = ctx.getBean("dog", Dog.class) ;
        System.out.println(dog.getName());
    }
}
