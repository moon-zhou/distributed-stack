package com.moonzhou.springbooteasyexcel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.moonzhou.springbooteasyexcel.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EasyExcelService {

    /**
     * 导出用户数据到 Excel
     */
    public void exportUserExcel(HttpServletResponse response) throws IOException {
        // 创建示例数据
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId((long) (i + 1));
            user.setName("用户" + (i + 1));
            user.setAge(20 + i);
            user.setEmail("user" + (i + 1) + "@example.com");
            user.setCreateTime(new Date());
            userList.add(user);
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("用户数据", StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 导出数据
        EasyExcel.write(response.getOutputStream(), User.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("用户数据")
                .doWrite(userList);
    }

    /**
     * 从 Excel 导入用户数据
     */
    public List<User> importUserExcel(MultipartFile file) throws IOException {
        List<User> userList = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), User.class, new UserDataListener(userList))
                .sheet()
                .doRead();
        return userList;
    }

    /**
     * 获取示例数据
     */
    public List<User> getSampleData() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId((long) (i + 1));
            user.setName("示例用户" + (i + 1));
            user.setAge(25 + i);
            user.setEmail("sample" + (i + 1) + "@example.com");
            user.setCreateTime(new Date());
            userList.add(user);
        }
        return userList;
    }
}