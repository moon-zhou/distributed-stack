package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.mapper.OrderItemMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    
    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Override
    public List<Order> listAllWithItems() {
        List<Order> orders = list();
        for (Order order : orders) {
            order.setItems(orderItemMapper.getItemsByOrderId(order.getId()));
        }
        return orders;
    }
    
    @Override
    public Order getByIdWithItems(Long id) {
        Order order = baseMapper.selectById(id);
        if (order != null) {
            order.setItems(orderItemMapper.getItemsByOrderId(id));
        }
        return order;
    }
    
    @Override
    @Transactional
    public boolean saveOrder(Order order) {
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            BigDecimal total = BigDecimal.ZERO;
            for (OrderItem item : order.getItems()) {
                total = total.add(item.getSubtotal());
            }
            order.setTotalAmount(total);
        }
        boolean result = save(order);
        if (result && order.getItems() != null && !order.getItems().isEmpty()) {
            for (OrderItem item : order.getItems()) {
                item.setOrderId(order.getId());
                orderItemMapper.insert(item);
            }
        }
        return result;
    }
    
    @Override
    @Transactional
    public boolean updateOrder(Order order) {
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            BigDecimal total = BigDecimal.ZERO;
            for (OrderItem item : order.getItems()) {
                total = total.add(item.getSubtotal());
            }
            order.setTotalAmount(total);
        }
        boolean result = updateById(order);
        if (result && order.getItems() != null) {
            orderItemMapper.delete(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<OrderItem>()
                    .eq(OrderItem::getOrderId, order.getId()));
            for (OrderItem item : order.getItems()) {
                item.setOrderId(order.getId());
                item.setId(null);
                orderItemMapper.insert(item);
            }
        }
        return result;
    }
    
    @Override
    @Transactional
    public boolean deleteById(Long id) {
        orderItemMapper.delete(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<OrderItem>()
                .eq(OrderItem::getOrderId, id));
        return removeById(id);
    }
    
    @Override
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        return orderItemMapper.getItemsByOrderId(orderId);
    }
}
