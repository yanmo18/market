package org.grow.mall.service;

import org.grow.mall.controller.dto.UsersDTO;
import org.grow.mall.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUsersService extends IService<Users> {
    UsersDTO UsersLogin(UsersDTO usersDTO);
    Users UsersRegister(UsersDTO usersDTO);


}
