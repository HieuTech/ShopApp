package org.oauth2.shopapp.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {

    @NotBlank(message = "user id is not be empty")
    @Min(value = 1, message = "User Id must be > 0 ")
    @JsonProperty("user_id")
    private Long userId;

    @NotBlank(message = "full name is not blank")
    @JsonProperty("fullname")
    private String fullName;

    @NotBlank
    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("phone_number")
    private String phoneNumber2;


    private String email;
    private String address;
    private String note;

    @JsonProperty("shipping_method")
    private Float shippingMethod;

    @JsonProperty("shipping_address")
    private Float shippingAddress;

    @JsonProperty("payment_method")
    private Float paymentMethod;




}
