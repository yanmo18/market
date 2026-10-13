package org.grow.mall.controller.dto;

import lombok.Data;

//接受前端登录请求的参数
@Data
public class UsersDTO {
    private Integer id;
    private String username;
    private String password;
    private String realname;
    private Integer age;
    private String sex;
    private String phone;
    private Float money;
    private String address;
    private String email;
    private String photo;
    private String token;
}
