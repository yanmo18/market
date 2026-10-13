package org.grow.mall.service.impl;

import org.grow.mall.common.Constants;
import org.grow.mall.entity.Order;
import org.grow.mall.entity.Product;
import org.grow.mall.entity.Users;
import org.grow.mall.exception.ServiceException;
import org.grow.mall.mapper.OrderMapper;
import org.grow.mall.mapper.ProductMapper;
import org.grow.mall.mapper.UsersMapper;
import org.grow.mall.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void buyProduct(Order order) {
        try {
            /*
             * 减少用户余额
             * */
            subUserMoney(order);

            /*
             * 减少商品库存
             * */
            subProductStore(order);

            /*
             * 插入到订单表
             * */

            orderMapper.insert(order);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"购买失败");
        }

    }

    public void subUserMoney(Order order){
        try {
            Users users = usersMapper.selectById(order.getUserId());
            users.setMoney(users.getMoney() - order.getTotal());
            usersMapper.updateById(users);
        } catch (Exception e){
            throw new ServiceException(Constants.CODE_600,"用户余额减少错误");
        }
    }

    public void subProductStore(Order order){
        try {
            Product product = productMapper.selectById(order.getProductId());
            product.setStore(product.getStore() - order.getNum());
            productMapper.updateById(product);
        } catch (Exception e){
            throw new ServiceException(Constants.CODE_500, "商品数量减少失败");
        }

    }
}
