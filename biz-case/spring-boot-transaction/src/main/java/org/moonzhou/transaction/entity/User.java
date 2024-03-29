package org.moonzhou.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @author moon zhou
 * @since 2023-09-01
 */
@Data
@TableName("t_user")
public class User {

    private static final long serialVersionUID = 1L;

    @TableId(
            type = IdType.ASSIGN_ID
    )
    private Long id;

    /**
     * 用户业务层面的编号，户头号
     * 只做代码逻辑层面的关联处理，可以作为内部rpc的接口参数，进行服务端接口调用的参数，但是不能作为http接口的参数或者返回。
     */
    private String userNo;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer age;

}
