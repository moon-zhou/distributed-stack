package com.example.demo.common;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class UserExcelDTO {
    
    @ExcelProperty("ID")
    private Long id;
    
    @ExcelProperty("姓名")
    @ColumnWidth(20)
    private String name;
    
    @ExcelProperty("邮箱")
    @ColumnWidth(30)
    private String email;
    
    @ExcelProperty("电话")
    @ColumnWidth(20)
    private String phone;
    
    @ExcelProperty("年龄")
    private Integer age;
    
    @ExcelProperty("状态")
    private Integer status;
}
