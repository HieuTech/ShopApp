package org.oauth2.shopapp.repository;

import org.oauth2.shopapp.entity.InvalidToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvalidToken extends MongoRepository<InvalidToken, String> {

    boolean existsById(String id);
}
