package org.oauth2.shopapp.repository;

import org.oauth2.shopapp.entity.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends MongoRepository<Orders, String> {
}
