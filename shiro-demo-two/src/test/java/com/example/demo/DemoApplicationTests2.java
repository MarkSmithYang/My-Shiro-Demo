package com.example.demo;

import com.example.demo.other.UserDao;
import com.example.demo.other.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.EntityManager;
import java.time.*;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests2 {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserDao userDao;

    /**
     * Date和LocalDate和LocalDateTime----->简洁版
     * 通过Instant和ZoneId.systemDefault()来作为中间量来进行转换
     * 注意LocalDate的atStartOfDay()方法
     * LocalDate localDate1 = localDateTime.toLocalDate();0
     * LocalDateTime localDateTime1 = now.atStartOfDay();
     * Instant instant1 = now.atStartOfDay(z).toInstant();
     */
    @Test
    public void MyTest2(){
        //Date转换为LocalDate和LocalDateTime
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        //LocalDate和LocalDateTime转换为Date
        Date fromDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fromTime = Date.from(localDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date和LocalDate和LocalDateTime----->繁复版
     */
    @Test
    public void MyTest1(){
        //Date转换为LocalDate和LocalDateTime
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        LocalDate localDate = zonedDateTime.toLocalDate();
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        System.err.println(date+"---1");
        System.err.println(instant+"---2");
        System.err.println(zoneId+"---3");
        System.err.println(localDate+"---4");
        //
        //LocalDate和LocalDateTime转换为Date
        LocalDate now = LocalDate.now();
        ZoneId z = ZoneId.systemDefault();
        LocalDateTime localDateTime1 = now.atStartOfDay();
        Instant instant1 = now.atStartOfDay(z).toInstant();
        Date from = Date.from(instant1);
        System.err.println(localDateTime1);
        System.err.println(from);
    }

    /**
     * java8时间类的应用
     */
    @Test
    public void MyTest(){
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        //
        LocalDate localDate = zonedDateTime.toLocalDate();
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        LocalTime localTime = zonedDateTime.toLocalTime();
        OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();
        //
        System.err.println("localDate::"+localDate);
        System.err.println("localDateTime::"+localDateTime);
        System.err.println("localTime::"+localTime);
        System.err.println("offsetDateTime::"+offsetDateTime);
        //
        System.err.println(zoneId);
        System.err.println(now);
        System.err.println(zonedDateTime);
        //
        Clock system = Clock.system(zoneId);
        Instant instant = system.instant();
        ZoneId zone = system.getZone();
        long millis = system.millis();
        System.err.println(millis);
        //
        Clock clock = system.withZone(ZoneId.of("Asia/Shanghai"));
        System.err.println(clock.getZone());
        System.err.println("---------------------");
        System.err.println(instant);
        System.err.println(zone);
    }

}
