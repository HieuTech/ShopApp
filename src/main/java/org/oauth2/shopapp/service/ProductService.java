package org.oauth2.shopapp.service;

import lombok.RequiredArgsConstructor;
import org.oauth2.shopapp.constant.ErrorDetail;
import org.oauth2.shopapp.dto.request.ProductDTO;
import org.oauth2.shopapp.dto.response.ProductResponse;
import org.oauth2.shopapp.entity.Products;
import org.oauth2.shopapp.exception.NotFoundException;
import org.oauth2.shopapp.mapper.ProductMapper;
import org.oauth2.shopapp.repository.IProductsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final IProductsRepository iProductsRepository;
    private final ProductMapper productMapper;
    public ProductResponse createProduct(ProductDTO req){
        return productMapper.toProductResponse(iProductsRepository.insert(productMapper.toProduct(req)));
    }

    public Page<Products> sortByPrice(Integer page, Integer limit, String direction){
        Sort sort1 = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("price").ascending() : Sort.by("price").descending();
        Pageable pageable = PageRequest.of(page,limit,sort1);
        return iProductsRepository.findAll(pageable);

    }



    public Products findById(String id){
        return  iProductsRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorDetail.PRODUCT_NOTFOUND_ERROR));

    }

    public List<Products> findByName(String name){
        return iProductsRepository.findProductsByNameContainingIgnoreCase(name);
    }

    public Page<Products> getAllProduct(Integer page, Integer limit){
        Pageable pageable = PageRequest.of(page,limit);

        return iProductsRepository.findAll(pageable);

    }


}
