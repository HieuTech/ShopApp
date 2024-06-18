package org.oauth2.shopapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.ProductDTO;
import org.oauth2.shopapp.dto.response.ApiResponse;
import org.oauth2.shopapp.dto.response.ProductResponse;
import org.oauth2.shopapp.entity.Products;
import org.oauth2.shopapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class ProductUnitTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private Page<Products> productsPage;
    @Mock
    private ProductDTO productDTO;
    @Mock
    private ProductResponse productResponse;
    @Mock
    private BindingResult errorResult;


    @Test
    public void testCreateProducts_Success() throws Exception {

        ProductDTO productDTO1 = ProductDTO.builder().
                name("product 1")
                .description("desc")
                .price(123333.1)
                .categoryId("0ifjd0i23dj923h").
                build();
        ProductResponse productResponse1 = ProductResponse.builder()
                .id("dmnvciowefjoas")
                .name("product 1")
                .description("desc")
                .price(123333.1)
                .active(true)
                .build();


        // Mock data
        when(errorResult.hasErrors()).thenReturn(false);
        when(productService.createProduct(productDTO1)).thenReturn(productResponse1);

        // Execute the method
        ApiResponse<ProductResponse> response = (ApiResponse<ProductResponse>) productController.createProducts(productDTO1, errorResult);

        // Assertions
        Assertions.assertEquals(HttpStatus.CREATED.value(), 201);
       Assertions.assertEquals(ErrorDetail.CREATED.getCode(), response.getCode());
       Assertions.assertEquals(ErrorDetail.CREATED.getMessage(), response.getMessage());
       Assertions.assertEquals(productDTO1.getName(), response.getResult().getName());
        log.info("response 1" + productResponse1.getName());
        log.info("response 2" + response.getResult().getName());
    }
    @Test
    void createProduct_ValidError_Name(){
        ProductDTO productDTO1 = ProductDTO.builder().
                name("p")
                .description("desc")
                .price(123333.1)
                .categoryId("0ifjd0i23dj923h").
                build();
        when(errorResult.hasErrors()).thenReturn(true);
        when(errorResult.getFieldError().getDefaultMessage()).




    }


    @Test
    void getProductSuccess(){
        //mockData
        int page = 2;
        int limit = 2;

        when(productService.getAllProduct(page - 1,limit)).thenReturn(productsPage);
        ApiResponse<Page<Products>> response = productController.getProduct(page,limit);

        Assertions.assertEquals(ErrorDetail.SUCCESS.getCode(), response.getCode());
        Assertions.assertEquals(ErrorDetail.SUCCESS.getMessage(), response.getMessage());
        Assertions.assertEquals(productsPage, response.getResult());
    }

}
