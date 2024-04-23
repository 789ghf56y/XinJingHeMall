package com.xjh.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xjh.mapper.ProProductClassifyMapper;
import com.xjh.mapper.ProProductProductClassifyMapper;
import com.xjh.pojo.ProProductClassify;
import com.xjh.pojo.ProProductProductClassify;
import com.xjh.pojo.dto.ProductDto;
import com.xjh.pojo.query.ProductQuery;
import com.xjh.pojo.vo.ProductVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjh.mapper.ProProductMapper;
import com.xjh.pojo.ProProduct;
import com.xjh.service.ProProductService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProProductServiceImpl extends ServiceImpl<ProProductMapper, ProProduct> implements ProProductService{

    @Resource
    private ProProductMapper productMapper;
    @Resource
    private ProProductClassifyMapper productClassifyMapper;

    @Resource
    private ProProductProductClassifyMapper proProductProductClassifyMapper;

    @Override
    public List<ProductVo> getProductList(ProductQuery productQuery) {
        List<ProductDto> productDtoList = productMapper.getProductList(productQuery);
        Map<String,List<String>> idForTypesMap = new HashMap<>();
        List<ProductVo> productVoList = productDtoList.stream().map(productDto -> {
            if (!idForTypesMap.containsKey(productDto.getId())) {
                idForTypesMap.put(productDto.getId(), new ArrayList<>(Arrays.asList(productDto.getTypeName())));
            }else{
                idForTypesMap.get(productDto.getId()).add(productDto.getTypeName());
            }
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(productDto, productVo);
            return productVo;
        }).collect(Collectors.toList());

        productVoList = productVoList.stream().map(productVo -> {
            if(idForTypesMap.keySet().contains(productVo.getId())){
                productVo.setProductClassify(idForTypesMap.get(productVo.getId()));
            }
            return productVo;
        }).distinct().collect(Collectors.toList());

        return productVoList;
    }

    @Override
    @Transactional
    public void saveProduct(ProductVo productVo) {
        ProProduct product = new ProProduct();
        BeanUtils.copyProperties(productVo,product);
        productMapper.insert(product);
        List<String> classifyNames = productVo.getProductClassify();
        for (String classifyName : classifyNames) {
            LambdaQueryWrapper<ProProductClassify> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(productVo.getProductClassify().size()!=0,ProProductClassify::getTypeName,classifyName);
            ProProductClassify productClassify = productClassifyMapper.selectOne(queryWrapper);
            ProProductProductClassify productProductClassify = new ProProductProductClassify();
            productProductClassify.setProductId(product.getId());
            productProductClassify.setProductClassifyId(productClassify.getId());
            proProductProductClassifyMapper.insert(productProductClassify);
        }
    }

    @Override
    @Transactional
    public void updateProduct(ProductVo productVo) {
        ProProduct product = new ProProduct();
        BeanUtils.copyProperties(productVo,product);
        productMapper.update(product,new LambdaUpdateWrapper<ProProduct>().eq(StringUtils.isNotBlank(productVo.getId()),ProProduct::getId,productVo.getId()));
        proProductProductClassifyMapper.delete(new LambdaQueryWrapper<ProProductProductClassify>().eq(StringUtils.isNotBlank(productVo.getId()),ProProductProductClassify::getProductId,productVo.getId()));
        List<String> classifyNames = productVo.getProductClassify();
        for (String classifyName : classifyNames) {
            LambdaQueryWrapper<ProProductClassify> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(productVo.getProductClassify().size()!=0,ProProductClassify::getTypeName,classifyName);
            ProProductClassify productClassify = productClassifyMapper.selectOne(queryWrapper);
            ProProductProductClassify productProductClassify = new ProProductProductClassify();
            productProductClassify.setProductId(product.getId());
            productProductClassify.setProductClassifyId(productClassify.getId());
            proProductProductClassifyMapper.insert(productProductClassify);
        }
    }

    @Override
    public List<ProductVo> getProductsByClassifyId(String id) {
        List<ProProduct> products = productMapper.getProductsByClassifyId(id);
        List<ProductVo> productVos = products.stream().map(item -> {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(item, productVo);
            return productVo;
        }).collect(Collectors.toList());
        return productVos;
    }
}
