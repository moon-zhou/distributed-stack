package org.moonzhou.serializer.param;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author moon zhou
 * @version 1.0
 * @description:
 * @date 2023/12/11 11:34
 */
@Data
public class TestSerializerParam {
    private Long id;

    private String name;

    private Integer age;

    private char sex;

    private Double height;

    private String address;

    private LocalDate birthDay;

    private LocalDateTime createTime;


}
