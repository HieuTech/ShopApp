package org.oauth2.shopapp.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.oauth2.shopapp.validation.RegexPhoneNumberValidator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RegexPhoneNumberValidator
public class Users {

    @Id
    String id;

    @NotBlank(message = "User Name Is Empty")
    String userName;

    @Email(regexp = "^\\w+@\\w+\\.\\w{2,3})$")
    String email;

    String phoneNumber;

    String address;
    @NotBlank(message = "Password Is Empty")
            @Min(value = 7, message = "Password At Least 7 character")
    String password;
    Date createdAt;
    Date updatedAt;
    Boolean is_active;
    String avatar;
    LocalDate dob;
    Integer facebookAccountId;
    Integer googleAccountId;

    List<Roles> rolesList;
}
