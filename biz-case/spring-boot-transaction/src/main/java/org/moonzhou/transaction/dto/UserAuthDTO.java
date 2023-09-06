package org.moonzhou.transaction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.moonzhou.transaction.entity.UserAuth;

import java.time.LocalDate;

/**
 * @author moon zhou
 * @since 2023-09-06
 */
@Data
@NoArgsConstructor
public class UserAuthDTO {

    private Long id;

    private String userNo;

    private String idNo;

    private String idName;

    private LocalDate idBirthDate;

    private String idStartDate;

    private String idEndDate;

    private String idLongTerm;

    private String idNativePlace;

    public UserAuthDTO(UserAuth entity) {
        this.id = entity.getId();
        this.userNo = entity.getUserNo();
        this.idNo = entity.getIdNo();
        this.idName = entity.getIdName();
        this.idBirthDate = entity.getIdBirthDate();
        this.idStartDate = entity.getIdStartDate();
        this.idEndDate = entity.getIdEndDate();
        this.idLongTerm = entity.getIdLongTerm();
        this.idNativePlace = entity.getIdNativePlace();
    }
}
