package org.moonzhou.transaction.param;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author moon zhou
 * @since 2023-09-06
 */
@Data
public class UserAuthParam {

    private String userNo;

    private String idNo;

    private String idName;

    private LocalDate idBirthDate;

    private String idStartDate;

    private String idEndDate;

    private String idLongTerm;

    private String idNativePlace;
}
