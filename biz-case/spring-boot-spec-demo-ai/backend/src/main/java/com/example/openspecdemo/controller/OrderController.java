package com.example.openspecdemo.controller;

import com.example.openspecdemo.generated.api.OrdersApi;
import com.example.openspecdemo.generated.model.CreateOrderRequest;
import com.example.openspecdemo.generated.model.CreateOrderRequestItemsInner;
import com.example.openspecdemo.generated.model.OrderResponse;
import com.example.openspecdemo.generated.model.OrderResponseItemsInner;
import com.example.openspecdemo.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrdersApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderResponse> createOrder(@Valid CreateOrderRequest createOrderRequest) {
        com.example.openspecdemo.dto.order.CreateOrderRequest serviceRequest = new com.example.openspecdemo.dto.order.CreateOrderRequest();
        serviceRequest.setUserId(createOrderRequest.getUserId());
        serviceRequest.setItems(createOrderRequest.getItems().stream().map(this::toServiceItem).collect(Collectors.toList()));

        com.example.openspecdemo.dto.order.OrderResponse serviceResponse = orderService.create(serviceRequest);
        return ResponseEntity.status(201).body(toGeneratedOrder(serviceResponse));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> listOrders() {
        return ResponseEntity.ok(orderService.list().stream().map(this::toGeneratedOrder).toList());
    }

    @Override
    public ResponseEntity<OrderResponse> getOrderById(Long id) {
        return ResponseEntity.ok(toGeneratedOrder(orderService.getById(id)));
    }

    private com.example.openspecdemo.dto.order.CreateOrderRequest.OrderItemRequest toServiceItem(CreateOrderRequestItemsInner item) {
        com.example.openspecdemo.dto.order.CreateOrderRequest.OrderItemRequest serviceItem =
            new com.example.openspecdemo.dto.order.CreateOrderRequest.OrderItemRequest();
        serviceItem.setProductId(item.getProductId());
        serviceItem.setQuantity(item.getQuantity());
        return serviceItem;
    }

    private OrderResponse toGeneratedOrder(com.example.openspecdemo.dto.order.OrderResponse order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount() == null ? null : order.getTotalAmount().doubleValue());
        response.setCreatedAt(order.getCreatedAt() == null ? null : order.getCreatedAt().atOffset(java.time.ZoneOffset.UTC));

        List<OrderResponseItemsInner> itemResponses = order.getItems().stream().map(item -> {
            OrderResponseItemsInner generatedItem = new OrderResponseItemsInner();
            generatedItem.setProductId(item.getProductId());
            generatedItem.setProductName(item.getProductName());
            generatedItem.setQuantity(item.getQuantity());
            generatedItem.setUnitPrice(item.getUnitPrice() == null ? null : item.getUnitPrice().doubleValue());
            generatedItem.setLineAmount(item.getLineAmount() == null ? null : item.getLineAmount().doubleValue());
            return generatedItem;
        }).toList();

        response.setItems(itemResponses);
        return response;
    }
}
