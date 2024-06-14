package org.oauth2.shopapp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.oauth2.shopapp.entity.Roles;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String userName;
    String email;
    String avatar;

    List<String> rolesList;
}
