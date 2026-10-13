package org.grow.mall.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//接口统一返回包装类
@Data//getting和setting方法
@NoArgsConstructor//无参构造
@AllArgsConstructor//有参构造
public class Result {

    private String code;
    private String msg;
    private Object data;//后台携带的数据

    public static Result success() {//没有数据的成功
        return new Result(Constants.CODE_200, "", null);
    }

    public static Result success(Object data) {//有数据的成功
        return new Result(Constants.CODE_200, "", data);//有参构造
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null);
    }
}
