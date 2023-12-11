package org.moonzhou.serializer.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author moon zhou
 * @version 1.0
 * @description:
 * @date 2023/12/11 11:35
 */
@Data
public class TestSerializerDto {
    private Long id;

    private String name;

    private Integer age;

    private char sex;

    private Double height;

    private String address;

    private LocalDate birthDay;

    private LocalDateTime createTime;
}
