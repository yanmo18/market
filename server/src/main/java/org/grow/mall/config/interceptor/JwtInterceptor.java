package org.grow.mall.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.grow.mall.common.Constants;
import org.grow.mall.entity.User;
import org.grow.mall.exception.ServiceException;
import org.grow.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 兼容两种取 token 方式：自定义 token 头 / 标准 Authorization: Bearer xxx
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            String auth = request.getHeader("Authorization");
            if (StrUtil.isNotBlank(auth)) {
                // 去掉 "Bearer " 前缀
                token = auth.startsWith("Bearer ") ? auth.substring(7) : auth;
            }
        }
        // 如果不是映射到方法直接通过

        if (handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) { //如果AuthAccess注解不等于空，则忽略token，通过校验
                return true;
            }
        }

        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401, "无token，请重新登录。"+this.getClass().getName());
        }

        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }

        // 根据token中的userid查询数据库
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
        }

        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }

        return true;
    }
}
