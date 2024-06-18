package org.oauth2.shopapp.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.oauth2.shopapp.controller.ProductController;
import org.oauth2.shopapp.dto.request.ProductDTO;
import org.oauth2.shopapp.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductController productController;

    private ProductDTO productDTO;


    @Test

    void testGetAllProductSuccess(){
        Pageable pageable =  PageRequest.of(0,3);



    }
}
