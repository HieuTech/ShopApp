package org.oauth2.shopapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.oauth2.shopapp.dto.request.ProductDTO;
import org.oauth2.shopapp.dto.response.ProductResponse;
import org.oauth2.shopapp.entity.Products;
import org.oauth2.shopapp.mapper.ProductMapper;
import org.oauth2.shopapp.repository.IProductsRepository;
import org.oauth2.shopapp.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


//@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/test.yml")
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {
    //Inject Mock cho interface se ko hoat dong
    //SPRINGBOOT Test chỉ dùng cho integration Test, vì nó load toàn bộ Bean trong App giống với việc chạy app.java
    @InjectMocks
    private ProductController productController;

    @MockBean
    IProductsRepository iProductsRepository;
    @MockBean
    private ProductService productService;
    @Mock
    private Page<Products> productPage;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductDTO request;
    @MockBean
    private ProductResponse response;
    @MockBean
    private Products products;
    @MockBean
    private ProductMapper productMapper;

    //Unit Test


    @BeforeEach
    void initData() {

        request = ProductDTO.builder().
                name("product 1")
                .description("desc")
                .price(123333.1)
                .categoryId("0ifjd0i23dj923h").
                build();
        response = ProductResponse.builder()
                .id("dmnvciowefjoas")
                .name("product 1")
                .description("desc")
                .price(123333.1)
                .active(true)
                .build();

    }

    @Test
    public void testCreateProduct_Success() throws Exception {
        //MockData


        //vì dữ liệu JSON nên phải parse về String
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(productService.createProduct(ArgumentMatchers.any())).
                thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}
