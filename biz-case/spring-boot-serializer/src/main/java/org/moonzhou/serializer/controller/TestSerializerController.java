package org.moonzhou.serializer.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.moonzhou.serializer.dto.Result;
import org.moonzhou.serializer.dto.TestSerializerDto;
import org.moonzhou.serializer.param.TestSerializerParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test Serializer", description = "Test Serializer")
@Slf4j
@RestController
@RequestMapping("/api/v1/test-serializer")
public class TestSerializerController {

    @ApiResponse(description = "test serializer")
    @PostMapping("/test")
    public Result<TestSerializerDto> test(@RequestBody TestSerializerParam testSerializerParam) {

        TestSerializerDto testSerializerDto = new TestSerializerDto();
        BeanUtils.copyProperties(testSerializerParam, testSerializerDto);

        return Result.success(testSerializerDto);
    }

}