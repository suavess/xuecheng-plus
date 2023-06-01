package com.suave.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Suave
 * @since 2023/06/01 21:43
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class XCPException extends RuntimeException {
    private String errMsg;

    public XCPException(CommonError error) {
        this.errMsg = error.getErrMessage();
    }
}
