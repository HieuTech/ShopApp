package org.oauth2.shopapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.oauth2.shopapp.dto.request.CategoriesDTO;
import org.oauth2.shopapp.dto.request.ProductDTO;
import org.oauth2.shopapp.service.EmailService;
import org.oauth2.shopapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(
            description = "Get endpoint for Product",
            summary = "This is a summary for Product get Product",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("") //http://localhost:8083/api/v1/products?page=10&limit=3
    public ResponseEntity<String> getProduct(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit) {
        productService

        return ResponseEntity.ok(String.format("get Products, page = %d, limit = %d", page, limit));

    }

    @GetMapping("/{id}") //http://localhost:8083/api/v1/products/11
    public ResponseEntity<String> getProduct(@PathVariable Long id) {
        {
            return ResponseEntity.ok(String.format("get product %d", id));
        }
    }

    //Chuyển file đó thành data
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //http://localhost:8083/api/v1/products
    //Nếu tham số truyền vào là 1 object thì là phải thông qua DTO
    public ResponseEntity<?> createProducts(@ModelAttribute @Valid ProductDTO productDTO,
//                                              @RequestPart("files") MultipartFile files,
                                            BindingResult resultError) {

        try {
            if (resultError.hasErrors()) {
                List<String> errors = resultError.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(errors);
            }
            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for (MultipartFile file : files) {

                if (file.getSize() == 0) {
                    continue;
                }

                //kiem tra kich thuoc file
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Image must less than 10MB");

                } //kich thuoc 10MB

                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                //luu file, cap nhat thumbnail trong DTO
                String fileName = storeFile(file);
                //luu vao product thumbnail trong DB
            }

            return ResponseEntity.ok("Product created");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //    {
//        "name":"",
//            "price": 22,
    // **Thumbnail(tên file ảnh) ko phải truyền bên ngoài vào, mà là tự tạo trong logic
//            "thumbnail": "link here",
//            "description": "desc",
//            "category_id":2
//    }
    //doi ten file va luu file
    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //them UUID vao truoc ten file
        String uniqueFileName = String.format("%s _ %s", UUID.randomUUID(), fileName);
        //duong dan den thu muc muon luu file
        Path uploadDir = Paths.get("uploads");
        //kiem tra xem file do co ton tai k
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        //duong dan day du den file
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        //sao chep file vao thu muc dich
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }


    @PutMapping("/{id}") //http://localhost:8083/api/v1/products/2
    public ResponseEntity<String> updateCate(@PathVariable Long id) {
        return ResponseEntity.ok("this is update products" + id);
    }

    @DeleteMapping("/{id}") //http://localhost:8083/api/v1/products/2
    public ResponseEntity<String> deleteCate(@PathVariable Long id) {
        return ResponseEntity.ok("delete");
    }
}
