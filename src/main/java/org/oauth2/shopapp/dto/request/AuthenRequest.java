package org.oauth2.shopapp.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenRequest {
    @NotBlank(message = "Email Is Required")
    String email;
    @NotBlank(message = "Password Is Required")
    String password;
}
