package com.example.demo.service;

import com.example.demo.common.UserExcelDTO;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    
    void exportUsers(HttpServletResponse response) throws IOException;
    
    void importUsers(java.io.InputStream inputStream) throws IOException;
    
    List<User> getUsersFromExcel(java.io.InputStream inputStream) throws IOException;
}
