package org.oauth2.shopapp.exception;


import lombok.Getter;
import org.oauth2.shopapp.constant.ErrorDetail;

@Getter
public class NotFoundException extends RuntimeException {

    private final ErrorDetail errorDetail;
        public NotFoundException(ErrorDetail errorDetail){
            super(errorDetail.getMessage());
            this.errorDetail = errorDetail;
        }
}
