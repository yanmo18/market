package org.grow.mall.config.interceptor;

import java.lang.annotation.*;
//用来忽略接口的token限制
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
}
