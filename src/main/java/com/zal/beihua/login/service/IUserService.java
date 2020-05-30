package com.zal.beihua.login.service;


import com.zal.beihua.entity.User;
import com.zal.beihua.login.service.Exception.UserNotExistsException;
import com.zal.beihua.login.service.Exception.UsernameIsEmpty;
import com.zal.beihua.login.service.Exception.UserpasswordError;

public interface IUserService {
//    String findUsername(String username);
//
//    boolean register(User user);
//
//    boolean active(String code);

    User login(User user) throws Exception, UsernameIsEmpty, UserNotExistsException, UserpasswordError;
}
