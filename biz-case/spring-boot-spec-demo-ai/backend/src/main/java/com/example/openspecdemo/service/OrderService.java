package com.example.openspecdemo.service;

import com.example.openspecdemo.dto.order.CreateOrderRequest;
import com.example.openspecdemo.dto.order.OrderResponse;
import com.example.openspecdemo.entity.OrderEntity;
import com.example.openspecdemo.entity.OrderItemEntity;
import com.example.openspecdemo.entity.ProductEntity;
import com.example.openspecdemo.entity.UserEntity;
import com.example.openspecdemo.repository.OrderItemRepository;
import com.example.openspecdemo.repository.OrderRepository;
import com.example.openspecdemo.repository.ProductRepository;
import com.example.openspecdemo.repository.UserRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(
        OrderRepository orderRepository,
        OrderItemRepository orderItemRepository,
        ProductRepository productRepository,
        UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setStatus("CREATED");
        order.setCreatedAt(Instant.now());
        order.setTotalAmount(BigDecimal.ZERO);

        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItemEntity> savedItems = new ArrayList<>();

        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            ProductEntity product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + itemRequest.getProductId()));

            OrderItemEntity item = new OrderItemEntity();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setLineAmount(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));

            total = total.add(item.getLineAmount());
            savedItems.add(orderItemRepository.save(item));
        }

        order.setTotalAmount(total);
        OrderEntity savedOrder = orderRepository.save(order);

        return toOrderResponse(savedOrder, savedItems);
    }

    public List<OrderResponse> list() {
        return orderRepository.findAll().stream()
            .map(order -> toOrderResponse(order, orderItemRepository.findByOrderId(order.getId())))
            .toList();
    }

    public OrderResponse getById(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        return toOrderResponse(order, orderItemRepository.findByOrderId(orderId));
    }

    private OrderResponse toOrderResponse(OrderEntity order, List<OrderItemEntity> items) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setCreatedAt(order.getCreatedAt());

        List<OrderResponse.OrderItemResponse> itemResponses = items.stream().map(item -> {
            OrderResponse.OrderItemResponse dto = new OrderResponse.OrderItemResponse();
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setUnitPrice(item.getUnitPrice());
            dto.setLineAmount(item.getLineAmount());
            return dto;
        }).toList();

        response.setItems(itemResponses);
        return response;
    }
}
