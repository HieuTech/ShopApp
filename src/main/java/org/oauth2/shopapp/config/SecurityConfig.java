package org.oauth2.shopapp.config;

import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.RoleName;
import org.oauth2.shopapp.security.AccessDenied;
import org.oauth2.shopapp.security.CustomJwtGrantedAuthorConverter;
import org.oauth2.shopapp.security.JwtAuthenticationEntryPoint;
import org.oauth2.shopapp.security.JwtDecoderCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//@RequiredArgsConstructor
public class SecurityConfig {
    private final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/v1/register", "/api/v1/auth/token", "/api/v1/auth/introspect", "/api/v1/auth/logout", "/api/v1/auth/refresh"
            , "/api/v1/auth/outbound/authentication"
    };


    private  final JwtDecoderCustom jwtDecoderCustom;
    private final AccessDenied accessDenied ;
    public SecurityConfig(@Lazy JwtDecoderCustom jwtDecoderCustom, @Lazy AccessDenied accessDenied){
        this.jwtDecoderCustom = jwtDecoderCustom;
        this.accessDenied = accessDenied;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers("/api/v1/admin/**").hasAnyAuthority(RoleName.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/user/**").hasAnyAuthority(RoleName.ROLE_USER.name())
                        .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer ->
                jwtConfigurer.decoder(jwtDecoderCustom).jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()).accessDeniedHandler(accessDenied));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }





    //MYSQL
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new CustomJwtGrantedAuthorConverter());

        return jwtAuthenticationConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder();
    }
}
