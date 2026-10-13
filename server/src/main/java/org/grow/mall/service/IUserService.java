package org.grow.mall.service;

import org.grow.mall.controller.dto.UserDTO;
import org.grow.mall.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

}
