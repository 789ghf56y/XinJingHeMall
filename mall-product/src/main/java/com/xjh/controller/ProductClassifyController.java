package com.xjh.controller;


import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjh.model.PageResult;
import com.xjh.model.R;
import com.xjh.pojo.ProProductClassify;
import com.xjh.pojo.query.ProductClassifyQuery;
import com.xjh.pojo.vo.ProductClassifyVo;
import com.xjh.service.ProProductClassifyService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product/productClassify")
public class ProductClassifyController {


    @Resource
    private ProProductClassifyService productClassifyService;

    //    查询所有商品，默认按照分类排序
    @GetMapping("/getProductClassifyList")
    public R getProductListByPage(ProductClassifyQuery productClassifyQuery){
        LambdaQueryWrapper<ProProductClassify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(productClassifyQuery.getTypeName()),ProProductClassify::getTypeName,productClassifyQuery.getTypeName());
        Page<ProProductClassify> productClassifyPage =  productClassifyService.page(new Page<>(productClassifyQuery.getPageNum(), productClassifyQuery.getPageSize()),queryWrapper);
        List<ProProductClassify> productClassifyList = productClassifyPage.getRecords();
        List<ProductClassifyVo> classifyVoList = productClassifyList.stream().map(productClassify -> {
            ProductClassifyVo classifyVo = new ProductClassifyVo();
            BeanUtils.copyProperties(productClassify, classifyVo);
            return classifyVo;
        }).collect(Collectors.toList());
        PageResult<ProductClassifyVo> pageResult = new PageResult<>();
        pageResult.setList(classifyVoList);
        pageResult.setTotal(productClassifyPage.getTotal());
        pageResult.setPageNum(productClassifyPage.getCurrent());
        pageResult.setPageSize(productClassifyPage.getSize());
        return R.ok(pageResult);
    }


    @GetMapping("/productClassifyList")
    public R getProductClassifyList(){
        List<ProProductClassify> productClassifies = productClassifyService.list();
        List<ProductClassifyVo> classifyVoList = productClassifies.stream().map(proProductClassify -> {
            ProductClassifyVo classifyVo = new ProductClassifyVo();
            BeanUtils.copyProperties(proProductClassify, classifyVo);
            return classifyVo;
        }).collect(Collectors.toList());
        return R.ok(classifyVoList);
    }

    @DeleteMapping("/deleteProductClassify/{id}")
    public R deleteProductById(@PathVariable String id){
        productClassifyService.removeById(id);
        return R.ok();
    }


    @PutMapping("/updateProductClassify")
    public R updateCommonUser(@RequestBody ProductClassifyVo productClassifyVo){
        ProProductClassify productClassify = new ProProductClassify();
        BeanUtils.copyProperties(productClassifyVo,productClassify);
        LambdaUpdateWrapper<ProProductClassify> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ProProductClassify::getId,productClassifyVo.getId());
        productClassifyService.update(productClassify,updateWrapper);
        return R.ok();
    }


    @PostMapping("/saveProductClassify")
    public R saveCommonUser(@RequestBody ProductClassifyVo productClassifyVo){
        ProProductClassify productClassify = new ProProductClassify();
        BeanUtils.copyProperties(productClassifyVo,productClassify);
        productClassifyService.save(productClassify);
        return R.ok();
    }

}
