package org.grow.mall.service;

import org.grow.mall.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;


public interface IOrderService extends IService<Order> {

    void buyProduct(Order order);
}
