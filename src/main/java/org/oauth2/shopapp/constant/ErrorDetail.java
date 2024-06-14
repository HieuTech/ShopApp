package org.oauth2.shopapp.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorDetail {

    SUCCESS(1010, "Success", HttpStatus.OK),
    CREATED(1010, "CREATED", HttpStatus.CREATED),


    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    NULL_POINTER_EXCEPTION(500, "Null Pointer Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    CREATE_JWT_ERROR(1001, "Cant Create JWT", HttpStatus.BAD_REQUEST),
    DECODE_JWT_ERROR(1010,"Cant Decode JWT", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    DATABASE_ERROR(1009, "Database Error", HttpStatus.BAD_REQUEST)
    ;

    ErrorDetail(int code, String message, HttpStatusCode httpStatusCode){
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
    private final Integer code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

}
