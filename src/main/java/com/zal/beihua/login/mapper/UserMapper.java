package com.zal.beihua.login.mapper;

import com.zal.beihua.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findUsername(String username);

    int register(@Param("user") User user);

    User active(String code);

    int edit(@Param("uuid") String uuid,@Param("activeUser") User activeUser);

    User findLoginUser(User user);

}
