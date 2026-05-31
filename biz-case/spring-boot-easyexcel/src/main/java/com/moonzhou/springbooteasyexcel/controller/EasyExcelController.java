package com.moonzhou.springbooteasyexcel.controller;

import com.moonzhou.springbooteasyexcel.entity.User;
import com.moonzhou.springbooteasyexcel.service.EasyExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class EasyExcelController {

    @Autowired
    private EasyExcelService easyExcelService;

    /**
     * 导出用户数据到 Excel
     */
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        easyExcelService.exportUserExcel(response);
    }

    /**
     * 从 Excel 导入用户数据
     */
    @PostMapping("/import")
    public List<User> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return easyExcelService.importUserExcel(file);
    }

    /**
     * 获取示例数据
     */
    @GetMapping("/sample")
    public List<User> sampleData() {
        return easyExcelService.getSampleData();
    }
}