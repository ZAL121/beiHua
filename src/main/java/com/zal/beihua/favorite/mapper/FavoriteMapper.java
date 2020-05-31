package com.zal.beihua.favorite.mapper;
import com.zal.beihua.entity.Route;
import com.zal.beihua.entity.User;
import com.zal.beihua.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface FavoriteMapper {
    List<Map<String,Object>> myFavoriteShow(User loginUser, PageBean pageBean);

    int totalOfMyFavorite(User loginUser);

    int totalOfFavorite();

    List<Route> findFavorite(PageBean<Route> pageBean);

}
