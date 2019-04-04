package com.yb.demo;

import com.yb.demo.model.UserInfo;
import com.yb.demo.repository.UserInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoTest {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void contentTest3() {
        List<UserInfo> all = userInfoRepository.findAll((root, query, cb) -> {
            //---------------------------------------------------------------------
            Predicate username = cb.like(root.get("username"), "%j%");
            Predicate username1 = cb.like(root.get("username"), "%o%");
            Predicate or = cb.or(username, username1);
            //通过这样的方式,直接拼接and和or的条件即可,不需要去做(List<Predicate>集合转数组)那些繁复的操作
            return query.where(or).getRestriction();
            //生成的sql语句=...from user_info userinfo0_ where userinfo0_.username like ? or userinfo0_.username like ?
            //---------------------------------------------------------------------
        });
        System.err.println(all);
    }

    @Test
    public void contentTest2() {
        Serializable test = redisTemplate.opsForValue().get("test1");
        System.err.println(test);
        System.err.println((String)test);
    }

    @Test
    public void contentTest1() {
        redisTemplate.opsForValue().set("test1", "哈哈哈啊11111");
        System.err.println("ok");
    }

    @Test
    public void contentTest() {
        //new Thread()这样的方式特别浪费资源,容易造成系统崩溃死机

        //创建一个定长线程池，支持定时及周期性任务执行
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        //创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        //创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
        // 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行,结果依次输出，相当于顺序执行各个任务。
        ExecutorService singleThread = Executors.newSingleThreadExecutor();

        scheduledThreadPool.schedule(() -> {
            //表示延迟3秒执行
            System.err.println("我是scheduledThreadPool线程池");
        }, 3, TimeUnit.SECONDS);

        fixedThreadPool.execute(() -> {
            System.err.println("我是fixedThreadPool线程池");
        });

        cachedThreadPool.execute(() -> {
            System.err.println("我是cachedThreadPool线程池");
        });

        singleThread.execute(() -> {
            System.err.println("我是singleThread线程池");
        });

        //从线程池中获取线程对象,然后调用Runnable实现中的run()
        cachedThreadPool.submit(()->{
            System.err.println("获取第一个线程");
        });
        cachedThreadPool.submit(()->{
            System.err.println("获取第二个线程");
        });
        cachedThreadPool.submit(()->{
            System.err.println("获取第三个线程");
        });
        //注意：submit方法调用结束后，程序并不终止，是因为线程池控制了线程的关闭。将使用完的线程又归还到了线程池中

        //关闭线程池
        cachedThreadPool.shutdown();
    }
}
