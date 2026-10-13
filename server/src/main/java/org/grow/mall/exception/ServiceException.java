package org.grow.mall.exception;


import lombok.Getter;

///RuntimeException自定义异常
@Getter
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
