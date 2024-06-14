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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserDTO userDTO
    ) {
        return ApiResponse.<UserResponse>builder()
                .code(ErrorDetail.SUCCESS.getHttpStatusCode().value())
                .message(ErrorDetail.CREATED.getMessage())
                .result(userService.createUser(userDTO))
                .build();
    }

    @GetMapping()
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(ErrorDetail.SUCCESS.getHttpStatusCode().value())
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(userService.getAllUsers())
                .build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId){
        return ApiResponse.<UserResponse>builder()
                .code(ErrorDetail.SUCCESS.getHttpStatusCode().value())
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(userService.getUserById(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo(){

        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userDTO,
                                   BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok("login ok");
    }
}
