package org.grow.mall.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import org.grow.mall.entity.User;
import org.grow.mall.entity.Users;
import org.grow.mall.service.IUserService;
import org.grow.mall.service.IUsersService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static IUserService staticUserService;
    private static IUsersService staticWxUsersService;

    @Resource
    private IUserService userService;
    @Resource
    private IUsersService usersService;
    @PostConstruct
    public void setUserService(){
        staticUserService =userService;
        staticWxUsersService = usersService;
    }

    public static String genToken(String userId, String sign){
        return JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .sign(Algorithm.HMAC256(sign));
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            if(!StrUtil.isNotBlank(token)){
                token = request.getHeader("token");
            }
            if (StrUtil.isNotBlank(token)) {

                String userId = JWT.decode(token).getAudience().get(0);
                System.out.println("解析token获取员工uid :"+userId);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    public static Users getCurrentWxUsers() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            if(!StrUtil.isNotBlank(token)){
                token = request.getHeader("token");
            }
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                System.out.println("解析token获取uid:"+userId);
                return staticWxUsersService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
