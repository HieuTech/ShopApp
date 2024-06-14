package org.oauth2.shopapp.exception;


import lombok.Getter;
import org.oauth2.shopapp.constant.ErrorDetail;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final ErrorDetail errorDetail;
        public UserNotFoundException(ErrorDetail errorDetail){
            super(errorDetail.getMessage());
            this.errorDetail = errorDetail;
        }
}
