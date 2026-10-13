package org.grow.mall.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.grow.mall.common.Constants;
import org.grow.mall.common.Result;
import org.grow.mall.controller.dto.UsersDTO;
import org.grow.mall.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.grow.mall.service.IUsersService;
import org.grow.mall.entity.Users;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UsersController {

    //table.serviceName  =>  private IUserService userService;
    @Resource
    private IUsersService usersService;

    //登录接口
    @PostMapping("/users-login")
    public Result UsersLogin(@RequestBody UsersDTO usersDTO) {
        String username = usersDTO.getUsername();
        String password = usersDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UsersDTO dto = usersService.UsersLogin(usersDTO);
        // 生成 token 供小程序端访问 /wx/** 使用
        if (dto != null && dto.getId() != null) {
            String token = TokenUtils.genToken(String.valueOf(dto.getId()), dto.getPassword());
            dto.setToken(token);
        }
        return Result.success(dto);
    }

    //注册接口
    @PostMapping("/UsersRegister")
    public Result UsersRegister(@RequestBody UsersDTO usersDTO) {
        String username = usersDTO.getUsername();
        String password = usersDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(usersService.UsersRegister(usersDTO));
    }

    @GetMapping("/get-userinfo")
    public Result getUserInfoById(@RequestParam Integer id){
        Users users = usersService.getById(id);
        return Result.success(users);
    }

    //新增和修改
    @PostMapping
    public Result save(@RequestBody Users users) {
        usersService.saveOrUpdate(users);
        return Result.success();
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        usersService.removeById(id);
        return Result.success();
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        usersService.removeByIds(ids);
        return Result.success();
    }

    //查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(usersService.list());
    }

    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(usersService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String realname) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        if (!"".equals(realname)) {
            queryWrapper.like("realname", realname);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(usersService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}

