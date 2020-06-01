package com.zal.beihua.show.service.impl;

import com.google.gson.Gson;
import com.zal.beihua.entity.*;
import com.zal.beihua.show.mapper.FlowerShowMapper;
import com.zal.beihua.show.service.FlowerShowService;
import com.zal.beihua.utils.DRUIDUtils;
import com.zal.beihua.utils.PageBean;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service
public class FlowerShowServiceImpl implements FlowerShowService {

    @Autowired
    private FlowerShowMapper flowerShowMapper;

    /**
     * @Description: 添加收藏
     * @Author ZAL
     * @Date 2020/5/31 21:30
     * @Version V1.0
     */
    @Override
    public String addMyFavorite(String rid, User loginUser) throws SQLException {
        ResultInfo resultInfo = null;
        if (loginUser == null) {
            //未登录
            resultInfo = new ResultInfo(false);
        } else {
            //已登录
            //判断是否收藏
            boolean isAdded = flowerShowMapper.isAddedMyFavorite(rid, loginUser) > 0;
            if (isAdded) {
                //已经收藏（取消收藏）

                try {
                    //删除收藏的操作
                    flowerShowMapper.deleteMyFavorite(rid, loginUser);
                    //计数减1的操作
                    flowerShowMapper.editDownRouteCount(rid);
                    resultInfo = new ResultInfo(true, true, null);
                } catch (Exception e) {
                    //失败
                    resultInfo = new ResultInfo(true, false, e.getMessage());
                }
            } else {
                //未收藏(后期增加事务操作)
                try {
                    //添加收藏的操作
                    flowerShowMapper.addMyFavorite(rid, loginUser);
                    //计数加1的操作
                    flowerShowMapper.editRouteCount(rid);
                    resultInfo = new ResultInfo(true, true, null);
                } catch (Exception e) {
                    //失败
                    resultInfo = new ResultInfo(true, false, e.getMessage());
                }
/*                if (isAddSucceed){
                    //收藏成功
                    resultInfo=new ResultInfo(true,true,null);
                }else{
                    //收藏失败
                    resultInfo=new ResultInfo(true,false,"收藏失败!");
                }*/
            }
        }
        String json = new Gson().toJson(resultInfo);
        return json;
    }

    //页面加载判断是否收藏
    @Override
    public String isAddMyFavorite(String rid, User loginUser) {
        ResultInfo resultInfo = null;
        if (loginUser != null) {
            //已经登录
            boolean addedMyFavorite = flowerShowMapper.isAddedMyFavorite(rid, loginUser) > 0;
            if (addedMyFavorite) {
                //已经收藏
                resultInfo = new ResultInfo(true, true, null);
            } else {
                resultInfo = new ResultInfo(true, false, null);
            }
        } else {
            //未登录
            resultInfo = new ResultInfo(false);
        }
        String json = new Gson().toJson(resultInfo);
        return json;
    }

    //根据rid查询花的详情信息
    @Override
    public String findRouteDetailByRid(String rid) {
        Map<String, Object> map = flowerShowMapper.findRouteInfoByRid(rid);
        Route route = new Route();
        Category category = new Category();
        Seller seller = new Seller();
        try {
            BeanUtils.populate(route, map);
            BeanUtils.populate(category, map);
            BeanUtils.populate(seller, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        route.setCategory(category);
        route.setSeller(seller);
        List<RouteImg> list = flowerShowMapper.findRouteDetailImgByRid(rid);
        route.setRouteImgList(list);
        return new Gson().toJson(route);

    }

    //根据cid查找所有花
    @Override
    public String findAllRouteByTJForPage(int cid, int pageNumber, String rname) {
        int pageSize = 8;
        //根据cid获取路线信息总数
        int total = flowerShowMapper.findTotalByCidRname(cid, rname);
        PageBean<Route> pageBean = new PageBean<>(pageNumber, pageSize, total);
        List<Route> routeList = flowerShowMapper.findAllRouteByTJForpage(cid, pageBean, rname);
        pageBean.setRows(routeList);
        return new Gson().toJson(pageBean);
    }

    //获取所有分类信息
    @Override
    public String findAllCategory() {
        List<Category> categoryList = flowerShowMapper.findAllCategory();
        return new Gson().toJson(categoryList);
    }
}
