package com.zal.beihua.show.service;


import com.zal.beihua.entity.User;

import java.sql.SQLException;

public interface FlowerShowService {
    String findAllCategory();

    String findAllRouteByTJForPage(int cid, int pageNumber, String rname);

    String findRouteDetailByRid(String rid);

    String addMyFavorite(String rid, User loginUser) throws SQLException;

    String isAddMyFavorite(String rid, User loginUser);
}
