package org.oauth2.shopapp.repository;

import org.oauth2.shopapp.entity.Categories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends MongoRepository<Categories,String> {
}
