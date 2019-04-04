package com.yb.shiro;

import org.assertj.core.util.Lists;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Author yangbiao
 * Description:练习lambda的类
 */
public class User implements Serializable {
    private static final long serialVersionUID = -3977788351593091491L;

    //***************************************************************************************
    public static void main(String[] args) {
        ArrayList<Object> list = Lists.newArrayList();
        list.add(new User("jack",16));
        list.add(new User("rose",17));
        list.add(new User("jim",19));
        list.add(new User("mary",18));
        //获取流stream
        Stream<Object> stream = list.stream();
        Stream<Object> objectStream = list.parallelStream();
        Stream<String> aa = Stream.of("aa");
        Stream<Integer> integerStream = Stream.of(12);
        IntStream intStream1 = IntStream.of(11,12,13,14);
        Optional<Integer> max = integerStream.filter(s -> s > 10).reduce(Integer::max);
        System.out.println(max.get());
//        long count = intStream1.filter(s -> s < 13).map(s -> {
//            return 0;
//        }).count();
//        System.err.println(count);
        //map这里相当于又把它当成一个输入流变成一个输出流来进行操作
//        int sum = intStream1.filter(s -> s < 13).map(s -> {
//            return s+1;
//        }).sum();
//        System.err.println(sum);
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> collect = integers.stream().filter(s -> s > 2).map(s -> s + 2).collect(Collectors.toList());
//        System.err.println(collect.toString());
        //
        intStream1.peek(s-> System.err.println(s+"aaa")).forEach(System.out::println);
        System.out.println("000000000");
//        integers.forEach(s-> System.err.println(s.toString()));
    }


//***************************************************************************************
    /**
     * id
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;

    public User() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    public User(String name, Integer age) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}