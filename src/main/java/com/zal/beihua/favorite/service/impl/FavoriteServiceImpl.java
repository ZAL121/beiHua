package com.zal.beihua.favorite.service.impl;
import com.google.gson.Gson;
import com.zal.beihua.entity.ResultInfo;
import com.zal.beihua.entity.Route;
import com.zal.beihua.entity.User;
import com.zal.beihua.favorite.mapper.FavoriteMapper;
import com.zal.beihua.favorite.service.FavoriteService;
import com.zal.beihua.utils.PageBean;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;


    //收藏排行
    @Override
    public String favoriterank(int pageNumber) {
        int pageSize = 8;
        int total = favoriteMapper.totalOfFavorite();
        PageBean<Route> pageBean = new PageBean<>(pageNumber, pageSize, total);
        List<Route> list = favoriteMapper.findFavorite(pageBean);
        pageBean.setRows(list);
        return new Gson().toJson(pageBean);
    }

    //我的收藏
    @Override
    public String myFavoriteShow(User loginUser, int pageNumber) {
        ResultInfo resultInfo = new ResultInfo();

        List<Route> routeList = new ArrayList<>();
        //先判断是否登录
        if (loginUser != null) {
            //已登录

            int pageSize = 12;
            int total = favoriteMapper.totalOfMyFavorite(loginUser);
            PageBean<Route> pageBean = new PageBean<>(pageNumber, pageSize, total);

            List<Map<String, Object>> mapList = favoriteMapper.myFavoriteShow(loginUser, pageBean);
            for (Map<String, Object> map : mapList) {
                Route route = new Route();
                try {
                    BeanUtils.populate(route, map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                routeList.add(route);
            }

            pageBean.setRows(routeList);
            resultInfo = new ResultInfo(true, pageBean, null);
        } else {
            //未登录
            resultInfo = new ResultInfo(false);

        }
        return new Gson().toJson(resultInfo);
    }


}
