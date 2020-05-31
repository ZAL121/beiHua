package com.zal.beihua.favorite.service;

import com.zal.beihua.entity.User;

public interface FavoriteService {

    String myFavoriteShow(User loginUser, int pageNumber);

    String favoriterank(int pageNumber);
}
