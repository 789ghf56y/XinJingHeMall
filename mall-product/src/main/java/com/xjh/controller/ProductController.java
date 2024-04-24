package com.xjh.controller;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xjh.model.PageResult;
import com.xjh.model.R;
import com.xjh.pojo.ProProduct;
import com.xjh.pojo.ProProductProductClassify;
import com.xjh.pojo.query.ProductQuery;
import com.xjh.pojo.vo.ProductClassifyVo;
import com.xjh.pojo.vo.ProductVo;
import com.xjh.service.ProProductProductClassifyService;
import com.xjh.service.ProProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/product")
@RestController
public class ProductController {

    @Resource
    private ProProductService proProductService;

    @Resource
    private ProProductProductClassifyService productProductClassifyService;


//    查询所有商品，默认按照分类排序
    @GetMapping("/getProductList")
    public R getProductList(ProductQuery productQuery){
        List<ProductVo> productList =  proProductService.getProductList(productQuery);
        PageResult<ProductVo> pageResult = new PageResult<>();
        pageResult.setList(productList);
        pageResult.setTotal(new Long(proProductService.count()));
        pageResult.setPageNum(productQuery.getPageNum());
        pageResult.setPageSize(productQuery.getPageSize());
        return R.ok(pageResult);
    }

//    删除一个商品
    @DeleteMapping("/deleteProduct/{id}")
    @Transactional
    public R deleteProductById(@PathVariable String id){
        LambdaQueryWrapper<ProProductProductClassify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProProductProductClassify::getProductId,id);
        List<ProProductProductClassify> productProductClassifyList = productProductClassifyService.list(queryWrapper);
        if(productProductClassifyList.size() !=0){
            productProductClassifyService.removeByIds(productProductClassifyList.stream().map(item -> item.getId()).collect(Collectors.toList()));
        }
        proProductService.removeById(id);
        return R.ok();
    }

//    增加商品
    @PostMapping("/saveProduct")
    public R saveProduct(@RequestBody ProductVo productVo){
        proProductService.saveProduct(productVo);
        return R.ok();
    }

//    批量删除商品
    @DeleteMapping("/batchDeleteProduct")
    @Transactional
    public R batchDeleteProduct(@RequestBody List<String> ids){
        for (String id : ids) {
            LambdaQueryWrapper<ProProductProductClassify> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProProductProductClassify::getProductId,id);
            List<ProProductProductClassify> productProductClassifyList = productProductClassifyService.list(queryWrapper);
            if(productProductClassifyList.size() !=0){
                productProductClassifyService.removeByIds(productProductClassifyList.stream().map(item -> item.getId()).collect(Collectors.toList()));
            }
        }
        proProductService.removeByIds(ids);
        return R.ok();
    }

//    修改商品
    @PutMapping("/updateProduct")
    public R updateProduct(@RequestBody ProductVo productVo){
        proProductService.updateProduct(productVo);
        return R.ok();
    }

//修改状态
    @PutMapping("/updateStatus")
    public R updateUserStatus(@RequestBody ProductVo productVo){
        ProProduct product = new ProProduct();
        product.setStatus(productVo.getStatus());
        boolean flag = proProductService.update(product,new LambdaUpdateWrapper<ProProduct>()
                .eq(StringUtils.isNotBlank(productVo.getId().toString()),ProProduct::getId,productVo.getId()));
        return R.ok(flag);
    }

    @GetMapping("/getProductsByClassifyId/{id}")
    public R getProductsByClassifyId(@PathVariable String id){
        List<ProductVo>  products = proProductService.getProductsByClassifyId(id);
        return R.ok(products);
    }


}
