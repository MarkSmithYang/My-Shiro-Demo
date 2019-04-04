package com.example.demo.repository;

import com.example.demo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,String> {

    UserInfo findByUsername(String username);
}
