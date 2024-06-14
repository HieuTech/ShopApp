package org.oauth2.shopapp.exception;

import lombok.Getter;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.springframework.security.oauth2.jwt.JwtException;

@Getter
public class UndecodeJwtException extends JwtException {

    private final ErrorDetail errorDetail;

    public UndecodeJwtException(ErrorDetail errorDetail){
        super(ErrorDetail.DECODE_JWT_ERROR.getMessage());
        this.errorDetail = errorDetail;
    }
}
