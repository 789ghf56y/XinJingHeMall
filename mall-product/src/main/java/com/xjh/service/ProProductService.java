package com.xjh.service;

import com.xjh.pojo.ProProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xjh.pojo.query.ProductQuery;
import com.xjh.pojo.vo.ProductVo;

import java.util.List;

public interface ProProductService extends IService<ProProduct>{


    List<ProductVo> getProductList(ProductQuery productQuery);

    void saveProduct(ProductVo productVo);

    void updateProduct(ProductVo productVo);

    List<ProductVo> getProductsByClassifyId(String id);
}
