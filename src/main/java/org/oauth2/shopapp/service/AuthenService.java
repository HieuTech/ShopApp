package org.oauth2.shopapp.service;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.AuthenRequest;
import org.oauth2.shopapp.dto.request.IntrospectRequest;
import org.oauth2.shopapp.dto.request.LogoutRequest;
import org.oauth2.shopapp.dto.response.ApiResponse;
import org.oauth2.shopapp.dto.response.AuthenResponse;
import org.oauth2.shopapp.dto.response.IntrospectReponse;
import org.oauth2.shopapp.entity.InvalidToken;
import org.oauth2.shopapp.exception.UnAuthenticationException;
import org.oauth2.shopapp.exception.UnAuthorizedException;
import org.oauth2.shopapp.exception.UndecodeJwtException;
import org.oauth2.shopapp.exception.UserNotFoundException;
import org.oauth2.shopapp.repository.IInvalidToken;
import org.oauth2.shopapp.repository.IUserRepository;
import org.oauth2.shopapp.security.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenService {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository iUserRepository;
    private final IInvalidToken iInvalidToken;
    private final JwtProvider jwtProvider;

    public AuthenResponse login(AuthenRequest authenRequest){
        var user =  iUserRepository.findByEmail(authenRequest.getEmail()).orElseThrow(()-> new UserNotFoundException(ErrorDetail.USER_NOT_EXISTED));

        //kiem tra password
        boolean passwordIsMatched = passwordEncoder.matches(authenRequest.getPassword(), user.getPassword());

        if(!passwordIsMatched) throw new UserNotFoundException(ErrorDetail.USER_NOT_EXISTED); //ERROR 401

        if(!user.getIs_active()){
            throw new UnAuthorizedException(ErrorDetail.UNAUTHORIZED);
        }
        //Ma hoa token
        return AuthenResponse.builder().token(jwtProvider.generateToken(user)).build();
    }
    public void logout(LogoutRequest request){
        var token = request.getToken();
        try {
            var signToken = jwtProvider.verifyToken(token,true);
            String jwtID = signToken.getJWTClaimsSet().getJWTID();
            Date expityTime = signToken.getJWTClaimsSet().getExpirationTime();
            iInvalidToken.save(InvalidToken.builder().
                            id(jwtID).token(token)
                            .expiryTime(expityTime).
                    build());

        } catch (ParseException | JOSEException e) {
            throw new UndecodeJwtException(ErrorDetail.DECODE_JWT_ERROR);
        }
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
