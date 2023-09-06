package org.moonzhou.transaction.param;

import lombok.Data;

/**
 * @author moon zhou
 * @version 1.0
 * @description:
 * @date 2023/9/6 21:06
 */
@Data
public class UserInfoParam {
    private UserParam userParam;

    private UserAuthParam userAuthParam;
}
