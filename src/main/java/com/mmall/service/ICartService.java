package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.Cartvo;

public interface ICartService {

    ServerResponse<Cartvo> add(Integer userId, Integer productId, Integer count);
    ServerResponse<Cartvo> update(Integer userId,Integer productId,Integer count);
    ServerResponse<Cartvo> deleteProduct(Integer userId,String productIds);
    ServerResponse<Cartvo> selectOrUnSelect (Integer userId,Integer productId,Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
    ServerResponse<Cartvo> list (Integer userId);
}
