package org.oauth2.shopapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntrospectRequest {

    @NotBlank(message = "Token Is Blank")
    String token;
}
