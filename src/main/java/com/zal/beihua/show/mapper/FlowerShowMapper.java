package com.zal.beihua.show.mapper;

import com.zal.beihua.entity.Category;
import com.zal.beihua.entity.Route;
import com.zal.beihua.entity.RouteImg;
import com.zal.beihua.entity.User;
import com.zal.beihua.utils.PageBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public interface FlowerShowMapper {
    List<Category> findAllCategory();

    List<Route> findAllRouteByTJForpage(@Param("cid") int cid, @Param("pageBean") PageBean pageBean, @Param("rname") String rname);

    int findTotalByCidRname(@Param("cid") int cid, @Param("rname") String rname);

    Map<String, Object> findRouteInfoByRid(String rid);

    List<RouteImg> findRouteDetailImgByRid(String rid);

    int isAddedMyFavorite(@Param("rid") String rid, @Param("loginUser") User loginUser);

    void addMyFavorite(@Param("rid") String rid, @Param("loginUser") User loginUser);

    void editRouteCount(String rid);

    void deleteMyFavorite(@Param("rid") String rid, @Param("loginUser") User loginUser);

    void editDownRouteCount(String rid);

}
