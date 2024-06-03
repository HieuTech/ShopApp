package org.oauth2.shopapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.logging.Level;

@Data //toString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CategoriesDTO {
    @NotEmpty(message = "name is not be Blank")
    private String  name;
}
