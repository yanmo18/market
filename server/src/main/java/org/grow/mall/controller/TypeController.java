package org.grow.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.grow.mall.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.grow.mall.service.ITypeService;
import org.grow.mall.entity.Type;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Resource
    private ITypeService typeService;

    //新增和修改
    @PostMapping
    public Result save(@RequestBody Type type) {
        typeService.saveOrUpdate(type);
        return Result.success();
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        typeService.removeById(id);
        return Result.success();
    }

    //批量輸出數據
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        typeService.removeByIds(ids);
        return Result.success();
    }

    //查询所有数据
    @GetMapping("/all")
    public Result findAll() {
        return Result.success(typeService.list());
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(typeService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam(defaultValue = "") String type, @RequestParam(defaultValue = "") String message, @RequestParam(defaultValue = "") String shop) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        if (!"".equals(type)) {
            queryWrapper.like("type", type);
        }
        if (!"".equals(message)) {
            queryWrapper.like("message", message);
        }
        if (!"".equals(shop)) {
            queryWrapper.like("shop", shop);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(typeService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

