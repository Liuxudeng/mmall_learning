//package com.mmall.service.impl;
//
//import com.google.common.base.Splitter;
//import com.google.common.collect.Lists;
//import com.mmall.common.Const;
//import com.mmall.common.ResponseCode;
//import com.mmall.common.ServerResponse;
//import com.mmall.dao.CartMapper;
//import com.mmall.dao.ProductMapper;
//import com.mmall.pojo.Cart;
//import com.mmall.pojo.Product;
//import com.mmall.service.ICartService;
//import com.mmall.util.BigDecimalUtil;
//import com.mmall.util.PropertiesUtil;
//import com.mmall.vo.CartProductVo;
//import com.mmall.vo.Cartvo;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Spliterator;
//
//@Service("iCartService")
//
//
//public class ICartServiceImpl implements ICartService {
//    //注入cartMapper
//    @Autowired
//    private CartMapper cartMapper;
//    @Autowired
//    private ProductMapper productMapper;
//
//
//
//
//
//
//
//
//
//
//
//
//
//@Override
//    //add方法
//    public ServerResponse<Cartvo> add(Integer userId, Integer productId, Integer count) {
//
//        Cartvo cartvo = new Cartvo();
//        if(productId == null || count == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEAGAL_ARGUMENT.getCode(),ResponseCode.ILLEAGAL_ARGUMENT.getDesc());
//        }
//        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
//
//
//        if (cart == null) {
//            //这个产品不在购物车里，需要增加一个这个产品记录
//            Cart cartItem = new Cart();
//            cartItem.setQuantity(count);
//            cartItem.setChecked(Const.Cart.CHECKED);
//
//            cartItem.setProductId(productId);
//            cartItem.setUserId(userId);
//
//            cartMapper.insert(cartItem);
//        } else {
//            //如果产品已经在购物车里了
//            //如果商品存在，则数量直接相加
//            count = cart.getQuantity() + count;
//            cart.setQuantity(count);
//            cartMapper.updateByPrimaryKeySelective(cart);
//        }
//
//
//        return this.list(userId);
//
//    }
//
//
//@Override
//    //update方法
//    public ServerResponse<Cartvo> update(Integer userId,Integer productId,Integer count){
//        if(productId == null || count == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEAGAL_ARGUMENT.getCode(),ResponseCode.ILLEAGAL_ARGUMENT.getDesc());
//        }
//
//        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
//
//
//        if(cart!=null){
//            cart.setQuantity(count);
//        }
//        cartMapper.updateByPrimaryKeySelective(cart);
//
//        Cartvo cartvo = this.getCartVoLimit(userId);
//
//
//        return ServerResponse.createBySuccess(cartvo);
//    }
//
//    //查询方法
//
//    public ServerResponse<Cartvo> list (Integer userId){
//        Cartvo cartVo = this.getCartVoLimit(userId);
//        return ServerResponse.createBySuccess(cartVo);
//    }
//
//
//@Override
// //删除产品方法
//    public ServerResponse<Cartvo> deleteProduct(Integer userId,String productIds){
//    List<String> productList = Splitter.on(",").splitToList(productIds);
//    if(CollectionUtils.isEmpty(productList)){
//        return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEAGAL_ARGUMENT.getCode(),ResponseCode.ILLEAGAL_ARGUMENT.getDesc());
//    }
//
//    cartMapper.deleteByUserIdProductIds(userId,productList);
//    Cartvo cartVo = this.getCartVoLimit(userId);
//    return ServerResponse.createBySuccess(cartVo);
//
//
//
//    }
//
//
//
//
//
//
//
//
//
//
//    //封装一个private方法
//
////    private Cartvo getCartVoLimit(Integer userId) {
////        Cartvo cartvo = new Cartvo();
////        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
////
////
////        List<CartProductVo> cartProductVoList = Lists.newArrayList();
////
////        BigDecimal cartTotalPrice = new BigDecimal("0");
////
////        if (CollectionUtils.isNotEmpty(cartList)) {
////            for (Cart cart : cartList) {
////                CartProductVo cartProductVo = new CartProductVo();
////                cartProductVo.setId(cart.getId());
////                cartProductVo.setUserId(userId);
////                cartProductVo.setProductId(cart.getProductId());
////
////
////                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
////
////                if (product != null) {
////                    cartProductVo.setProductMainImage(product.getMainImage());
////
////
////                    cartProductVo.setProductName(product.getName());
////
////                    cartProductVo.setProductSubtitle(product.getSubtitle());
////                    cartProductVo.setProductStatus(product.getStatus());
////                    cartProductVo.setProductPrice(product.getPrice());
////                    //设置库存
////                    cartProductVo.setProductStock(product.getStock());
////                    //判断库存
////                    int buyLimitCount = 0;
////                    if (product.getStock() >= cart.getQuantity()) {
////                        //库存充足的时候
////                        buyLimitCount = cart.getQuantity();
////                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
////                    } else {
////                        buyLimitCount = product.getStock();
////                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
////                        //购物车中更新有效库存
////                        Cart cartForQuantity = new Cart();
////                        cartForQuantity.setId(cart.getId());
////                        cartForQuantity.setQuantity(buyLimitCount);
////                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
////                    }
////
////                    cartProductVo.setQuantity(buyLimitCount);
////                    //计算总价
////                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
////
////                    cartProductVo.setProductChecked(cart.getChecked());
////                }
////
////                if (cart.getChecked() == Const.Cart.CHECKED) {
////                    //如果已经勾选,增加到整个的购物车总价中
////
////                    // cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
////                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
////
////                    //cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), 0);
////                }
////                cartProductVoList.add(cartProductVo);
////
////            }
////
////        }
////
////        cartvo.setCartTotalPrice(cartTotalPrice);
////        cartvo.setCartProductVoList(cartProductVoList);
////        cartvo.setAllChecked(this.getAllCheckedStatus(userId));
////        cartvo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
////
////        return cartvo;
////
////    }
//
//    private Cartvo getCartVoLimit(Integer userId){
//        Cartvo cartVo = new Cartvo();
//        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
//        List<CartProductVo> cartProductVoList = Lists.newArrayList();
//
//        BigDecimal cartTotalPrice = new BigDecimal("0");
//
//        if(CollectionUtils.isNotEmpty(cartList)){
//            for(Cart cartItem : cartList){
//                CartProductVo cartProductVo = new CartProductVo();
//                cartProductVo.setId(cartItem.getId());
//                cartProductVo.setUserId(userId);
//                cartProductVo.setProductId(cartItem.getProductId());
//
//                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
//                if(product != null){
//                    cartProductVo.setProductMainImage(product.getMainImage());
//                    cartProductVo.setProductName(product.getName());
//                    cartProductVo.setProductSubtitle(product.getSubtitle());
//                    cartProductVo.setProductStatus(product.getStatus());
//                    cartProductVo.setProductPrice(product.getPrice());
//                    cartProductVo.setProductStock(product.getStock());
//                    //判断库存
//                    int buyLimitCount = 0;
//                    if(product.getStock() >= cartItem.getQuantity()){
//                        //库存充足的时候
//                        buyLimitCount = cartItem.getQuantity();
//                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
//                    }else{
//                        buyLimitCount = product.getStock();
//                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
//                        //购物车中更新有效库存
//                        Cart cartForQuantity = new Cart();
//                        cartForQuantity.setId(cartItem.getId());
//                        cartForQuantity.setQuantity(buyLimitCount);
//                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
//                    }
//                    cartProductVo.setQuantity(buyLimitCount);
//                    //计算总价
//                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
//                    cartProductVo.setProductChecked(cartItem.getChecked());
//                }
//
//                if(cartItem.getChecked() == Const.Cart.CHECKED){
//                    //如果已经勾选,增加到整个的购物车总价中
//                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
//                }
//                cartProductVoList.add(cartProductVo);
//            }
//        }
//        cartVo.setCartTotalPrice(cartTotalPrice);
//        cartVo.setCartProductVoList(cartProductVoList);
//        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
//        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
//
//        return cartVo;
//    }
//
//
//    private boolean getAllCheckedStatus(Integer userId) {
//        if (userId == null) {
//            return false;
//        }
//        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
//    }
//
//
//
//    //全选
//    //全反选
//
//@Override
//    public ServerResponse<Cartvo> selectOrUnSelect (Integer userId,Integer productId,Integer checked){
//        cartMapper.checkedOrUncheckedProduct(userId,productId,checked);
//        return this.list(userId);
//    }
//@Override
//public ServerResponse<Integer> getCartProductCount(Integer userId){
//        if(userId == null){
//            return ServerResponse.createBySuccess(0);
//        }
//        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
//    }
//
//
//    //单独选
//    //单独反选
//
//
//
//
//
//
//
//}

