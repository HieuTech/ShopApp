package org.oauth2.shopapp.exception;

import lombok.Getter;
import lombok.Setter;
import org.oauth2.shopapp.constant.ErrorDetail;

@Getter
@Setter
public class UserReadyExistException extends RuntimeException{

    private ErrorDetail errorDetail;

    public UserReadyExistException(ErrorDetail errorDetail){
        super(errorDetail.getMessage());
        this.errorDetail = errorDetail;
    }


}
