package org.oauth2.shopapp.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.oauth2.shopapp.constant.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders {

    @Id
    String id;
    String trackingNumber;
    String fullName;
    String phoneNumber;
    String address;
    String note;



    Date createdAt;
    OrderStatus status;
    Boolean active;
    Double totalPrice;

    @DBRef
    Users users;
}
