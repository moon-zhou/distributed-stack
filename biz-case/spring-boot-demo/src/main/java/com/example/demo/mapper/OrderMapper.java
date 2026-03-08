package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Order getOrderWithItems(@Param("id") Long id);
    
    @Select("SELECT * FROM `order`")
    List<Order> getAllOrdersWithItems();
}
