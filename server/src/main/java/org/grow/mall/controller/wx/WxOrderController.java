package org.grow.mall.controller.wx;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.grow.mall.common.Result;
import org.grow.mall.entity.Order;
import org.grow.mall.entity.Users;
import org.grow.mall.service.IOrderService;
import org.grow.mall.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wx/order")
public class WxOrderController {
    @Resource
    private IOrderService orderService;

    /*
     * 购买商品
     * */
    @PostMapping("/users/buy")
    public Result productBuy(@RequestBody Order order){
        Users currentWxUsers = TokenUtils.getCurrentWxUsers();
        if(currentWxUsers!=null){
            order.setUserId(currentWxUsers.getId());
            order.setUserName(currentWxUsers.getRealname());
            order.setUserPhone(currentWxUsers.getPhone());
        }else{
            System.out.println("当前没有登录人");
        }

        orderService.buyProduct(order);
        return Result.success();
    }

    /*
    * 查询订单
    * */
    @GetMapping("/getinfo/byid")
    public Result getOrderById(@RequestParam String state){
        if(state.equals("3")){
            List<Order> list = orderService.list();
            return Result.success(list);
        } else {
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Order::getState, state);
            List<Order> list = orderService.list(wrapper);
            return Result.success(list);
        }
    }

    @PostMapping("/update")
    public Result updateOrderStatus(@RequestBody Order order){
        orderService.saveOrUpdate(order);
        return Result.success();
    }

}
