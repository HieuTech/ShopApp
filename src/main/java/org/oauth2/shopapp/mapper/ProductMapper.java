package org.oauth2.shopapp.mapper;


import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.ProductDTO;
import org.oauth2.shopapp.dto.response.ProductResponse;
import org.oauth2.shopapp.entity.Products;
import org.oauth2.shopapp.exception.NotFoundException;
import org.oauth2.shopapp.repository.ICategoryRepository;
import org.oauth2.shopapp.service.AmazonClient;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final ICategoryRepository repository;
    private final AmazonClient amazonClient;

    public Products toProduct(ProductDTO req) {
        return Products.builder()
                .name(req.getName())
                .description(req.getDescription())
                .active(true)
                .categories(repository.findById(req.getCategoryId()).orElseThrow(() -> new NotFoundException(ErrorDetail.CATEGORY_NOTFOUND_ERROR))
                )
                .price(req.getPrice())
                .createdAt(new Date())
                .thumbnail(amazonClient.uploadFile(req.getFiles()))
                .build();


    }

    ;

    public ProductResponse toProductResponse(Products p) {
        return ProductResponse.builder()
                .id(p.getId())
                .active(p.getActive())
                .updateAt(p.getUpdateAt())
                .categories(p.getCategories())
                .price(p.getPrice())
                .description(p.getDescription())
                .name(p.getName())
                .thumbnail(p.getThumbnail())

                .build();
    }


}
