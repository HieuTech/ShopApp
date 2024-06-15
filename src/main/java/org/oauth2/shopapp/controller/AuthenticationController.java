package org.oauth2.shopapp.controller;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.AuthenRequest;
import org.oauth2.shopapp.dto.request.IntrospectRequest;
import org.oauth2.shopapp.dto.request.LogoutRequest;
import org.oauth2.shopapp.dto.response.ApiResponse;
import org.oauth2.shopapp.dto.response.AuthenResponse;
import org.oauth2.shopapp.dto.response.IntrospectReponse;
import org.oauth2.shopapp.service.AuthenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenService authenService;
    @PostMapping("/token")
    ApiResponse<AuthenResponse> getToken(@RequestBody AuthenRequest authenRequest) {
        var result = authenService.login(authenRequest);
        return ApiResponse.<AuthenResponse>builder()
                .code(ErrorDetail.SUCCESS.getHttpStatusCode().value())
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        authenService.logout(logoutRequest);
        return ApiResponse.<Void>builder()
                .message(ErrorDetail.SUCCESS.getMessage())
                .build();

    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectReponse> introSpect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {

        return ApiResponse.<IntrospectReponse>builder()
                .result(authenService.introspect(request))
                .build();
    }


}
