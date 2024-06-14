package org.oauth2.shopapp.service;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.AuthenRequest;
import org.oauth2.shopapp.dto.request.IntrospectRequest;
import org.oauth2.shopapp.dto.response.AuthenResponse;
import org.oauth2.shopapp.dto.response.IntrospectReponse;
import org.oauth2.shopapp.exception.UnAuthenticationException;
import org.oauth2.shopapp.exception.UnAuthorizedException;
import org.oauth2.shopapp.exception.UserNotFoundException;
import org.oauth2.shopapp.repository.IUserRepository;
import org.oauth2.shopapp.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthenService {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository iUserRepository;
    private final JwtProvider jwtProvider;

    public AuthenResponse login(AuthenRequest authenRequest){
        var user =  iUserRepository.findByEmail(authenRequest.getEmail()).orElseThrow(()-> new UserNotFoundException(ErrorDetail.USER_NOT_EXISTED));

        //kiem tra password
        boolean passwordIsMatched = passwordEncoder.matches(authenRequest.getPassword(), user.getPassword());

        if(!passwordIsMatched) throw new UserNotFoundException(ErrorDetail.USER_NOT_EXISTED); //ERROR 401

        //Ma hoa token
        return AuthenResponse.builder().token(jwtProvider.generateToken(user)).build();
    }

    public IntrospectReponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        boolean isValid = true;

        try{
            jwtProvider.verifyToken(token, false);
        }catch (UnAuthenticationException e){
            isValid = false;
        }
        return IntrospectReponse.builder()
                .isValid(isValid)
                .build();
    }
}
