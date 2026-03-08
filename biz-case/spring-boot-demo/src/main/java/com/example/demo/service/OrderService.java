package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import java.util.List;

public interface OrderService extends IService<Order> {
    
    List<Order> listAllWithItems();
    
    Order getByIdWithItems(Long id);
    
    boolean saveOrder(Order order);
    
    boolean updateOrder(Order order);
    
    boolean deleteById(Long id);
    
    List<OrderItem> getItemsByOrderId(Long orderId);
}
