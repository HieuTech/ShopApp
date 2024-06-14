package org.oauth2.shopapp.repository;

import org.oauth2.shopapp.constant.RoleName;
import org.oauth2.shopapp.entity.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolesRepository extends MongoRepository<Roles, String> {
    Roles findByRoleName(RoleName roleName);
}
