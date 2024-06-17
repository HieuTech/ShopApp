package org.oauth2.shopapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.UserDTO;
import org.oauth2.shopapp.dto.request.UserLoginDTO;
import org.oauth2.shopapp.dto.response.ApiResponse;
import org.oauth2.shopapp.dto.response.UserResponse;
import org.oauth2.shopapp.service.UserService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
//    http://localhost:8080/identity/api/v1/register

    @PostMapping("/register")
    public ApiResponse<?> createUser(@RequestBody @Valid UserDTO userDTO,BindingResult result
    ) {
        if(result.hasErrors()){
            ApiResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(result.getFieldError().getDefaultMessage())
                    .build();
        }

        return ApiResponse.<UserResponse>builder()
                .code(ErrorDetail.SUCCESS.getHttpStatusCode().value())
                .message(ErrorDetail.CREATED.getMessage())
                .result(userService.createUser(userDTO))
                .build();
    }

//    http://localhost:8080/identity/api/v1/admin
    @GetMapping("/admin")
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(ErrorDetail.SUCCESS.getHttpStatusCode().value())
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(userService.getAllUsers())
                .build();
    }
    @GetMapping("/admin/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId){
        return ApiResponse.<UserResponse>builder()
                .code(ErrorDetail.SUCCESS.getHttpStatusCode().value())
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(userService.getUserById(userId))
                .build();
    }

//    http://localhost:8080/identity/api/v1/user/my-info
    @GetMapping("/user/my-info")
    ApiResponse<UserResponse> getMyInfo(){

        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
//    http://localhost:8080/identity/api/v1/admin/block-user?id=666bea12aac9586824e7f808
    @GetMapping("/admin/block-user")
    public ApiResponse<?> blockUser(@RequestParam("id") String id

    ) {
        return ApiResponse.builder()
                .code(ErrorDetail.SUCCESS.getHttpStatusCode().value())
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(userService.blockUser(id))
                .build();
    }
//    http://localhost:8080/identity/api/v1/admin/unblock-user/666bea12aac9586824e7f808
    @GetMapping("/admin/unblock-user/{id}")
    public ApiResponse<?> unBlockUser(@PathVariable("id") String id

    ) {
        return ApiResponse.builder()
                .code(ErrorDetail.SUCCESS.getHttpStatusCode().value())
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(userService.unBlockUser(id))
                .build();
    }
}
