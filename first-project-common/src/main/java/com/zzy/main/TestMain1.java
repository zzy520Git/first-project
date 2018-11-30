package com.zzy.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/11/9 13:02
 */
public class TestMain1 {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("gy.jd.com.");
        System.out.println(sb.substring(0, sb.length()-1));

        Runnable a = ()->{
            //dosomething
        };
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.forEach(System.out::println);

        String s = "1,3,5,7,9";
        List<Long> data = Stream.of(s.split(",")).map(Long::valueOf).collect(Collectors.toList());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date d = calendar.getTime();
        System.out.println(d.toString());

    }
}

