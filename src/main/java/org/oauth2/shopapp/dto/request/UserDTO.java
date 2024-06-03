package org.oauth2.shopapp.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.oauth2.shopapp.validation.PasswordMatches;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches()

public class UserDTO {

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone Number Is Required")

    private String phoneNumber;

    private String address;

    @NotBlank(message = "Password cant be blank")
    private String password;

    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date dob;

    @JsonProperty("facebook_account_id")
    private Integer facebookAccountId;

    @JsonProperty("google_account_id")
    private Integer googleAccountId;

    @NotNull(message = "Role ID is required")
    @JsonProperty("role_id")
    private Long roleId;

}
