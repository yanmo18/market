package org.grow.mall.mapper;

import org.grow.mall.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface OrderMapper extends BaseMapper<Order> {

    @Update("update sys_order set state =#{state} , payment_time = #{paymentTime}, alipay_no = #{alipayNo} where no = #{no}")
    int updateState(@Param("no") String no, @Param("state") String state,
                     @Param("paymentTime") String gmtPayment,
                     @Param("alipayNo") String alipayTradeNo);
}
