package org.oauth2.shopapp.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
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
public class Users {

    @Id
    String id;

    String userName;
    String email;
    String phoneNumber;
    String address;
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
