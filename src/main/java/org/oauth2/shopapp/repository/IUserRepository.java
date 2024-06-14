package org.oauth2.shopapp.repository;

import org.oauth2.shopapp.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<Users, String> {

    boolean existsByUserName(String userName);

    Users findByUserName(String userName);

    boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);
}
