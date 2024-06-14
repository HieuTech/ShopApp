package org.oauth2.shopapp.exception;

import lombok.Getter;
import org.oauth2.shopapp.constant.ErrorDetail;

@Getter

public class NullPointerHandler extends NullPointerException{
    private final ErrorDetail errorDetail;
    public NullPointerHandler(ErrorDetail errorDetail){
        super(errorDetail.getMessage());
        this.errorDetail = errorDetail;
    }
}
