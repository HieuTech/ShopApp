package org.oauth2.shopapp.service;

import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.dto.response.ProductResponse;
import org.oauth2.shopapp.entity.Products;
import org.oauth2.shopapp.repository.IProductsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final IProductsRepository iProductsRepository;


//    public Page<ProductResponse> getAllProduct(Integer page, Integer limit){
//        Pageable pageable = PageRequest.of(page,limit);
//
//        return iProductsRepository.findAll(pageable);
//
//    }


}
