package org.oauth2.shopapp.exception;

import com.amazonaws.services.devicefarm.model.Run;
import lombok.Getter;
import org.oauth2.shopapp.constant.ErrorDetail;

@Getter
public class CreateTokenException extends RuntimeException {

    private final ErrorDetail errorDetail;
    public CreateTokenException(ErrorDetail errorDetail){
        this.errorDetail = errorDetail;
    }
}
