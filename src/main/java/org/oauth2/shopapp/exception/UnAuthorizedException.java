package org.oauth2.shopapp.exception;

import lombok.Getter;
import org.oauth2.shopapp.constant.ErrorDetail;

@Getter
public class UnAuthorizedException extends RuntimeException{
    private final ErrorDetail errorDetail;

    public UnAuthorizedException(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }
}
