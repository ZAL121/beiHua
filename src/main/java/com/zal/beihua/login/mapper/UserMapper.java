package com.zal.beihua.login.mapper;

import com.zal.beihua.entity.User;

public interface UserMapper {
//    User findUsername(String username);
//
//    int register(User user);
//
//    User active(String code);
//
//    int edit(User activeUser);

    User findLoginUser(User user);

}
