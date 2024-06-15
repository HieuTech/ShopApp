package org.oauth2.shopapp.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.oauth2.shopapp.entity.Categories;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {

    String id;
    String name;
    String description;
    String thumbnail;
    Date createdAt;
    LocalDate updateAt;
    Boolean active;
    Double price;


    Categories categories;
}
