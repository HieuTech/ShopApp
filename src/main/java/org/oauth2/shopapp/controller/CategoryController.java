package org.oauth2.shopapp.controller;


import jakarta.validation.Valid;
import org.oauth2.shopapp.dto.request.CategoriesDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @GetMapping("") //http://localhost:8083/api/v1/categories?page=10&limit=3
    public ResponseEntity<String> getCategories(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit){                                    {
        return ResponseEntity.ok(String.format("get Categories, page = %d, limit = %d",page,limit));
    }
    }

    @PostMapping("") //http://localhost:8083/api/v1/categories
    //Nếu tham số truyền vào là 1 object thì là phải thông qua DTO
    public ResponseEntity<?> createCategories(@RequestBody @Valid  CategoriesDTO categoriesDTO,
                                                   BindingResult resultError){
        if(resultError.hasErrors()){
            List<String> errorMessage = resultError.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
    return ResponseEntity.ok("This is create categories" + categoriesDTO.getName());
    }
    @PutMapping("/{id}") //http://localhost:8083/api/v1/categories/2
    public ResponseEntity<String> updateCate(@PathVariable Long id){
        return ResponseEntity.ok("this is update categories"+ id);
    }
    @DeleteMapping("/{id}") //http://localhost:8083/api/v1/categories/2
    public ResponseEntity<String> deleteCate(@PathVariable Long id){
        return ResponseEntity.ok("delete");
    }
}
