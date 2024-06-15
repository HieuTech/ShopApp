package org.oauth2.shopapp.exception;


import lombok.Getter;
import org.oauth2.shopapp.constant.ErrorDetail;

@Getter
public class CategoryNotFoundException extends RuntimeException {
    private final ErrorDetail errorDetail;

    public CategoryNotFoundException(ErrorDetail e
    ) {
        this.errorDetail = e;
    }
}
