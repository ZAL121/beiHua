package com.zal.beihua.login.controller;

import com.google.gson.Gson;
import com.zal.beihua.entity.ResultInfo;

import com.zal.beihua.login.service.Exception.UserNotExistsException;
import com.zal.beihua.login.service.Exception.UsernameIsEmpty;
import com.zal.beihua.login.service.Exception.UserpasswordError;
import com.zal.beihua.login.service.UserService;
import com.zal.beihua.utils.MailUtil;
import com.zal.beihua.utils.Md5Util;
import com.zal.beihua.utils.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.zal.beihua.entity.*;

@Controller
@RequestMapping("/UserController")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * @Description 登录退出
     * @Author ZAL
     * @Date 2020/5/30 21:35
     * @Parameter
     * @Return
     * @Exception
     * @Version V1.0
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
    }

    //验证是否是登录状态
    @RequestMapping("/getUserData")
    public void getUserData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultInfo resultInfo = null;
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            //未登录
            resultInfo = new ResultInfo(false);
        } else {
            //已登录
            resultInfo = new ResultInfo(true, loginUser, null);
        }
        String json = new Gson().toJson(resultInfo);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    /**
     * @Description: 登录
     * @Author X.Li
     * @Date 2020年6月1日01:39:05
     * @Version V1.0
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        ResultInfo resultInfo = null;
        try {
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            BeanUtils.populate(user, map);
            User loginUser = userService.login(user);
            //未抛出异常，代表登录成功
            resultInfo = new ResultInfo(true);
            //将用户存入session域中，判断是否显示登录注册按钮时使用
            request.getSession().setAttribute("loginUser", loginUser);
        } catch (IllegalAccessException e) {
            //BeanUtils的错误
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            //BeanUtils的错误
            e.printStackTrace();
        } catch (Exception e) {
            //MD5的错误
            e.printStackTrace();
        } catch (UserNotExistsException e) {
            //用户不存在或未激活的错误
            e.printStackTrace();
            resultInfo = new ResultInfo(false, e.getMessage());
        } catch (UserpasswordError e) {
            //用户密码错误
            e.printStackTrace();
            resultInfo = new ResultInfo(false, e.getMessage());
        } catch (UsernameIsEmpty e) {
            //用户名输入为空的错误
            e.printStackTrace();
            resultInfo = new ResultInfo(false, e.getMessage());
        }

        String json = new Gson().toJson(resultInfo);
        response.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }

    /**
     * @Description 邮箱激活
     * @Author ZAL
     * @Date 2020/6/1 1:38
     * @Param
     * @Return
     * @Exception
     */
    @RequestMapping("/active")
    public void active(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        boolean isActive = userService.active(code);
        if (isActive) {
            response.getWriter().write("激活成功!");
        } else {
            response.getWriter().write("激活失败，可能您已激活！");
        }

    }


    /**
     * @Description 注册
     * @Author ZAL
     * @Date 2020/6/1 1:36
     * @Param
     * @Return
     * @Exception
     */
    @RequestMapping("register")
    public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        try {
            user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean isRegisterSucceed = userService.register(user);

        if (isRegisterSucceed) {
            try {
                MailUtil.sendMail(user.getEmail(), user.getCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("register_ok.html");
        } else {
            response.getWriter().write("服务器忙!");
        }
    }

    /**
     * 验证码校验
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws Exception,
            IOException {
        String trueCheckCode = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        String inputCheckCode = request.getParameter("inputCheckCode");
        if ((trueCheckCode == null || inputCheckCode == null) || (!trueCheckCode.equalsIgnoreCase(inputCheckCode))) {
            response.getWriter().write("no");
        } else {
            response.getWriter().write("yes");
        }

    }

    /**
     * @Description 判断用户名是否存在
     * @Author ZAL
     * @Date 2020/6/1 1:26
     * @Param
     * @Return
     * @Exception
     */
    @RequestMapping("/findUsername")
    public void findUsername(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String resultMsg = userService.findUsername(username);
        response.getWriter().write(resultMsg);
    }
}