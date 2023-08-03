package org.moonzhou.onebyone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moonzhou.onebyone.enums.ExceptionEnum;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {
        private ExceptionEnum e;
}