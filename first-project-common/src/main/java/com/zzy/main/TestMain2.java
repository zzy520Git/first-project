package com.zzy.main;

import lombok.Getter;
import lombok.Setter;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * @date 2019/1/25 16:25
 */
public class TestMain2 {
    public static void main(String[] args) {

//        List<Person> pList = Arrays.asList(
//                new Person(1,1996),
//                new Person(2,2393),
//                new Person(3,193),
//                new Person(4,3993),
//                new Person(5,1934),
//                new Person(6,8893)
//        );
//        List<Integer> res = pList.stream()
//                .flatMap(o -> Stream.of(o.getAge(), o.getYear()))
//                .collect(Collectors.toList());
//        System.out.println(Arrays.toString(res.toArray()));

    }
}

@Setter
@Getter
class Person {
    Person(int age, int year) {
        this.age = age;
        this.year = year;
    }
    Integer age;
    Integer year;
}
