package com.mmall.controller.backend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台的类
 */

@Controller
@RequestMapping("/product/")
public class ProductController {

    //注入类
    @Autowired
    private IProductService iProductService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){

        return iProductService.getProductDetail(productId);

    }


    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keword,
                                         @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                         @RequestParam(value ="pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue= "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue= "") String orderBy

                                         ){

return iProductService.getProductByKeywordCategory(keword,categoryId,pageNum,pageSize,orderBy);


    }



}
