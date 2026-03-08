package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public Result<List<Order>> list() {
        return Result.success(orderService.listAllWithItems());
    }
    
    @GetMapping("/{id}")
    public Result<Order> getById(@PathVariable Long id) {
        return Result.success(orderService.getByIdWithItems(id));
    }
    
    @GetMapping("/{id}/items")
    public Result<List<OrderItem>> getItems(@PathVariable Long id) {
        return Result.success(orderService.getItemsByOrderId(id));
    }
    
    @PostMapping
    public Result<Boolean> save(@RequestBody Order order) {
        return Result.success(orderService.saveOrder(order));
    }
    
    @PutMapping
    public Result<Boolean> update(@RequestBody Order order) {
        return Result.success(orderService.updateOrder(order));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(orderService.deleteById(id));
    }
}
