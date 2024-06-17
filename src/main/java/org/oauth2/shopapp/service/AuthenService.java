package org.oauth2.shopapp.service;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.constant.RoleName;
import org.oauth2.shopapp.dto.request.AuthenRequest;
import org.oauth2.shopapp.dto.request.ExchangeTokenRequest;
import org.oauth2.shopapp.dto.request.IntrospectRequest;
import org.oauth2.shopapp.dto.request.LogoutRequest;
import org.oauth2.shopapp.dto.response.AuthenResponse;
import org.oauth2.shopapp.dto.response.IntrospectReponse;
import org.oauth2.shopapp.entity.InvalidToken;
import org.oauth2.shopapp.entity.Roles;
import org.oauth2.shopapp.entity.Users;
import org.oauth2.shopapp.exception.UnAuthenticationException;
import org.oauth2.shopapp.exception.UnAuthorizedException;
import org.oauth2.shopapp.exception.UndecodeJwtException;
import org.oauth2.shopapp.exception.NotFoundException;
import org.oauth2.shopapp.repository.IInvalidToken;
import org.oauth2.shopapp.repository.IUserRepository;
import org.oauth2.shopapp.security.JwtProvider;
import org.oauth2.shopapp.service.httpclient.ExchangeToken;
import org.oauth2.shopapp.service.httpclient.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenService {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository iUserRepository;
    private final IInvalidToken iInvalidToken;
    private final JwtProvider jwtProvider;
    private final ExchangeToken exchangeToken;
    private final UserInfo userInfo;


    @Value("${outbound.client-id}")
    protected String CLIENT_ID;


    @Value("${outbound.uri-redirect}")
    protected String URI_REDIRECT;


    @Value("${outbound.grant-type}")
    protected String GRANT_TYPE;


    @Value("${outbound.secret-id}")
    protected String SECRET_ID;


    public AuthenResponse outboundAuthen(String code){
        var response = exchangeToken.exchangeToken(ExchangeTokenRequest.builder()
                        .clientId(CLIENT_ID)
                        .clientSecret(SECRET_ID)
                        .code(code)
                        .grantType(GRANT_TYPE)
                        .redirectUri(URI_REDIRECT)
                .build());

        //Google Tra ve
        log.info("RESPONSE TOKEN {}", response);

        var userInfoDetail = userInfo.getUserInfo("json", response.getAccessToken());
        log.info("userInfoDetail" + userInfoDetail);

        //Onboard User, Generate Token dua tren thong tin user cua minh

        List<Roles> roles = new ArrayList<>();
        roles.add(Roles.builder().roleName(RoleName.ROLE_USER).build());

        var user = iUserRepository.findByEmail(userInfoDetail.getEmail()).orElseGet(() ->
            iUserRepository.save(Users.builder()
                    .email(userInfoDetail.getEmail())
                    .userName(userInfoDetail.getName())
                    .avatar(userInfoDetail.getPicture())
                    .createdAt(new Date())
                    .is_active(true)
                    .rolesList(roles)
                    .build())
        );

        String token = jwtProvider.generateToken(user);


        return AuthenResponse.builder()
                .token(token)
                .build();

        //ko lay token cua Google de dua cho User.



    }

    public AuthenResponse login(AuthenRequest authenRequest){
        var user =  iUserRepository.findByEmail(authenRequest.getEmail()).orElseThrow(()-> new NotFoundException(ErrorDetail.USER_NOT_EXISTED));

        //kiem tra password
        boolean passwordIsMatched = passwordEncoder.matches(authenRequest.getPassword(), user.getPassword());

        if(!passwordIsMatched) throw new NotFoundException(ErrorDetail.USER_NOT_EXISTED); //ERROR 401

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
