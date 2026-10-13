package org.grow.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.grow.mall.common.Result;

import org.grow.mall.entity.Order;

import org.grow.mall.service.IOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    //table.serviceName  =>  private IUserService userService;
    @Resource
    private IOrderService orderService;




    //新增和修改
    @PostMapping
    public Result save(@RequestBody Order order) {
        orderService.saveOrUpdate(order);
        return Result.success();
    }


    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        orderService.removeById(id);
        return Result.success();
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        orderService.removeByIds(ids);
        return Result.success();
    }

    //查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(orderService.list());
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(orderService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(orderService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

