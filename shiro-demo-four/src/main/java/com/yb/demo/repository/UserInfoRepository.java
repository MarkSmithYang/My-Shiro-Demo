package com.yb.demo.repository;

import com.yb.demo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,String>, JpaSpecificationExecutor<UserInfo> {

    UserInfo findByUsername(String username);
}
