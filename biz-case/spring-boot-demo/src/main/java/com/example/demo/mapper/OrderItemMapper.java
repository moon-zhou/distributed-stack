package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    
    @Select("SELECT * FROM t_order_item WHERE order_id = #{orderId}")
    List<OrderItem> getItemsByOrderId(@Param("orderId") Long orderId);
}
