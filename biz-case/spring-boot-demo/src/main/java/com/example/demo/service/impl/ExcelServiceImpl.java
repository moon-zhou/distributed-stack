package com.example.demo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.example.demo.common.UserExcelDTO;
import com.example.demo.entity.User;
import com.example.demo.service.ExcelService;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void exportUsers(HttpServletResponse response) throws IOException {
        List<User> users = userService.listAll();
        List<UserExcelDTO> excelData = new ArrayList<>();
        
        for (User user : users) {
            UserExcelDTO dto = new UserExcelDTO();
            BeanUtils.copyProperties(user, dto);
            excelData.add(dto);
        }
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("用户数据", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        
        EasyExcel.write(response.getOutputStream(), UserExcelDTO.class)
                .sheet("用户列表")
                .doWrite(excelData);
    }
    
    @Override
    public void importUsers(java.io.InputStream inputStream) throws IOException {
        List<UserExcelDTO> dataList = new ArrayList<>();
        
        EasyExcel.read(inputStream, UserExcelDTO.class, new PageReadListener<UserExcelDTO>(dataList::addAll))
                .sheet()
                .doRead();
        
        for (UserExcelDTO dto : dataList) {
            User user = new User();
            BeanUtils.copyProperties(dto, user);
            user.setId(null);
            userService.save(user);
        }
    }
    
    @Override
    public List<User> getUsersFromExcel(java.io.InputStream inputStream) throws IOException {
        List<UserExcelDTO> dataList = new ArrayList<>();
        
        EasyExcel.read(inputStream, UserExcelDTO.class, new PageReadListener<UserExcelDTO>(dataList::addAll))
                .sheet()
                .doRead();
        
        List<User> users = new ArrayList<>();
        for (UserExcelDTO dto : dataList) {
            User user = new User();
            BeanUtils.copyProperties(dto, user);
            users.add(user);
        }
        return users;
    }
}
