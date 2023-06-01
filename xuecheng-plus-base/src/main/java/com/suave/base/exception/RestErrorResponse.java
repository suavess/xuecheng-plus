package com.suave.base.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Suave
 * @since 2023/06/01 21:42
 */
@Data
@AllArgsConstructor
public class RestErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1886374470300459409L;

    private String errMsg;
}
