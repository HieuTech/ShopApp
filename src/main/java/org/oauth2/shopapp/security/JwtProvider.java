package org.oauth2.shopapp.security;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.entity.Users;
import org.oauth2.shopapp.exception.CreateTokenException;
import org.oauth2.shopapp.exception.UnAuthenticationException;
import org.oauth2.shopapp.repository.IInvalidToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final IInvalidToken iInvalidToken;

    @Value("${jwt.secretKey}")
    protected String SECRET_KEY;
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;
    @Value("${jwt.refresh-duration}")
    protected Long REFRESH_DURATION;

    public String generateToken(Users users) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().
                subject(users.getEmail())
                .issueTime(new Date())
                .issuer("GoVegan Server")
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString()).
                claim("scope", users.getRolesList()).
                build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new CreateTokenException(ErrorDetail.CREATE_JWT_ERROR);
        }
    }

    //dung cho logout
    public SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {


        JWSVerifier jwsVerifier = new MACVerifier(SECRET_KEY.getBytes());

        //trong SignedJWT la thong tin cua token
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh) ? new Date(signedJWT.getJWTClaimsSet()
                .getIssueTime().toInstant().plus(REFRESH_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(jwsVerifier);
        if (!(verified && expiryTime.after(new Date())))
            throw new UnAuthenticationException(ErrorDetail.UNAUTHENTICATED);


        //nếu mà trong DB của Expied mà có token này, th ko đc phép truy cập
        if (iInvalidToken.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new UnAuthenticationException(ErrorDetail.UNAUTHENTICATED);

        return signedJWT;


    }


}
