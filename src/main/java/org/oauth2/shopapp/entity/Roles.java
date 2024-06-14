package org.oauth2.shopapp.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.oauth2.shopapp.constant.RoleName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Roles {
    @Id
    String id;
    RoleName roleName;

}
