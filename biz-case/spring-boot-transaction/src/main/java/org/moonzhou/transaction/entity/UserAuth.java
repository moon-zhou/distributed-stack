package org.moonzhou.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

/**
 * 
 * @author moon zhou
 * @since 2023-09-06
 */
@Data
@TableName("t_user_auth")
public class UserAuth {

    private static final long serialVersionUID = 1L;

    @TableId(
            type = IdType.ASSIGN_ID
    )
    private Long id;

    /**
     * 
     */
    private String userNo;

    /**
     * 
     */
    private String idNo;

    /**
     * 
     */
    private String idName;

    /**
     * 
     */
    private LocalDate idBirthDate;

    /**
     * 
     */
    private String idStartDate;

    /**
     * 
     */
    private String idEndDate;

    /**
     * 
     */
    private String idLongTerm;

    /**
     * 
     */
    private String idNativePlace;

}
