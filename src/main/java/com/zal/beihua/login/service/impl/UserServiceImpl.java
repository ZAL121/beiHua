package com.zal.beihua.login.service.impl;


import com.zal.beihua.entity.User;
import com.zal.beihua.login.service.Exception.UserNotExistsException;
import com.zal.beihua.login.service.Exception.UsernameIsEmpty;
import com.zal.beihua.login.service.Exception.UserpasswordError;
import com.zal.beihua.login.service.IUserService;
import com.zal.beihua.utils.Md5Util;

public class UserServiceImpl implements IUserService {

   // IUserDao dao = new UserDaoImpl();


    //登录
    @Override
    public User login(User user) throws Exception, UsernameIsEmpty, UserNotExistsException, UserpasswordError {
        if (user.getUsername() == null || user.getUsername().trim().equalsIgnoreCase("")) {
            throw new UsernameIsEmpty("用户名不能为空!");
        }
        //loginUser是从数据库中获取的用户
//        User loginUser = dao.findLoginUser(user);
        User loginUser = new User();
        if (loginUser == null) {
            //用户未激活或者不存在
            throw new UserNotExistsException("用户未激活或不存在!");
        } else {
            //用户存在，校验密码
            if (loginUser.getPassword().equalsIgnoreCase(Md5Util.encodeByMd5(user.getPassword()))) {
                //密码正确，登录成功
            } else {
                //密码错误
                throw new UserpasswordError("密码错误!");
            }

        }
        return loginUser;
    }

/*    //邮件激活
    @Override
    public boolean active(String code) {
        User activeUser = dao.active(code);
        if (activeUser != null) {
            //修改用户数据，进行激活
            int result = dao.edit(activeUser);
            if (result > 0) {
                //激活成功
                return true;
            } else {
                //激活失败
                return false;
            }
        } else {
            //用户已经激活，返回false
            return false;
        }
    }


    //注册
    @Override
    public boolean register(User user) {

        int result = dao.register(user);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }


    //查重用户名
    @Override
    public String findUsername(String username) {
        User user = dao.findUsername(username);
        if (user == null) {
            return "yes";
        } else {
            return "no";
        }
    }*/


}