package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.Cartvo;
import com.mmall.vo.Cartvo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service("iCartService")
public class ICartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
@Override
    public ServerResponse<Cartvo> add(Integer userId,Integer productId,Integer count){
        if(productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }


        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if(cart == null){
            //这个产品不在这个购物车里,需要新增一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        }else{
            //这个产品已经在购物车里了.
            //如果产品已存在,数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        //
   // Cartvo cartvo = this.getCartVoLimit(userId);
     //   return ServerResponse.createBySuccess(cartvo);

        return this.list(userId);
    }
@Override
    public ServerResponse<Cartvo> update(Integer userId,Integer productId,Integer count){
        if(productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if(cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKey(cart);
        return this.list(userId);
    }
@Override
    public ServerResponse<Cartvo> deleteProduct(Integer userId,String productIds){
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId,productList);
        return this.list(userId);
    }

@Override
    public ServerResponse<Cartvo> list (Integer userId){
        Cartvo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

@Override

    public ServerResponse<Cartvo> selectOrUnSelect (Integer userId,Integer productId,Integer checked){
        cartMapper.checkedOrUncheckedProduct(userId,productId,checked);
        return this.list(userId);
    }
@Override
    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }















    private Cartvo getCartVoLimit(Integer userId){
        Cartvo cartVo = new Cartvo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if(CollectionUtils.isNotEmpty(cartList)){
            for(Cart cartItem : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cartItem.getProductId());
                System.out.println("ProductId,"+ cartItem.getProductId());
                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if(product != null){
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if(product.getStock() >= cartItem.getQuantity()){
                        //库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else{
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }

                if(cartItem.getChecked() == Const.Cart.CHECKED){
                    //如果已经勾选,增加到整个的购物车总价中
              //      System.out.println(cartTotalPrice.doubleValue());
               //     System.out.println(cartProductVo.getProductTotalPrice().doubleValue());
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                   // cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),0);

                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return cartVo;
    }

    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;

    }


























}


