package org.oauth2.shopapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone Number Is Required")
    private String phoneNumber;

    @NotBlank(message = "Password cant be blank")
    private String password;

}
