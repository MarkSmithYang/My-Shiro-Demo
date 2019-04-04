package com.example.demo;

import com.example.demo.common.Result;
import com.example.demo.controller.UserInfoController;
import com.example.demo.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.annotation.Validated;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoTest {

    @Autowired
    private UserInfoController userInfoController;

    @Test
    public void findByUsername(){
        Result<UserInfo> jack = userInfoController.findByUsername("rose");
        System.err.println(jack.getData()!=null?jack.getData():null);
        System.err.println(jack.toString());
    }
}
