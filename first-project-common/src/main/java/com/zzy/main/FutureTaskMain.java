package com.zzy.main;

import java.util.concurrent.FutureTask;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * @date 2019/3/4 15:47
 */
public class FutureTaskMain {
    public static void main(String[] args) {
        FutureTask<Integer> ftask = new FutureTask<>(()->{
            int sum = 0;
            for(int i=100 ; i>=0 ; i--) {
                Thread.sleep(10);
                sum += 1/i;
            }
            return sum;
        });
        new Thread(ftask).start();
        try {
            int res = ftask.get();
            System.out.println(res);
        } catch (Exception e) {
            System.out.println("11" + e.getMessage());
        }
        System.out.println(222);
    }
}
