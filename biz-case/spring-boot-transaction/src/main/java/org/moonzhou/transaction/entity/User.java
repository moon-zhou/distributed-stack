package org.moonzhou.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 
 * @author moon zhou
 * @since 2023-09-01
 */
@Data
public class User {

    private static final long serialVersionUID = 1L;

    @TableId(
            type = IdType.ASSIGN_ID
    )
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer age;

}
