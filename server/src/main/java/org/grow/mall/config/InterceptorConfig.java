package org.grow.mall.config;

import org.grow.mall.config.interceptor.AuthAccess;
import org.grow.mall.config.interceptor.JwtInterceptor;
import org.grow.mall.config.interceptor.WxJwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Autowired
    private WxJwtInterceptor wxJwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器1：原 JwtInterceptor，作用于 PC 端管理后台（拦截所有非放行路径）
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login",
                        "/user/register",
                        "/**/export",
                        "/**/import",
                        "/file/**",
                        "/type/**",
                        "/users/**/",
                        "/wx/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/v2/api-docs/**",
                        "/doc.html",
                        "/webjars/**");

        // 拦截器2：WxJwtInterceptor，仅作用于 /wx/**（小程序顾客端）
//        registry.addInterceptor(wxJwtInterceptor)
//                .addPathPatterns("/wx/order/**");
    }

}
