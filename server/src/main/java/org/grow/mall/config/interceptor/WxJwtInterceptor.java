package org.grow.mall.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.grow.mall.common.Constants;
import org.grow.mall.entity.Users;
import org.grow.mall.exception.ServiceException;
import org.grow.mall.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信小程序端 JWT 拦截器
 * 仅作用于 /wx/** 路径，校验小程序顾客（sys_users）token
 * 兼容自定义 token 头和标准 Authorization: Bearer xxx
 */
@Component
public class WxJwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUsersService usersService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 兼容两种取 token 方式
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            String auth = request.getHeader("Authorization");
            if (StrUtil.isNotBlank(auth)) {
                token = auth.startsWith("Bearer ") ? auth.substring(7) : auth;
            }
        }

        // 2. @AuthAccess 注解放行
        if (handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }

        // 3. token 必填校验
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401, "无token，请先登录。"+this.getClass().getName());
        }

        // 4. 解析 token 中的 userId
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "token解析失败，请重新登录");
        }

        // 5. 查小程序顾客表 sys_users（Users.id 为 Integer，需转换类型）
        Users users = usersService.getById(Integer.valueOf(userId));
        if (users == null) {
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
        }
        RequestContextHolder.currentRequestAttributes().setAttribute("UID", userId, 0);
        RequestContextHolder.currentRequestAttributes().setAttribute("token", token, 0);


        // 6. 微信小程序固定密钥验证 token 合法性（与 WxLoginController 签发保持一致）
        final String wxjwtSecret = "abcdefghijklmn";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(wxjwtSecret)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }

        return true;
    }

    public static Integer getCurrentUser() {
        return (Integer) RequestContextHolder.currentRequestAttributes().getAttribute("UID", 0);
    }

    public static Integer getUID() {
        return (Integer) RequestContextHolder.currentRequestAttributes().getAttribute("UID", 0);
    }

    public static String getCurrentToken() {
        return (String) RequestContextHolder.currentRequestAttributes().getAttribute("token", 0);
    }
}
