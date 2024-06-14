package org.oauth2.shopapp.repository;

import org.oauth2.shopapp.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends MongoRepository<Cart,String> {
}
