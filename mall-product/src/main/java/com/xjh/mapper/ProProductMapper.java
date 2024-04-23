package com.xjh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjh.pojo.ProProduct;
import com.xjh.pojo.dto.ProductDto;
import com.xjh.pojo.query.ProductQuery;

import java.util.List;

public interface ProProductMapper extends BaseMapper<ProProduct> {
    List<ProductDto> getProductList(ProductQuery productQuery);

    List<ProProduct> getProductsByClassifyId(String id);
}