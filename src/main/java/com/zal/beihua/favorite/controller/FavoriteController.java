package com.zal.beihua.favorite.controller;

import com.zal.beihua.entity.User;
import com.zal.beihua.favorite.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/FavoriteController")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    /**
     * @Description 收藏排行榜
     * @Author ZAL
     * @Date 2020/5/31 21:10
     * @Parameter request response
     */
    @RequestMapping("/favoriterank")
    public void favoriterank(HttpServletRequest request, HttpServletResponse response) throws Exception,
            IOException {
        String pageNumberStr = request.getParameter("pageNumber");
        int pageNumber;
        if (pageNumberStr != null && !pageNumberStr.trim().equals("") && !pageNumberStr.equals("null")) {
            pageNumber = Integer.parseInt(pageNumberStr);
        } else {
            pageNumber = 1;
        }
        String json = favoriteService.favoriterank(pageNumber);
        response.getWriter().write(json);
    }


    /**
     * @Description 我的收藏展示
     * @Author ZAL
     * @Date 2020/5/31 21:11
     * @Parameter request response
     * @Return
     * @Exception ServletException IOException
     */
    @RequestMapping("/myFavoriteShow")
    public void myFavoriteShow(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        String pageNumberStr = request.getParameter("pageNumber");
        int pageNumber;
        if (pageNumberStr != null && !pageNumberStr.trim().equals("") && !pageNumberStr.equals("null")) {
            pageNumber = Integer.parseInt(pageNumberStr);
        } else {
            pageNumber = 1;
        }
        String json = favoriteService.myFavoriteShow(loginUser, pageNumber);
        response.getWriter().write(json);
    }
}