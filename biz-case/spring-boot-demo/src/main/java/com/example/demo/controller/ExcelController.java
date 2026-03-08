package com.example.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.example.demo.common.OrderExcelDTO;
import com.example.demo.common.Result;
import com.example.demo.common.UserExcelDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.service.ExcelService;
import com.example.demo.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {
    
    @Autowired
    private ExcelService excelService;
    
    @Autowired
    private OrderService orderService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @GetMapping("/users/export")
    public void exportUsers(HttpServletResponse response) throws IOException {
        excelService.exportUsers(response);
    }
    
    @PostMapping("/users/import")
    public Result<Integer> importUsers(@RequestParam("file") MultipartFile file) throws IOException {
        List<UserExcelDTO> dataList = new ArrayList<>();
        
        EasyExcel.read(file.getInputStream(), UserExcelDTO.class, new PageReadListener<UserExcelDTO>(dataList::addAll))
                .sheet()
                .doRead();
        
        for (UserExcelDTO dto : dataList) {
            com.example.demo.entity.User user = new com.example.demo.entity.User();
            BeanUtils.copyProperties(dto, user);
            user.setId(null);
            excelService.getUsersFromExcel(file.getInputStream());
            break;
        }
        
        excelService.importUsers(file.getInputStream());
        return Result.success(dataList.size());
    }
    
    @GetMapping("/orders/export")
    public void exportOrders(HttpServletResponse response) throws IOException {
        List<Order> orders = orderService.listAllWithItems();
        List<OrderExcelDTO> excelData = new ArrayList<>();
        
        for (Order order : orders) {
            OrderExcelDTO dto = new OrderExcelDTO();
            BeanUtils.copyProperties(order, dto);
            
            if (order.getItems() != null && !order.getItems().isEmpty()) {
                dto.setItemsJson(objectMapper.writeValueAsString(order.getItems()));
            }
            
            excelData.add(dto);
        }
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("订单数据", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        
        EasyExcel.write(response.getOutputStream(), OrderExcelDTO.class)
                .sheet("订单列表")
                .doWrite(excelData);
    }
    
    @PostMapping("/orders/import")
    public Result<Integer> importOrders(@RequestParam("file") MultipartFile file) throws IOException {
        List<OrderExcelDTO> dataList = new ArrayList<>();
        
        EasyExcel.read(file.getInputStream(), OrderExcelDTO.class, new PageReadListener<OrderExcelDTO>(dataList::addAll))
                .sheet()
                .doRead();
        
        int count = 0;
        for (OrderExcelDTO dto : dataList) {
            Order order = new Order();
            BeanUtils.copyProperties(dto, order);
            order.setId(null);
            
            if (dto.getItemsJson() != null && !dto.getItemsJson().isEmpty()) {
                List<OrderItem> items = objectMapper.readValue(dto.getItemsJson(), 
                        objectMapper.getTypeFactory().constructCollectionType(List.class, OrderItem.class));
                order.setItems(items);
            }
            
            orderService.saveOrder(order);
            count++;
        }
        
        return Result.success(count);
    }
}
