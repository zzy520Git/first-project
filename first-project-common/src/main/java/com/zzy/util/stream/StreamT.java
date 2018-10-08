package com.zzy.util.stream;

import com.firstproject.common.bean.Dog;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/9/30 10:04
 */
public class StreamT {
    public static void main(String[] args) {
        List<Dog> dogs = Stream.of(new Dog("小白"), new Dog("皮蛋"),
                new Dog("轮胎")).collect(Collectors.toList());
        //List<Dog> dogs = new ArrayList<>(0) ;
//        dogs.add(new Dog("abc")) ;
//        dogs.forEach(e->System.out.println(e.getName()));
//        List<String> names = dogs.stream().map(Dog::getName).collect(Collectors.toList());
//        names.forEach(System.out::println);
//
//        StreamT st = new StreamT() ;
//        st.test();
//        st.flatMap();
        dogs.parallelStream().forEach(e->{
            System.out.println(e.getName()) ;
        });
    }

    public void test() {
        //测试lambda表达式中的this值
        List<String> ts = Arrays.asList("a", "b", "c") ;
        ts.forEach(e-> System.out.println(this==StreamT.this) );
    }

    public void flatMap() {
        //测试flatMap
        Stream.of("1#2", "3#4", "5#6").flatMap(str-> Stream.of(str.split("#"))).forEach(System.out::println);
    }
}
