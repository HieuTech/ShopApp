package org.oauth2.shopapp.exception;


import lombok.extern.slf4j.Slf4j;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {



    @ExceptionHandler(value = UserReadyExistException.class)
    ResponseEntity<ApiResponse> handleUserExistException(UserReadyExistException exception) {
        log.info("UserReadyExistException" + exception);
        return ResponseEntity.status(exception.getErrorDetail().getHttpStatusCode()).body(ApiResponse.
                builder()
                .code(exception.getErrorDetail().getCode())
                .message(exception.getMessage())
                .result(exception.getErrorDetail().getHttpStatusCode())
                .build());
    }

    @ExceptionHandler(value = CreateTokenException.class)
    ResponseEntity<ApiResponse> handleCreateTokenException(CreateTokenException ex){
        log.info("CreateTokenException" + ex);
        return ResponseEntity.status(ex.getErrorDetail().getHttpStatusCode()).body(ApiResponse
                .builder()
                        .code(ex.getErrorDetail().getCode())
                        .message(ex.getErrorDetail().getMessage())
                        .result(ex.getErrorDetail().getHttpStatusCode())
                .build()
        );
    }
    @ExceptionHandler(value = UndecodeJwtException.class)
    ResponseEntity<ApiResponse> handleUndecodeTokenException(UndecodeJwtException ex){
        log.info("UndecodeJwtException" + ex);
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                        .code(ex.getErrorDetail().getCode())
                        .message(ex.getMessage())
                        .result(ex.getErrorDetail().getHttpStatusCode())

                .build());

    }

    @ExceptionHandler(value = NotFoundException.class)
    ResponseEntity<ApiResponse> handleUserNotFoundException(NotFoundException ex){
        log.info("UserNotFoundException" + ex);
        return ResponseEntity.status(ex.getErrorDetail().getHttpStatusCode()).body(ApiResponse.
                builder()
                .code(ex.getErrorDetail().getCode())
                .message(ex.getMessage())
                .result(ex.getErrorDetail().getHttpStatusCode())
                .build());
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    ResponseEntity<ApiResponse> handleUnAuthorizedException(UnAuthorizedException ex){
        log.info("UnAuthorizedException" + ex);
        return ResponseEntity.status(ex.getErrorDetail().getHttpStatusCode()).body(
                ApiResponse.builder()
                        .code(ex.getErrorDetail().getCode())
                        .message(ex.getErrorDetail().getMessage())
                        .result(ex.getErrorDetail().getHttpStatusCode())
                        .build()
        );
    }
    @ExceptionHandler(value = UnAuthenticationException.class)
    ResponseEntity<ApiResponse> handleUnAuthenException(UnAuthenticationException ex){
        log.info("UnAuthenticationException" + ex);
        return ResponseEntity.status(ex.getErrorDetail().getHttpStatusCode()).body(
                ApiResponse.builder()
                        .code(ex.getErrorDetail().getCode())
                        .message(ex.getErrorDetail().getMessage())
                        .result(ex.getErrorDetail().getHttpStatusCode())
                        .build()
        );
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleException(RuntimeException e){
        log.info("Exception" + e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                        .code(ErrorDetail.UNCATEGORIZED_EXCEPTION.getCode())
                        .message(ErrorDetail.UNCATEGORIZED_EXCEPTION.getMessage())
                        .result(ErrorDetail.UNCATEGORIZED_EXCEPTION.getHttpStatusCode())
                .build());
    }

    @ExceptionHandler(value = NullPointerHandler.class)
    ResponseEntity<ApiResponse> handleNullPointerException(NullPointerHandler e){
        log.info("NullPointerHandler" + e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                        .code(ErrorDetail.NULL_POINTER_EXCEPTION.getCode())
                        .message(ErrorDetail.NULL_POINTER_EXCEPTION.getMessage())
                        .result(ErrorDetail.NULL_POINTER_EXCEPTION.getHttpStatusCode())
                .build());

    }

    @ExceptionHandler(value = MongoExceptionHandler.class)
    ResponseEntity<ApiResponse> handleMongoException(MongoExceptionHandler ex) {
        return ResponseEntity.status(ex.getErrorDetail().getHttpStatusCode()).body(ApiResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .result(ex.getErrorDetail().getHttpStatusCode())
                .build());

    }


}
