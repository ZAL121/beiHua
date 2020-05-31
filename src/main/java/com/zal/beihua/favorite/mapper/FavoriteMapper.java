package com.zal.beihua.favorite.mapper;

import com.zal.beihua.entity.Route;
import com.zal.beihua.entity.User;
import com.zal.beihua.utils.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FavoriteMapper {

    List<Map<String, Object>> myFavoriteShow(@Param("loginUser") User loginUser, @Param("pageBean") PageBean pageBean);

    int totalOfMyFavorite(@Param("loginUser") User loginUser);

    int totalOfFavorite();

    List<Route> findFavorite(@Param("pageBean") PageBean<Route> pageBean);

}
