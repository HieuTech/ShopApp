package org.oauth2.shopapp.exception;

import lombok.Getter;
import org.oauth2.shopapp.constant.ErrorDetail;

@Getter
public class UnAuthenticationException extends RuntimeException{
    private final ErrorDetail errorDetail;

    public UnAuthenticationException(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }
}
