package org.oauth2.shopapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.ProductDTO;
import org.oauth2.shopapp.dto.response.ProductResponse;
import org.oauth2.shopapp.entity.Products;
import org.oauth2.shopapp.service.AmazonClient;
import org.oauth2.shopapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @Operation(
            description = "Get endpoint for Product",
            summary = "This is a summary for Product get Product",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid T oken",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("") // http://localhost:8080/identity/api/v1/products?page=10&limit=3
    public org.oauth2.shopapp.dto.response.ApiResponse<Page<Products>> getProduct(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit) {

        Page<Products> product = productService.getAllProduct(page - 1, limit);

        return org.oauth2.shopapp.dto.response.ApiResponse.<Page<Products>>builder()
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(product)
                .build();
    }

    @GetMapping("/sort-asc") // http://localhost:8080/identity/api/v1/products/sort-asc?page=10&limit=3&sort=asc
    public org.oauth2.shopapp.dto.response.ApiResponse<Page<Products>> sortProductByPriceAsc(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            @RequestParam(value = "sort", defaultValue = "asc") String sort) {

        Page<Products> product = productService.sortByPrice(page - 1, limit, sort);

        return org.oauth2.shopapp.dto.response.ApiResponse.<Page<Products>>builder()
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(product)
                .build();
    }
    @GetMapping("/sort-des") // http://localhost:8080/identity/api/v1/products/sort-des?page=10&limit=3&sort=des
    public org.oauth2.shopapp.dto.response.ApiResponse<Page<Products>> sortProductByPriceDes(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            @RequestParam(value = "sort", defaultValue = "des") String sort) {

        Page<Products> product = productService.sortByPrice(page - 1, limit, sort);

        return org.oauth2.shopapp.dto.response.ApiResponse.<Page<Products>>builder()
                .message(ErrorDetail.SUCCESS.getMessage())
                .result(product)
                .build();
    }


    @GetMapping("/{id}") //http://localhost:8080/identity/api/v1/products/666e5a0a00e9c2550af46fa7
    public org.oauth2.shopapp.dto.response.ApiResponse<Products> findById(@PathVariable("id") String id) {

            return org.oauth2.shopapp.dto.response.ApiResponse.<Products>builder()
                    .result(productService.findById(id))
                    .build();

    }

    @GetMapping("/name") //http://localhost:8080/identity/api/v1/products/name?name
    public org.oauth2.shopapp.dto.response.ApiResponse<List<Products>> searchByName(@RequestParam("name") String name){
        return org.oauth2.shopapp.dto.response.ApiResponse.<List<Products>>builder()
                .result(productService.findByName(name))
                .build();
    }


    //Chuyển file đó thành data
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //http://localhost:8080/identity/api/v1/products
    //Nếu tham số truyền vào là 1 object thì là phải thông qua DTO
    public org.oauth2.shopapp.dto.response.ApiResponse<?> createProducts(@ModelAttribute @Valid ProductDTO productDTO,
                                                                                       BindingResult result) {

        if(result.hasErrors()){
            return org.oauth2.shopapp.dto.response.ApiResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(result.getFieldError().getDefaultMessage())
                    .build();
        }

        return org.oauth2.shopapp.dto.response.ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(productDTO))
                .build();





//        try {
//            if (resultError.hasErrors()) {
//                List<String> errors = resultError.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
//                return ResponseEntity.badRequest().body(errors);
//            }
//            List<MultipartFile> files = productDTO.getFiles();
//            files = files == null ? new ArrayList<MultipartFile>() : files;
//            for (MultipartFile file : files) {
//
//                if (file.getSize() == 0) {
//                    continue;
//                }
//
//                //kiem tra kich thuoc file
//                if (file.getSize() > 10 * 1024 * 1024) {
//                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Image must less than 10MB");
//
//                } //kich thuoc 10MB
//
//                String contentType = file.getContentType();
//                if (contentType == null || !contentType.startsWith("image/")) {
//                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
//                }
//                //luu file, cap nhat thumbnail trong DTO
//                String fileName = storeFile(file);
//                //luu vao product thumbnail trong DB
//            }
//
//            return ResponseEntity.ok("Product created");
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
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
//    private String storeFile(MultipartFile file) throws IOException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        //them UUID vao truoc ten file
//        String uniqueFileName = String.format("%s _ %s", UUID.randomUUID(), fileName);
//        //duong dan den thu muc muon luu file
//        Path uploadDir = Paths.get("uploads");
//        //kiem tra xem file do co ton tai k
//        if (!Files.exists(uploadDir)) {
//            Files.createDirectories(uploadDir);
//        }
//
//        //duong dan day du den file
//        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
//        //sao chep file vao thu muc dich
//        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//        return uniqueFileName;
//    }


    @PutMapping("/{id}") //http://localhost:8083/api/v1/products/2
    public ResponseEntity<String> updateCate(@PathVariable Long id) {
        return ResponseEntity.ok("this is update products" + id);
    }

    @DeleteMapping("/{id}") //http://localhost:8083/api/v1/products/2
    public ResponseEntity<String> deleteCate(@PathVariable Long id) {
        return ResponseEntity.ok("delete");
    }
}
