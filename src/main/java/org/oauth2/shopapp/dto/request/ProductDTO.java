package org.oauth2.shopapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Product Name Is Required")
    @Size(min = 3, max = 200, message = "Title must between 3 and 200 characters")
    private String name;
    @Min(value = 0, message = "Price must greater than or equal to 0")
    @Max(value = 1000000, message = "Price must less than or equal 1000000")
    private Float price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id")
    private Integer categoryId;

    private List<MultipartFile> files;
}
