package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")

public class IProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    //保存或者更新产品
    @Override

    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                //
                String[] subImageArray = product.getSubImages().split(",");

                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }

            }

            if (product.getId() != null) {


                int rowcount = productMapper.updateByPrimaryKey(product);
                if (rowcount > 0) {
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                return ServerResponse.createBySuccess("更新产品失败");


            } else {

                int rowcount = productMapper.updateByPrimaryKey(product);
                if (rowcount > 0) {
                    return ServerResponse.createBySuccess("增加产品成功");
                }

                return ServerResponse.createBySuccess("增加产品失败");

            }

        }

        return ServerResponse.createByErrorMessage("新增或者更新产品参数不正确");
    }


    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEAGL_ARGUMENT.getCode(), ResponseCode.ILLEAGL_ARGUMENT.getDesc());
        }

        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);

        int rowcount = productMapper.updateByPrimaryKeySelective(product);
        if (rowcount > 0) {
            return ServerResponse.createBySuccess("修改产品销售状态成功");

        }

        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

@Override
    public ServerResponse<Object> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEAGL_ARGUMENT.getCode(), ResponseCode.ILLEAGL_ARGUMENT.getDesc());

        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已经下架或者删除");
        }

        //vo对象--value object
        ProductDetailVo productDetailVo = assembaleProductDetailVo(product);

        return ServerResponse.createBySuccess(productDetailVo);




    }

@Autowired
private CategoryMapper categoryMapper;
    private ProductDetailVo assembaleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;


    }


}
