package org.oauth2.shopapp.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.oauth2.shopapp.validation.DobConstraint;
import org.oauth2.shopapp.validation.PasswordMatches;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches()

public class UserDTO {

    @NotBlank(message = "Email Is Required")

    private String userName;


    @NotBlank(message = "Email Is Required")
    private String email;

    @NotBlank(message = "Password Is Required")
    private String password;

    private String retypePassword;


}
