package org.grow.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.grow.mall.common.Constants;
import org.grow.mall.controller.dto.UsersDTO;
import org.grow.mall.entity.Users;
import org.grow.mall.exception.ServiceException;
import org.grow.mall.mapper.UsersMapper;
import org.grow.mall.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Override
    public UsersDTO UsersLogin(UsersDTO usersDTO) {
        Users one = getUsersInfo(usersDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, usersDTO, true);
            return usersDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }

    }

    @Override
    public Users UsersRegister(UsersDTO usersDTO) {
        Users one = getUsersInfo(usersDTO);
        if (one == null) {
            one = new Users();
            BeanUtil.copyProperties(usersDTO, one, true);
            save(one);
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名以存在");
        }
        return one;
    }


    private Users getUsersInfo(UsersDTO usersDTO) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", usersDTO.getUsername());
        queryWrapper.eq("password", usersDTO.getPassword());
        Users one;
        try {
            one = getOne(queryWrapper);//从数据库查询用户信息
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}
