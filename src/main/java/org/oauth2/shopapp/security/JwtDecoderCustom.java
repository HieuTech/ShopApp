package org.oauth2.shopapp.security;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.IntrospectRequest;
import org.oauth2.shopapp.exception.UnAuthenticationException;
import org.oauth2.shopapp.exception.UnAuthorizedException;
import org.oauth2.shopapp.exception.UndecodeJwtException;
import org.oauth2.shopapp.service.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtDecoderCustom implements JwtDecoder {
    @Value("${jwt.secretKey}")
    protected String SECRET_KEY;

    private final AuthenService service;

    private NimbusJwtDecoder nimbusJwtDecoder = null;


    @Override
    public Jwt decode(String token) {

        try {
            var response = service.introspect(IntrospectRequest.builder().token(token).build());
            if (!response.getIsValid()) throw new UnAuthorizedException(ErrorDetail.UNAUTHORIZED);
        } catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }


        //ma hoa = thu vien nimbusJwtDecoder
        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
