package org.oauth2.shopapp.exception;

import com.mongodb.MongoException;
import lombok.Getter;
import lombok.Setter;
import org.oauth2.shopapp.constant.ErrorDetail;

@Getter
@Setter
public class MongoExceptionHandler extends MongoException {
    private ErrorDetail errorDetail;
    public MongoExceptionHandler(ErrorDetail errorDetail) {
        super(errorDetail.getCode(), errorDetail.getMessage());
        this.errorDetail = errorDetail;
    }


}
