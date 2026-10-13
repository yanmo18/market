package org.grow.mall.controller.wx;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.grow.mall.common.Result;
import org.grow.mall.entity.Product;
import org.grow.mall.entity.Type;
import org.grow.mall.service.IProductService;
import org.grow.mall.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wx/user")
public class WxProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ITypeService typeService;

    @GetMapping("/product/info")
    public Result getProductAllData(){
        List<Product> list = productService.list();
        return Result.success(list);
    }

    /*
    * 获取全部分类信息
    * */
    @GetMapping("/product/type")
    public Result getProductAllType(){
        List<Type> typeList = typeService.list();
        return Result.success(typeList);
    }

    /*
    * 分类获取
    * */
    @GetMapping("/product/category")
    public Result getProductCategory(@RequestParam Integer num){
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getTypeId, num);
        List<Product> list = productService.list(queryWrapper);
        return Result.success(list);
    }

    /*
    * 根据商品id获取商品数据
    * */
    @GetMapping("/product/info-id")
    public Result getProductById(@RequestParam Integer id){
        Product product = productService.getById(id);
        return Result.success(product);
    }


}
