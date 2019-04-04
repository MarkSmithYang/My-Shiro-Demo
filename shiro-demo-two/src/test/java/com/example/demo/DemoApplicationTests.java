package com.example.demo;

import com.example.demo.other.User;
import com.example.demo.other.UserDao;
import com.example.demo.other.UsersRepository;
import org.apache.commons.collections.*;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Preconditions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserDao userDao;

    /**
     * 一般小测试
     */
    @Test
    public void contextLoads3() {
        List<User> list = Lists.newArrayList();
        //获取流---->流是一次性的,返回的是stream都是中间方法,返回其他的是终点方法
        Stream<User> stream = list.stream();
        //过滤流
        Stream<User> userStream = stream.filter(s -> s.getAge() > 18);
        long count = userStream.map((User user) -> {
            System.err.println(user.getAge());
//            return user.getName();
            return null;
        }).count();
        System.err.println("------------"+count+"------------");
        List<String> words = Arrays.asList("Java8", "Lambdas", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths.toString());

        Optional<Integer> reduce = wordLengths.stream().reduce(Integer::max);
        Preconditions.checkNotNullOrEmpty("s", "不能为空");
        System.out.println("ssssssssssssssss");
        //
        com.google.common.base.Preconditions.checkArgument(0==1);
    }

    @Test
    public void contextLoads2() {
        List<User> all = usersRepository.findAll();
        if (all != null && !all.isEmpty()) {
//            并行（parallel）数组
            Arrays.parallelSort(new String[]{"1","2"});
            Arrays.parallelSetAll(new String[]{"1","2"}, s->s>0);
            //没有开启的时候的输出
            all.stream().forEach(s-> System.err.println("未并行的:"+s.toString()));
            //直接开启并行流
            all.parallelStream().forEach(s-> System.err.println(s.toString()));
            //另一种开启并行的方式
            all.stream().parallel().forEach(s-> System.err.println(s.toString()));
            Stream<User> parallel1 = all.stream().parallel();
            Stream<User> stream = all.stream();
           if(parallel1.isParallel()){
               System.err.println("parallel1是并行流");
           }
           if(stream.isParallel()){
               System.err.println("stream是并行流");
           }
        }
    }

    /**
     * peek和CollectionUtils等的使用
     */
    @Test
    public void contextLoads() {
        List<User> all = usersRepository.findAll();
        if (all != null && !all.isEmpty()) {
            long count = all.stream().peek(s -> System.err.println(s.toString())).count();
            System.out.println(count);
        } else {
            System.err.println("集合为空");
        }
        System.err.println("--------------------------------------------------------------");
        List<User> list = userDao.findAll();
        if (CollectionUtils.isNotEmpty(list)) {
            long count = list.stream().peek(s -> System.out.println(s.toString())).count();
            System.out.println(count);
        } else {
            System.err.println("集合为空");
        }
    }

    /**
     * CollectionUtils,MapUtils,SetUtils,ListUtils的使用,很多功能用lambda轻松搞定,不必要那么麻烦
     */
    @Test
    public void contextLoads1() {
        String sql = "select * from users";
//        String sql = "select * from (select id,`name`,age,firstName from users where age=20) t";
        Query query = entityManager.createNativeQuery(sql, User.class);
        query.setFirstResult(0);
        query.setMaxResults(2);
        List<User> list = query.getResultList();
        if (list != null && !list.isEmpty()) {
            list.forEach(s -> System.err.println(s.getId()));
//            list.forEach(s -> System.err.println(((Object[])s)[0]));
        }
        Map map = MapUtils.fixedSizeMap(new HashMap());
        Set set = SetUtils.orderedSet(new HashSet());
        List list1 = ListUtils.fixedSizeList(new ArrayList());
        CollectionUtils.filter(set, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return false;
            }
        });
        //测试集合过滤--------->感觉没什么场景,这些东西用Lambda的filte更轻松的做到
        System.err.println("bbb:" + list);//bbb:[User{id='1', name='jack', age=20}, User{id='2', name='rose', age=21}]
        //处理自己的过滤逻辑
        CollectionUtils.filter(list, (a) -> {
            System.err.println("哈哈,我实现了evaluate方法");
            if (CollectionUtils.isNotEmpty(list) && list.get(0).getAge() > 20) {
                System.err.println(true);
                return true;
            } else {
                System.err.println(false);
                return false;
            }
        });
        //------------------------------lambda版本-----------------------------------
        List<User> collect = list.stream().filter(s -> s.getAge() > 20).collect(Collectors.toList());
        System.err.println("lambda版本:" + collect);//lambda版本:[User{id='2', name='rose', age=21}]
        //---------------------------------------------------------------------------
        System.err.println("aaa:" + list);//aaa:[User{id='2', name='rose', age=21}]
    }

    /**
     * Optional类和三元表达式的使用
     */
    @Test
    public void MeTest() {
        Optional<String> o = Optional.ofNullable(null);
        String a = o.orElse("空");
        System.err.println(a);
        System.err.println("--------------");
        Optional<Object> oo = Optional.of("aa");
        Object aa = !oo.isPresent() ? null : oo.orElse("空");
        Object aaa = !Optional.ofNullable(11).isPresent() ? null : oo.orElseGet(() -> {
            System.err.println("我是实现了get方法的对象(多态)");
            return 100;
        });
        Optional<Integer> integer = Optional.ofNullable(11);
        Integer integer1 = integer.orElseGet(() -> {
            System.err.println("我是实现了get方法的对象(多态)");
            return 100;
        });//--------------------------------------->11

        //当Optional返回的结果 optional.isPreset()为true,那么就返回optional.get(),
        // 如果有optional.orElse()或者optional.orElseGet()的话,返回此中的值,一个是直接的参数,一个是实现方法返回的值
        Optional<Integer> integer11 = Optional.ofNullable(null);
        Integer integer2 = integer11.orElseGet(() -> {
            System.err.println("我是实现了get方法的对象(多态)");
            return 100;
        });//-------------------------------------->100

        System.err.println(integer1);
        System.err.println(integer2);
        System.err.println(aa);
        System.err.println(aaa);
    }

    /**
     *
     */
    @Test
    public void MeTest7() {

    }

    /**
     * Stream.iterate(xxx,yyy)----->自己生成流
     */
    @Test
    public void MeTest6() {
        //进阶：自己生成流--->不管那种方式,都需要limt()来限制数量,不能让其无限制的下去---->Stream.iterate(xxx,yyy)
        //iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。
        // 然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推。
        List<Integer> collect = Stream.iterate(0, s -> s + 2).limit(5).collect(Collectors.toList());
        //这里就像是通过seed(初始值),传参到后面的表达式中进行计算出新的值给seed就好像这样就会反复形成新的值,就形成了
        //Integer类型的集合
        System.err.println(collect);//[0, 2, 4, 6, 8]-->集合不用toString,可以这样展示数据
    }

    /**
     * Stream.generate(xxx)----->自己生成流
     */
    @Test
    public void MeTest5() {
        //进阶：自己生成流--->不管那种方式,都需要limt()来限制数量,不能让其无限制的下去----> Stream.generate(xxx)
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
//        Function<Integer, Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(s -> System.err.println(s));
        new Thread(() -> {
            System.err.println("哈哈,我实现了Runnable的run的方法");
        }).start();
        //-------------------------------------------------------------------------
        //通过自己实现Supplier的方式来创建流
        long count = Stream.generate(() -> {
            System.err.println("我实现了Supplier的get方法");
            return 0;
        }).limit(5).count();
        //因为接口的方法是void 的--> void run();
        Runnable a = () -> {
            System.err.println("你好吗?");
        };
        //因为接口里的方法get是 T get();所以是()->{return xx;}
        Supplier<Object> aa = () -> {
            System.err.println(1);
            return 0;
        };
        //所以写函数式接口的时候需要注意接口唯一方法的返回值和参数这个决定了lambda的写法(语法)
        //例如
//        public interface TestOne {
//            public int ge(String s);
//        }
//    }
        //就需要这样写了(带字符串类型的变量,返回int类型的值)
//        TestOne to = (a)->{
//            return 1;
//        };
    }

    /**
     * allMath-----noneMatch-----antMatch的使用
     */
    @Test
    public void MeTest4() {
        //allMath-----noneMatch-----antMatch的使用
        // allMatch：Stream 中全部元素符合传入的 predicate，返回 true
        List<User> list = usersRepository.findAll();
        list.stream().forEach(s -> System.err.println(s.toString()));
        boolean b = list.stream().allMatch(s -> s.getAge() > 18);
        boolean b1 = list.stream().noneMatch(s -> s.getAge() > 22);
        boolean b2 = list.stream().anyMatch(s -> s.getAge() > 18);
        System.err.println(b);
        System.err.println(b1);
        System.err.println(b2);
        if (b) {
            System.err.println("全部成年了");
        } else if (list.stream().allMatch(s -> s.getAge() >= 6)) {
            System.err.println("在6岁到18岁之间");
        } else {
            System.err.println("6岁以下");
        }
        // anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
        // noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
    }

    /**
     * boxed()把基本数值类型的包装
     */
    @Test
    public void MeTest3() {
        List<String> list = Arrays.asList("C", "Java", "Python", "Go");
        //boxed()把int类型的数值包装成Integer类型,只有这样才能封装成集合返回,否则用不了collect(Collectors.toList())
        List<Integer> collect = list.stream().mapToInt(String::length).boxed().collect(Collectors.toList());
        list.stream().mapToInt(String::length).forEach(s -> System.err.println(s));
        System.err.println(collect);
    }

    /**
     * limit和skip的使用(sorted)
     */
    @Test
    public void MeTest2() {
        List<Integer> integers = Arrays.asList(5, 2, 8, 4, 1, 6, 7, 3, 10, 9);
        //limit和skip操作排序前和排序前和排序后的结果是不一样的,limit n取前n个,skip n从n+1个开始取
        String ss = integers.stream().skip(3).collect(Collectors.toList()).toString();
        String ss1 = integers.parallelStream().sorted().skip(3).collect(Collectors.toList()).toString();
        System.err.println("ss:" + ss);//ss:[4, 1, 6, 7, 3, 10, 9]
        System.err.println("ss1:" + ss1);//ss1:[4, 5, 6, 7, 8, 9, 10]
        //-------------------------------------------------------------------
        //异步并行流在排序之后使用limit是很慢的(这里数据小,看不出来)
        String sss = integers.parallelStream().sorted().limit(3).collect(Collectors.toList()).toString();
        String sss1 = integers.stream().limit(3).collect(Collectors.toList()).toString();
        System.err.println("sss:" + sss);//sss:[1, 2, 3]
        System.err.println("sss1:" + sss1);//sss1:[5, 2, 8]
        System.out.println("-------------------------");
        String s = integers.stream().sorted().collect(Collectors.toList()).toString();
        System.err.println(s);//[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

        List<User> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            User person = new User(i + "", "name" + i);
            persons.add(person);
        }
        List<User> personList2 = persons.stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).
                limit(2).peek(a -> System.err.println(a.toString())).collect(Collectors.toList());
        System.out.println(personList2);
    }

    /**
     * reduce(归并)的应用
     */
    @Test
    public void MeTest1() {
        List<Integer> integers = Arrays.asList(5, 2, 8, 4, 1, 6, 7, 3, 10, 9);
        Integer reduce = integers.stream().reduce(0, (a, b) -> a + b);
        System.err.println(reduce);//55
        System.err.println(integers.stream().reduce(Integer::compareTo));//Optional[-1]
        System.err.println(integers.stream().reduce(Integer::sum));//Optional[55]
        //设置了初始值之后,就不会再返回Optional对象了
        System.err.println(integers.stream().reduce(0, Integer::compareTo));//-1
        System.err.println(integers.stream().reduce(1, Integer::sum));//56
        //实测证明,用reduce的第一个参数+x,则最后返回的数是x乘以集合大小(遍历次数)的值,而b则是集合最后的那个值加上x
        //用*x则为0,看来就是通过初始值来进行加减乘除的,也就是a的出事值为0,然后重复进行累加累乘累除的操作
        //现在可以看出来,a(第一个参数)有初始值先赋初始值给它,然后每次运算之后的结果赋值给它,b(第二个参数),
        // 就是挨个往后遍历取数和a(第一个参数)来进行操作
        System.err.println(integers.stream().reduce(0, (a, b) -> a + 20));//200
        System.err.println(integers.stream().reduce(0, (a, b) -> a * 20));//0
        System.err.println(integers.stream().reduce(0, (a, b) -> a / 20));//0
        System.err.println(integers.stream().reduce(0, (a, b) -> a + b + 2));//75
        //显然b(第二个参数)直接计算输出,和初始值和a没有关系
        System.err.println(integers.stream().reduce(2, (a, b) -> b + 2));//12
        System.err.println(integers.stream().reduce(0, (a, b) -> b + 2));//12
    }

}
