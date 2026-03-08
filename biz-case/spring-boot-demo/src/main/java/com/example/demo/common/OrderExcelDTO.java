package com.example.demo.common;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderExcelDTO {
    
    @ExcelProperty("订单ID")
    private Long id;
    
    @ExcelProperty("订单号")
    @ColumnWidth(25)
    private String orderNo;
    
    @ExcelProperty("用户ID")
    private Long userId;
    
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;
    
    @ExcelProperty("状态")
    private Integer status;
    
    @ExcelProperty("订单详情")
    @ColumnWidth(50)
    private String itemsJson;
}
