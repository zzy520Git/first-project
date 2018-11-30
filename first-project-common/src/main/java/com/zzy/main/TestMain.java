package com.zzy.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/10/19 14:18
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        System.out.println(System.currentTimeMillis());
        //运行得线程直接调用直接阻塞
        LockSupport.unpark(Thread.currentThread());
        //必须wrap by loop
        LockSupport.park();
        System.out.println(System.currentTimeMillis());
        ThreadPoolExecutor executor ;
        ReentrantLock lock ;
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService = Executors.newCachedThreadPool();
        LinkedBlockingQueue q ;
        executorService = Executors.newWorkStealingPool();
        List<String> s = new ArrayList<>();
        s.stream();
        //必须16位以上，且必须8的整数倍
//        String key = "1hyu349j1leu340j2hgt43og" ;
//        int count = 0 ;
//        for(int i=0 ; i<1000 ; i++) {
//            String content = UUID.randomUUID().toString() ;
//            String enc = AESCipher.encrypt(content, key) ;
//            String dec = AESCipher.decrypt(enc, key) ;
//            if(content.equals(dec)) {
//                count++ ;
//            }
//        }
//        System.out.println(count);
//        int count = 0 ;
//        for(int i=0 ; i<10 ; i++) {
//            String s = UUID.randomUUID().toString() ;
//            String abc = Md5Util.encrypt(s) ;
//            System.out.println(abc);
//        }
//        String s = "\\\\aha" ;
//        System.out.println(s);
//        System.out.println(s.replaceAll("[\\\\]+", "7"));

    }

}
