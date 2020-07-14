package com.zal.beihua.show.controller;

import com.zal.beihua.entity.User;
import com.zal.beihua.show.service.FlowerShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

@Controller
@RequestMapping("/FlowerShowController")
public class FlowerShowController {

    @Autowired
    private FlowerShowService service;

    /**
     * @Description: 添加收藏（先判断用户是否登录，如果登录，再判断是否收藏，如果没有收藏，进行收藏操作，如果收藏了，进行取消收藏的操作）
     * @Author ZAL
     * @Date 2020/5/31 21:01
     * @Version V1.0
     */
    @RequestMapping("/addMyFavorite")
    public void addMyFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rid = request.getParameter("rid");
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        String json = service.addMyFavorite(rid, loginUser);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    /**
     * @Description 页面加载判断是否收藏
     * @Author ZAL
     * @Date 2020/5/31 21:04
     * @Param
     * @Return
     * @Exception
     */
    @RequestMapping("/isAddMyFavorite")
    public void isAddMyFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception,
            IOException {
        String rid = request.getParameter("rid");
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        String json = service.isAddMyFavorite(rid, loginUser);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    /**
     * @Description: 根据cid获取详情信息
     * @Author ZAL
     * @Date 2020/5/31 21:04
     * @Version V1.0
     */
    @RequestMapping("/findRouteDetailByRid")
    public void findRouteDetailByRid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rid = request.getParameter("rid");
        String json = service.findRouteDetailByRid(rid);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }


    /**
     * @Description: 根据条件获取路线信息并分页
     * @Author ZAL
     * @Date 2020/5/31 21:05
     * @Version V1.0
     */
    @RequestMapping("/findAllRouteByTJForPage")
    public void findAllRouteByTJForPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int cid = 0;
        String cidStr = request.getParameter("cid");
        if (cidStr != null && !cidStr.equals("null") && !cidStr.trim().equals("")) {
            cid = Integer.parseInt(cidStr);
        }
        String pageNumberStr = request.getParameter("pageNumber");
        String inputInfo = request.getParameter("rname");
        String rname = URLDecoder.decode(inputInfo, "utf-8");
        int pageNumber = 1;
        if(!"".equals(pageNumberStr)) {
             pageNumber = Integer.parseInt(pageNumberStr);
        }
        String json = service.findAllRouteByTJForPage(cid, pageNumber, rname);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    /**
     * @Description: 获取所有分类信息
     * @Author ZAL
     * @Date 2020/5/31 21:05
     * @Version V1.0
     */
    @RequestMapping("/findAllCategory")
    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String json = service.findAllCategory();
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }


}